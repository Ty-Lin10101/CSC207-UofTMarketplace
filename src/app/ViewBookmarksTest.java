package app;

import Entities.Listing;
import Entities.User;
import data_access.InMemoryBookmarkDataAccess;
import interface_adapter.view_bookmarks.ViewBookmarksController;
import interface_adapter.view_bookmarks.ViewBookmarksPresenter;
import interface_adapter.view_bookmarks.ViewBookmarksViewModel;
import use_case.view_bookmarks.ViewBookmarksInteractor;

public class ViewBookmarksTest {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("millie");

        Listing textbook = new Listing();
        textbook.setId(42);
        textbook.setName("Textbook");

        Listing calculator = new Listing();
        calculator.setId(43);
        calculator.setName("Calculator");

        InMemoryBookmarkDataAccess dataAccess = new InMemoryBookmarkDataAccess(user, textbook);
        dataAccess.addListing(calculator);

        ViewBookmarksViewModel viewModel = new ViewBookmarksViewModel();
        ViewBookmarksPresenter presenter = new ViewBookmarksPresenter(viewModel);
        ViewBookmarksInteractor interactor = new ViewBookmarksInteractor(dataAccess, presenter);
        ViewBookmarksController controller = new ViewBookmarksController(interactor);

        // 1. The marketplace has listings, but this user hasn't saved any.
        controller.execute();
        if (!viewModel.getBookmarks().isEmpty()
                || !"You haven't saved any listings yet".equals(viewModel.getMessage())) {
            throw new AssertionError("Expected an empty list and the empty-bookmarks message.");
        }

        // 2. Both saved listings should appear, and the old message should clear.
        user.addBookmark(textbook);
        user.addBookmark(calculator);
        controller.execute();
        if (viewModel.getBookmarks().size() != 2
                || viewModel.getBookmarks().get(0).getListingId() != 42
                || viewModel.getBookmarks().get(1).getListingId() != 43
                || !viewModel.getMessage().isEmpty()) {
            throw new AssertionError("Expected the textbook and calculator to appear.");
        }

        // 3. The user still has a bookmark for a listing the marketplace no longer has.
        Listing deletedListing = new Listing();
        deletedListing.setId(44);
        user.addBookmark(deletedListing);
        controller.execute();
        if (user.getBookmarks().contains(deletedListing)
                || viewModel.getBookmarks().size() != 2
                || viewModel.getBookmarks().get(0).getListingId() != 42
                || viewModel.getBookmarks().get(1).getListingId() != 43) {
            throw new AssertionError("Expected only the deleted bookmark to be removed.");
        }

        System.out.println("View bookmarks test passed.");
    }
}