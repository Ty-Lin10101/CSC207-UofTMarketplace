package app;

import Entities.User;
import Entities.UserBuilder;
import data_access.InMemoryMarketplaceDataAccessObject;
import data_access.LocalImageUploadGateway;
import interface_adapter.create_listing.CreateListingController;
import interface_adapter.create_listing.CreateListingPresenter;
import interface_adapter.create_listing.CreateListingViewModel;
import use_case.create_listing.CreateListingInteractor;
import view.CreateListingView;
import view.PublishedListingView;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.UUID;

public final class Main {
    private static final String HOME = "home";
    private static final String CREATE_LISTING = "create listing";
    private static final String PUBLISHED_LISTING = "published listing";

    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::showMarketplace);
    }

    private static void showMarketplace() {
        // Use a local seller session until the shared login flow is connected.
        User seller = new UserBuilder()
                .firstName("Demo")
                .lastName("Seller")
                .username("demo-seller")
                .campus("St. George")
                .build();
        InMemoryMarketplaceDataAccessObject dataAccess = new InMemoryMarketplaceDataAccessObject(seller);
        Path photoDirectory = Path.of(System.getProperty("java.io.tmpdir"),
                "uoft-marketplace-photos", UUID.randomUUID().toString());

        CreateListingViewModel viewModel = new CreateListingViewModel();
        CreateListingPresenter presenter = new CreateListingPresenter(viewModel);
        CreateListingInteractor interactor = new CreateListingInteractor(
                dataAccess, new LocalImageUploadGateway(photoDirectory), presenter);
        CreateListingController controller = new CreateListingController(interactor);

        CardLayout navigation = new CardLayout();
        JPanel screens = new JPanel(navigation);
        Runnable showHome = () -> navigation.show(screens, HOME);
        PublishedListingView details = new PublishedListingView(showHome);
        CreateListingView form = new CreateListingView(controller, viewModel, showHome, listing -> {
            details.setListing(listing);
            navigation.show(screens, PUBLISHED_LISTING);
        });

        JButton createListing = new JButton("Create Listing");
        createListing.addActionListener(event -> navigation.show(screens, CREATE_LISTING));
        JPanel home = new JPanel(new BorderLayout(0, 16));
        home.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        home.add(new JLabel("Signed in as Demo Seller (local session)"), BorderLayout.NORTH);
        JPanel actions = new JPanel();
        actions.add(createListing);
        home.add(actions, BorderLayout.CENTER);

        screens.add(home, HOME);
        screens.add(form, CREATE_LISTING);
        screens.add(details, PUBLISHED_LISTING);

        JFrame frame = new JFrame("UofT Marketplace");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(screens);
        frame.setMinimumSize(new Dimension(620, 570));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
