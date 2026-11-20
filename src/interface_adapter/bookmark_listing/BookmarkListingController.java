package interface_adapter.bookmark_listing;

import use_case.bookmark_listing.BookmarkListingInputBoundary;
import use_case.bookmark_listing.BookmarkListingInputData;

public class BookmarkListingController {
    private final BookmarkListingInputBoundary interactor;

    public BookmarkListingController(BookmarkListingInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(int listingId) {
        BookmarkListingInputData inputData = new BookmarkListingInputData(listingId);
        interactor.execute(inputData);
    }
}
