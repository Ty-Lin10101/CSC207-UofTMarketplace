package use_case.bookmark_listing;

import Entities.Listing;
import Entities.User;

public class BookmarkListingInteractor implements BookmarkListingInputBoundary {
    private final BookmarkListingDataAccessInterface dataAccess;
    private final BookmarkListingOutputBoundary presenter;

    public BookmarkListingInteractor(BookmarkListingDataAccessInterface dataAccess, BookmarkListingOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(BookmarkListingInputData inputData) {
        String username = dataAccess.getCurrentUsername();
        User user = null;
        if (username != null) {
            user = dataAccess.getUser(username);
        }
        if (user == null) {
            presenter.prepareFailView("Log in to bookmark a listing.");
            return;
        }

        int listingId = inputData.getListingId();
        Listing listing = dataAccess.getListing(listingId);
        if (listing == null) {
            presenter.prepareFailView("This listing is no longer available");
            return;
        }

        Listing savedListing = findSavedListing(user, listingId);
        boolean bookmarked = savedListing == null;

        if (bookmarked) {
            user.addBookmark(listing);
        } else {
            user.removeBookmark(savedListing);
        }

        dataAccess.saveUser(user);
        presenter.prepareSuccessView(new BookmarkListingOutputData(listingId, bookmarked));
    }

    private Listing findSavedListing(User user, int listingId) {
        for (Listing savedListing : user.getBookmarks()) {
            if (savedListing.getId() == listingId) {
                return savedListing;
            }
        }
        return null;
    }
}