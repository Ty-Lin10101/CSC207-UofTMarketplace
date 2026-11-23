package view;

import interface_adapter.view_bookmarks.ViewBookmarksController;
import interface_adapter.view_bookmarks.ViewBookmarksViewModel;
import use_case.view_bookmarks.BookmarkedListingData;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.util.List;

public class ViewBookmarksView extends JPanel {
    private final JTextArea listingsArea = new JTextArea(10, 30);

    public ViewBookmarksView(ViewBookmarksController controller,
                             ViewBookmarksViewModel viewModel) {
        setLayout(new BorderLayout());

        JButton bookmarksButton = new JButton("My bookmarks");
        listingsArea.setEditable(false);

        add(bookmarksButton, BorderLayout.NORTH);
        add(new JScrollPane(listingsArea), BorderLayout.CENTER);

        bookmarksButton.addActionListener(event -> controller.execute());
        viewModel.addPropertyChangeListener(event -> refresh(viewModel));
        refresh(viewModel);
    }

    private void refresh(ViewBookmarksViewModel viewModel) {
        listingsArea.setText("");

        List<BookmarkedListingData> bookmarks = viewModel.getBookmarks();
        for (BookmarkedListingData listing : bookmarks) {
            listingsArea.append(String.format("%s - $%.2f%n",
                    listing.getName(), listing.getPrice()));
        }

        listingsArea.append(viewModel.getMessage());
    }
}