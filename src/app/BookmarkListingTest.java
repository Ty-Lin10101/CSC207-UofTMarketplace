package app;

import Entities.Listing;
import Entities.User;
import data_access.InMemoryBookmarkDataAccess;
import interface_adapter.bookmark_listing.BookmarkListingController;
import interface_adapter.bookmark_listing.BookmarkListingPresenter;
import interface_adapter.bookmark_listing.BookmarkListingViewModel;
import use_case.bookmark_listing.BookmarkListingInteractor;

public class BookmarkListingTest {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("millie");

        Listing textbook = new Listing();
        textbook.setId(42);

        InMemoryBookmarkDataAccess dataAccess = new InMemoryBookmarkDataAccess(user, textbook);
        BookmarkListingViewModel viewModel = new BookmarkListingViewModel();
        BookmarkListingPresenter presenter = new BookmarkListingPresenter(viewModel);
        BookmarkListingInteractor interactor = new BookmarkListingInteractor(dataAccess, presenter);
        BookmarkListingController controller = new BookmarkListingController(interactor);

        // First click should save the textbook.
        controller.execute(textbook.getId());
        if (!user.getBookmarks().contains(textbook)) {
            throw new AssertionError("The first click should save the textbook.");
        }

        // Second click should remove it.
        controller.execute(textbook.getId());
        if (!user.getBookmarks().isEmpty()) {
            throw new AssertionError("The second click should remove the textbook.");
        }

        System.out.println("UC9 passed: the textbook was saved, then removed.");
    }
}