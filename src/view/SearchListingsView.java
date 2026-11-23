package view;

import Entities.Listing;
import interface_adapter.searchListings.searchListingsController;
import use_case.searchListings.SearchListingFilters;
import use_case.searchListings.searchListingOutputData;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntConsumer;

public final class SearchListingsView extends JPanel {
    private final JTextField keyword = new JTextField(18);
    private final JTextField category = new JTextField();
    private final JTextField condition = new JTextField();
    private final JTextField min = new JTextField();
    private final JTextField max = new JTextField();
    private final JComboBox<String> sort = new JComboBox<>(new String[]{"Newest", "Price: low to high", "Price: high to low"});
    private final JPanel rows = new JPanel();
    private final JLabel message = new JLabel(" ");
    private final IntConsumer openListing;
    private searchListingsController controller;

    public SearchListingsView(IntConsumer openListing) {
        super(new BorderLayout(8, 8));
        this.openListing = openListing;
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        JPanel fields = new JPanel(new GridLayout(0, 2, 6, 4));
        addField(fields, "Keyword", keyword);
        addField(fields, "Category", category);
        addField(fields, "Condition", condition);
        addField(fields, "Minimum price", min);
        addField(fields, "Maximum price", max);
        addField(fields, "Sort by", sort);
        JButton search = new JButton("Search");
        fields.add(search);
        fields.add(message);
        add(fields, BorderLayout.NORTH);
        add(new JScrollPane(rows), BorderLayout.CENTER);
        search.addActionListener(event -> search());
        keyword.addActionListener(event -> search());
        sort.addActionListener(event -> search());
    }

    public void connect(searchListingsController controller) {
        this.controller = controller;
        search();
    }

    private static void addField(JPanel fields, String name, JComponent input) {
        JLabel label = new JLabel(name);
        label.setLabelFor(input);
        fields.add(label);
        fields.add(input);
    }

    private static Double price(JTextField field) {
        String value = field.getText().trim();
        return value.isEmpty() ? null : Double.valueOf(value);
    }

    private void search() {
        try {
            SearchListingFilters filters = new SearchListingFilters(price(min), price(max),
                    category.getText(), condition.getText(), null);
            controller.execute(keyword.getText(), filters);
        } catch (NumberFormatException error) {
            message.setText("Enter a number for the price.");
        } catch (IllegalArgumentException error) {
            message.setText(error.getMessage());
        }
    }

    public void showResults(searchListingOutputData result) {
        List<Listing> items = result.getListings();
        Comparator<Listing> order = Comparator.comparing(Listing::getTimePosted).reversed();
        if (sort.getSelectedIndex() == 1) {
            order = Comparator.comparingDouble(Listing::getPrice);
        } else if (sort.getSelectedIndex() == 2) {
            order = Comparator.comparingDouble(Listing::getPrice).reversed();
        }
        items.sort(order);
        rows.removeAll();
        for (Listing item : items) {
            JButton open = new JButton(String.format("%s — $%.2f", item.getName(), item.getPrice()));
            open.addActionListener(event -> openListing.accept(item.getId()));
            rows.add(open);
        }
        message.setText(items.isEmpty() ? "No listings found" : items.size() + " found");
        rows.revalidate();
        rows.repaint();
    }
}
