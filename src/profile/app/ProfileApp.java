package profile.app;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import profile.data_access.InMemoryProfileRepository;
import profile.entity.*;
import profile.interface_adapter.*;
import profile.use_case.CurrentUser;
import profile.use_case.edit_profile.EditProfileInteractor;
import profile.use_case.view_profile.ViewProfileInteractor;
import profile.view.ProfilePanel;

/** Composition root for Tony's UC3/UC4 demo. The seeded session is not real authentication. */
public final class ProfileApp {
    private ProfileApp() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PublicProfile tony = new PublicProfile("tony",
                    new ProfileDetails("Tony Lin", "", "UofT student", "St. George"), 0, List.of());
            PublicProfile seller = new PublicProfile("seller",
                    new ProfileDetails("Campus Seller", "", "Books and study supplies", "Scarborough"), 4.5,
                    List.of(new ProfileListing("1", "CSC207 textbook", 30, true),
                            new ProfileListing("2", "Sold notebook", 5, false)));
            InMemoryProfileRepository repository = new InMemoryProfileRepository(List.of(tony, seller));
            CurrentUser session = () -> "tony";
            ProfileViewModel model = new ProfileViewModel();
            ProfilePresenter presenter = new ProfilePresenter(model);
            ProfileController controller = new ProfileController(
                    new ViewProfileInteractor(repository, session, presenter),
                    new EditProfileInteractor(repository, repository, session, presenter));
            JFrame frame = new JFrame("UofT Marketplace — Public profile demo (signed in as Tony)");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new ProfilePanel(model, controller));
            controller.myProfile();
            frame.pack();
            frame.setMinimumSize(frame.getSize());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
