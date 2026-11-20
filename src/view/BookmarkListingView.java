package view;

import interface_adapter.bookmark_listing.BookmarkListingController;
import interface_adapter.bookmark_listing.BookmarkListingViewModel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BookmarkListingView extends JPanel {
    private final JButton bookmarkButton = new JButton("Bookmark");
    private final JLabel errorLabel = new JLabel();

    public BookmarkListingView(int listingId,
                               BookmarkListingController controller,
                               BookmarkListingViewModel viewModel) {
        add(bookmarkButton);
        add(errorLabel);

        bookmarkButton.addActionListener(event -> controller.execute(listingId));
        viewModel.addPropertyChangeListener(event -> refresh(viewModel));
        refresh(viewModel);
    }

    private void refresh(BookmarkListingViewModel viewModel) {
        if (viewModel.isBookmarked()) {
            bookmarkButton.setText("Bookmarked");
        } else {
            bookmarkButton.setText("Bookmark");
        }

        errorLabel.setText(viewModel.getErrorMessage());
    }
}
