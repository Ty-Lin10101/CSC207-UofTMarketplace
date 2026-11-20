package profile;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import profile.data_access.InMemoryProfileRepository;
import profile.entity.*;
import profile.interface_adapter.*;
import profile.use_case.*;
import profile.use_case.edit_profile.*;
import profile.use_case.view_profile.*;
import profile.view.ProfilePanel;

/** Dependency-free behavior tests; assertions run regardless of JVM -ea settings. */
public final class ProfileTests {
    private static int passed;

    public static void main(String[] args) throws Exception {
        test("public view includes fields, rating, and only active listings", () -> {
            Fixture f = new Fixture(); f.controller.viewProfile("other");
            ProfileResponse p = f.model.getProfile();
            check(p.displayName.equals("Other Student") && p.campus.equals("Scarborough"));
            check(p.bio.equals("Books") && p.picture.equals("photo.png") && p.rating == 4.5);
            check(p.listings.size() == 1 && p.listings.get(0).id.equals("active"));
            check(!p.editable && !f.model.isEditing());
        });
        test("own profile and edit prefill", () -> {
            Fixture f = new Fixture(); f.controller.myProfile(); f.controller.edit("tony");
            check(f.model.getProfile().editable && f.model.isEditing());
            check(f.model.getProfile().displayName.equals("Tony Lin"));
        });
        test("save updates all editable fields and preserves other public data", () -> {
            Fixture f = new Fixture(); f.controller.save("tony", "  New Name  ", "new.png", " New bio ", "Mississauga");
            PublicProfile p = f.repository.findById("tony").orElseThrow();
            check(p.details.displayName.equals("New Name") && p.details.picture.equals("new.png"));
            check(p.details.bio.equals("New bio") && p.details.campus.equals("Mississauga"));
            check(p.userId.equals("tony") && p.rating == 4.5 && p.listings.size() == 2);
            check(!f.model.isEditing() && f.model.getMessage().equals("Profile saved."));
            f.controller.myProfile(); check(f.model.getProfile().displayName.equals("New Name"));
        });
        test("cancel does not persist draft changes", () -> {
            Fixture f = new Fixture(); PublicProfile before = f.repository.findById("tony").orElseThrow();
            f.controller.edit("tony"); f.controller.cancel("tony");
            check(!f.model.isEditing() && f.repository.findById("tony").orElseThrow() == before);
        });
        test("invalid fields do not change repository or leave edit mode", () -> {
            String[][] invalid = {{" ", "", "", "St. George"}, {"x".repeat(81), "", "", "St. George"},
                    {"Tony", "", "x".repeat(1001), "St. George"}, {"Tony", "", "", "Unknown"},
                    {"Tony", "x".repeat(2049), "", "St. George"}, {null, "", "", "St. George"},
                    {"Tony", "", "", null}};
            for (String[] values : invalid) {
                Fixture f = new Fixture(); f.controller.edit("tony");
                PublicProfile before = f.repository.findById("tony").orElseThrow();
                f.controller.save("tony", values[0], values[1], values[2], values[3]);
                check(f.repository.findById("tony").orElseThrow() == before);
                check(f.model.isEditing() && !f.model.getMessage().isEmpty());
            }
        });
        test("valid limits and optional fields", () -> {
            Fixture f = new Fixture();
            f.controller.save("tony", "x".repeat(80), null, "x".repeat(1000), "St. George");
            check(f.model.getProfile().displayName.length() == 80 && f.model.getProfile().picture.isEmpty());
            f.controller.save("tony", "Tony", "", null, "Scarborough");
            check(f.model.getProfile().bio.isEmpty());
        });
        test("other account cannot be edited even through a forged request", () -> {
            Fixture f = new Fixture(); PublicProfile before = f.repository.findById("other").orElseThrow();
            f.controller.edit("other"); check(f.model.getMessage().contains("own profile"));
            f.controller.save("other", "Hacked", "", "", "St. George");
            check(f.repository.findById("other").orElseThrow() == before);
            f.controller.cancel("other"); check(f.model.getMessage().contains("own profile"));
        });
        test("logged-out visitors can view but cannot edit", () -> {
            Fixture f = new Fixture(); f.session.id = null;
            f.controller.viewProfile("tony"); check(!f.model.getProfile().editable);
            f.controller.myProfile(); check(f.model.getMessage().contains("Log in"));
            f.controller.edit("tony"); check(f.model.getMessage().contains("Log in"));
            f.controller.save("tony", "Changed", "", "", "St. George");
            check(f.repository.findById("tony").orElseThrow().details.displayName.equals("Tony Lin"));
        });
        test("session change rejects a stale edit form", () -> {
            Fixture f = new Fixture(); f.controller.edit("tony"); f.session.id = "other";
            f.controller.save("tony", "Changed", "", "", "St. George");
            check(f.model.getMessage().contains("own profile"));
            check(f.repository.findById("tony").orElseThrow().details.displayName.equals("Tony Lin"));
        });
        test("missing and invalid IDs produce the specified error", () -> {
            for (String id : new String[]{"missing", "", null}) {
                Fixture f = new Fixture(); f.controller.viewProfile(id);
                check(f.model.getMessage().equals("This user is no longer available"));
            }
            Fixture f = new Fixture(); f.session.id = "deleted";
            f.controller.edit("deleted"); check(f.model.getMessage().contains("no longer available"));
            f.controller.save("deleted", "Name", "", "", "St. George");
            check(f.model.getMessage().contains("no longer available"));
            check(f.repository.findById("deleted").isEmpty());
        });
        test("storage errors are presented and never report save success", () -> {
            Fixture f = new Fixture(); f.controller.edit("tony");
            ProfileReader brokenReader = id -> { throw new ProfileStorageException("internal", null); };
            ProfileWriter brokenWriter = (id, details) -> { throw new ProfileStorageException("internal", null); };
            ProfilePresenter presenter = new ProfilePresenter(f.model);
            new ViewProfileInteractor(brokenReader, f.session, presenter).execute("tony");
            check(f.model.getMessage().contains("Unable to load"));
            EditProfileInteractor edit = new EditProfileInteractor(brokenReader, brokenWriter, f.session, presenter);
            edit.begin("tony"); check(f.model.getMessage().contains("Unable to load"));
            edit.save(new EditProfileRequest("tony", "New", "", "", "St. George"));
            check(f.model.getMessage().contains("Unable to save") && f.model.isEditing());
            check(f.repository.findById("tony").orElseThrow().details.displayName.equals("Tony Lin"));
        });
        test("deletion between load and save does not recreate an account", () -> {
            Fixture f = new Fixture(); ProfilePresenter presenter = new ProfilePresenter(f.model);
            EditProfileInteractor edit = new EditProfileInteractor(f.repository,
                    (id, details) -> Optional.empty(), f.session, presenter);
            edit.begin("tony");
            edit.save(new EditProfileRequest("tony", "New", "", "", "St. George"));
            check(f.model.getMessage().contains("no longer available"));
        });
        test("null request produces a useful validation error", () -> {
            Fixture f = new Fixture();
            new EditProfileInteractor(f.repository, f.repository, f.session,
                    new ProfilePresenter(f.model)).save(null);
            check(f.model.getMessage().equals("Provide profile details."));
        });
        test("snapshot collections cannot be changed by callers", () -> {
            ArrayList<ProfileListing> source = new ArrayList<>();
            PublicProfile p = new PublicProfile("id", details("Student"), 0, source);
            source.add(new ProfileListing("1", "Title", 2, true)); check(p.listings.isEmpty());
            try { p.listings.add(source.get(0)); throw new AssertionError(); }
            catch (UnsupportedOperationException expected) { }
            ProfileResponse response = new ProfileResponse(p, false);
            try { response.listings.clear(); throw new AssertionError(); }
            catch (UnsupportedOperationException expected) { }
        });
        SwingUtilities.invokeAndWait(() -> test("Swing buttons preserve invalid drafts, cancel, save, and enforce read-only views", () -> {
            Fixture f = new Fixture(); ProfilePanel panel = new ProfilePanel(f.model, f.controller);
            f.controller.myProfile(); button(panel, "Edit profile").doClick();
            JTextField name = fields(panel).stream().filter(field -> field.getText().equals("Tony Lin")).findFirst().orElseThrow();
            name.setText(" "); button(panel, "Save").doClick();
            check(name.getText().equals(" ") && f.model.isEditing());
            check(!button(panel, "My profile").isEnabled());
            button(panel, "Cancel").doClick(); check(name.getText().equals("Tony Lin"));
            button(panel, "Edit profile").doClick(); name.setText("Updated in Swing");
            button(panel, "Save").doClick();
            check(f.repository.findById("tony").orElseThrow().details.displayName.equals("Updated in Swing"));
            f.controller.viewProfile("other");
            check(!button(panel, "Edit profile").isEnabled() && !name.isEditable());
        }));
        System.out.println("Passed " + passed + " profile behavior tests.");
    }

    private static ProfileDetails details(String name) {
        return new ProfileDetails(name, "photo.png", "Books", "Scarborough");
    }

    private static final class Session implements CurrentUser {
        String id = "tony";
        public String userId() { return id; }
    }

    private static final class Fixture {
        final Session session = new Session();
        final ProfileViewModel model = new ProfileViewModel();
        final InMemoryProfileRepository repository;
        final ProfileController controller;
        Fixture() {
            List<ProfileListing> listings = List.of(new ProfileListing("active", "Book", 20, true),
                    new ProfileListing("sold", "Notebook", 5, false));
            repository = new InMemoryProfileRepository(List.of(
                    new PublicProfile("tony", details("Tony Lin"), 4.5, listings),
                    new PublicProfile("other", details("Other Student"), 4.5, listings)));
            ProfilePresenter presenter = new ProfilePresenter(model);
            controller = new ProfileController(new ViewProfileInteractor(repository, session, presenter),
                    new EditProfileInteractor(repository, repository, session, presenter));
        }
    }

    private static void check(boolean condition) {
        if (!condition) { throw new AssertionError("Expectation failed"); }
    }

    private static void test(String name, Runnable test) {
        try { test.run(); passed++; System.out.println("PASS " + name); }
        catch (Throwable error) { throw new AssertionError("FAIL " + name, error); }
    }

    private static JButton button(Container container, String text) {
        for (Component component : descendants(container)) {
            if (component instanceof JButton && ((JButton) component).getText().equals(text)) {
                return (JButton) component;
            }
        }
        throw new AssertionError("Missing button: " + text);
    }

    private static List<JTextField> fields(Container container) {
        List<JTextField> result = new ArrayList<>();
        for (Component component : descendants(container)) {
            if (component instanceof JTextField) { result.add((JTextField) component); }
        }
        return result;
    }

    private static List<Component> descendants(Container container) {
        List<Component> result = new ArrayList<>();
        for (Component component : container.getComponents()) {
            result.add(component);
            if (component instanceof Container) { result.addAll(descendants((Container) component)); }
        }
        return result;
    }
}
