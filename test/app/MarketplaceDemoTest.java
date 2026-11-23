package app;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

/** Exercises the same panels and buttons as app.Main, without requiring a display. */
public final class MarketplaceDemoTest {
    private Main.Marketplace app;

    public static void main(String[] args) throws Exception {
        MarketplaceDemoTest test = new MarketplaceDemoTest();
        SwingUtilities.invokeAndWait(test::publishAsSeller);
        // Publishing copies photos and saves the listing on a SwingWorker.
        for (int retry = 0; retry < 200; retry++) {
            boolean[] ready = {false};
            SwingUtilities.invokeAndWait(() -> ready[0] = test.button("Seller profile") != null);
            if (ready[0]) {
                SwingUtilities.invokeAndWait(test::findAsBuyer);
                System.out.println("Marketplace end-to-end demo passed.");
                return;
            }
            Thread.sleep(25);
        }
        throw new AssertionError("Publishing did not open the listing details.");
    }

    private void publishAsSeller() {
        app = new Main.Marketplace();
        click("Sign up");
        field("First Name:", "Campus");
        field("Last Name:", "Seller");
        field("Username:", "alice");
        field("UofT Email:", "alice@utoronto.ca");
        field("Password:", "DemoTest1!");
        field("Confirm Password:", "DemoTest1!");
        choose("Campus:", "St. George");
        click("Sign Up");
        click("Create Listing");
        field("Title", "Blue bike");
        field("Description", "A touring bicycle");
        field("Price (CAD)", "25");
        choose("Category", "Bikes & gear");
        choose("Condition", "Good");
        choose("Campus", "St. George");
        click("Publish");
    }

    private void findAsBuyer() {
        click("Back to Marketplace");
        click("Log out");
        field("Keyword", "touring");
        field("Minimum price", "26");
        click("Search");
        check(button("Blue bike — $25.00") == null, "Price filter should hide the bike");
        field("Minimum price", "");
        choose("Sort by", "Price: low to high");
        click("Blue bike — $25.00");
        click("Log in to bookmark");
        List<JTextField> inputs = components(app, JTextField.class);
        inputs.get(0).setText("demo@utoronto.ca");
        inputs.get(1).setText("wrong");
        click("Log In");
        check(button("Log In") != null, "Wrong password should keep login open");
        inputs.get(1).setText("CampusDemo1!");
        click("Log In");
        click("Seller profile");
        check(!button("Edit profile").isEnabled(), "Buyer should not be able to edit the seller");
        click("Back to marketplace");
        click("Blue bike — $25.00");
        click("Bookmark");
        click("My bookmarks");
        click("Blue bike — $25.00");
        click("Bookmarked");
        click("My bookmarks");
        check(button("Blue bike — $25.00") == null, "Removed listing still appears in bookmarks");
        check(components(app, JLabel.class).stream().anyMatch(label ->
                "You haven't saved any listings yet".equals(label.getText())), "Missing empty state");
        click("Back to marketplace");
        click("My profile");
        click("Edit profile");
        components(app, JTextArea.class).get(0).setText("Updated demo bio");
        click("Save");
        click("Back to marketplace");
        click("My profile");
        check("Updated demo bio".equals(components(app, JTextArea.class).get(0).getText()), "Bio was not saved");
    }

    private JButton button(String text) {
        for (JButton button : components(app, JButton.class)) {
            if (text.equals(button.getText())) return button;
        }
        return null;
    }

    private void click(String text) {
        JButton button = button(text);
        check(button != null && button.isEnabled(), "Missing or disabled button: " + text);
        button.doClick(0);
    }

    private Component input(String text) {
        for (JLabel label : components(app, JLabel.class)) {
            if (text.equals(label.getText())) return label.getLabelFor();
        }
        throw new AssertionError("Missing label: " + text);
    }

    private void field(String label, String value) {
        ((JTextComponent) input(label)).setText(value);
    }

    private void choose(String label, String value) {
        ((JComboBox<?>) input(label)).setSelectedItem(value);
    }

    private static <T extends Component> List<T> components(Container root, Class<T> type) {
        List<T> found = new ArrayList<>();
        for (Component child : root.getComponents()) {
            if (type.isInstance(child)) found.add(type.cast(child));
            if (child instanceof Container) found.addAll(components((Container) child, type));
        }
        return found;
    }

    private static void check(boolean passed, String message) {
        if (!passed) throw new AssertionError(message);
    }
}
