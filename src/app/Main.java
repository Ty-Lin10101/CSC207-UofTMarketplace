package app;

import Entities.Listing;
import Entities.User;
import data_access.InMemoryMarketplaceDataAccessObject;
import data_access.LocalImageUploadGateway;
import data_access.MarketplaceProfiles;
import interface_adapter.ViewListingDetails.ViewListingDetailsController;
import interface_adapter.ViewListingDetails.ViewListingDetailsPresenter;
import interface_adapter.ViewListingDetails.ViewListingDetailsViewModel;
import interface_adapter.bookmark_listing.BookmarkListingController;
import interface_adapter.bookmark_listing.BookmarkListingPresenter;
import interface_adapter.bookmark_listing.BookmarkListingViewModel;
import interface_adapter.create_listing.CreateListingController;
import interface_adapter.create_listing.CreateListingPresenter;
import interface_adapter.create_listing.CreateListingViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.searchListings.searchListingsController;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.view_bookmarks.ViewBookmarksController;
import interface_adapter.view_bookmarks.ViewBookmarksPresenter;
import interface_adapter.view_bookmarks.ViewBookmarksViewModel;
import profile.interface_adapter.ProfileController;
import profile.interface_adapter.ProfilePresenter;
import profile.interface_adapter.ProfileViewModel;
import profile.use_case.edit_profile.EditProfileInteractor;
import profile.use_case.view_profile.ViewProfileInteractor;
import profile.view.ProfilePanel;
import use_case.ViewListingDetails.ViewListingDetailsInteractor;
import use_case.bookmark_listing.BookmarkListingInteractor;
import use_case.create_listing.CreateListingInteractor;
import use_case.login.LoginInteractor;
import use_case.searchListings.searchListingsInteractor;
import use_case.signup.SignupInteractor;
import use_case.view_bookmarks.ViewBookmarksInteractor;
import view.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("UofT Marketplace");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new Marketplace());
            frame.setMinimumSize(new Dimension(780, 650));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    static final class Marketplace extends JPanel {
        private final InMemoryMarketplaceDataAccessObject store;
        private final LocalImageUploadGateway images = new LocalImageUploadGateway(
                Path.of(System.getProperty("java.io.tmpdir"), "uoft-marketplace-photos", UUID.randomUUID().toString()));

        Marketplace() {
            super(new BorderLayout(6, 8));
            User seller = new User("Demo", "Seller", "demo", "demo@utoronto.ca",
                    "CampusDemo1!", "", "", "St. George");
            store = new InMemoryMarketplaceDataAccessObject(seller);
            Listing textbook = new Listing();
            textbook.setName("CSC207 textbook");
            textbook.setDescription("Used course textbook with clean pages.");
            textbook.setPrice(30);
            textbook.setCondition("Good");
            textbook.setCategories(List.of("Books"));
            textbook.setCampus(seller.getCampus());
            textbook.setSeller(seller);
            try {
                store.saveListing(textbook);
            } catch (IOException error) {
                throw new IllegalStateException("Could not load the demo listing", error);
            }
            store.setCurrentUsername(null);
            showHome();
        }

        private static JButton button(String name, Runnable action) {
            JButton button = new JButton(name);
            button.addActionListener(event -> action.run());
            return button;
        }

        private void display(JPanel header, JPanel content) {
            removeAll();
            if (header != null) {
                add(header, BorderLayout.NORTH);
            }
            add(content, BorderLayout.CENTER);
            revalidate();
            repaint();
        }

        private JPanel backBar() {
            JPanel bar = new JPanel();
            bar.add(button("Back to marketplace", this::showHome));
            return bar;
        }

        private void showHome() {
            JPanel bar = new JPanel();
            if (store.getCurrentUsername() == null) {
                bar.add(button("Sign up", this::signup));
                bar.add(button("Log in", () -> login(this::showHome)));
            } else {
                bar.add(new JLabel("Signed in as " + store.getCurrentUsername()));
                bar.add(button("Create Listing", this::createListing));
                bar.add(button("My bookmarks", this::bookmarks));
                bar.add(button("My profile", () -> profile(store.getCurrentUsername())));
                bar.add(button("Log out", () -> {
                    store.setCurrentUsername(null);
                    showHome();
                }));
            }
            SearchListingsView search = new SearchListingsView(this::details);
            search.connect(new searchListingsController(new searchListingsInteractor(store, search::showResults)));
            display(bar, search);
        }

        private void login(Runnable afterLogin) {
            LoginViewModel model = new LoginViewModel();
            LoginController controller = new LoginController(new LoginInteractor(store, new LoginPresenter(model)));
            LoginView view = new LoginView(model, controller);
            model.addPropertyChangeListener(event -> {
                if (model.getState().getErrorMessage() == null) {
                    afterLogin.run();
                }
            });
            display(backBar(), view);
        }

        private void signup() {
            SignupViewModel model = new SignupViewModel();
            SignupPresenter presenter = new SignupPresenter(model, new LoginViewModel());
            SignupView view = new SignupView(model, new SignupController(new SignupInteractor(store, presenter)));
            model.addPropertyChangeListener(event -> {
                if (model.getState().getErrorMessage() == null) {
                    showHome();
                }
            });
            display(backBar(), view);
        }

        private void createListing() {
            CreateListingViewModel model = new CreateListingViewModel();
            CreateListingController controller = new CreateListingController(new CreateListingInteractor(
                    store, images, new CreateListingPresenter(model)));
            display(null, new CreateListingView(controller, model, this::showHome,
                    result -> details(result.getListingId())));
        }

        private void details(int id) {
            ViewListingDetailsViewModel model = new ViewListingDetailsViewModel();
            new ViewListingDetailsController(new ViewListingDetailsInteractor(
                    new ViewListingDetailsPresenter(model), store::getListing)).execute(id);
            if (model.getErrorMessage() != null) {
                JPanel error = new JPanel();
                error.add(new JLabel(model.getErrorMessage()));
                display(backBar(), error);
                return;
            }
            PublishedListingView item = new PublishedListingView(this::showHome);
            item.setListing(model);
            Listing listing = store.getListing(id);
            JPanel actions = new JPanel();
            actions.add(button("Seller profile", () -> profile(listing.getSeller().getUsername())));
            if (store.getCurrentUser().isPresent()) {
                BookmarkListingViewModel bookmark = new BookmarkListingViewModel();
                bookmark.setBookmarked(store.getCurrentUser().get().getBookmarks().contains(listing));
                BookmarkListingController toggle = new BookmarkListingController(new BookmarkListingInteractor(
                        store, new BookmarkListingPresenter(bookmark)));
                actions.add(new BookmarkListingView(id, toggle, bookmark));
                actions.add(button("My bookmarks", this::bookmarks));
            } else {
                actions.add(button("Log in to bookmark", () -> login(() -> details(id))));
            }
            display(actions, item);
        }

        private void bookmarks() {
            ViewBookmarksViewModel model = new ViewBookmarksViewModel();
            ViewBookmarksController controller = new ViewBookmarksController(new ViewBookmarksInteractor(
                    store, new ViewBookmarksPresenter(model)));
            display(backBar(), new ViewBookmarksView(controller, model, this::details));
            controller.execute();
        }

        private void profile(String username) {
            MarketplaceProfiles profiles = new MarketplaceProfiles(store);
            ProfileViewModel model = new ProfileViewModel();
            ProfilePresenter presenter = new ProfilePresenter(model);
            ProfileController controller = new ProfileController(
                    new ViewProfileInteractor(profiles, store::getCurrentUsername, presenter),
                    new EditProfileInteractor(profiles, profiles, store::getCurrentUsername, presenter));
            ProfilePanel panel = new ProfilePanel(model, controller);
            controller.viewProfile(username);
            display(backBar(), panel);
        }
    }
}
