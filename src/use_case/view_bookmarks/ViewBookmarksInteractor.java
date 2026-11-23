package use_case.view_bookmarks;

import Entities.Listing;
import Entities.User;
import use_case.bookmark_listing.BookmarkListingDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class ViewBookmarksInteractor implements ViewBookmarksInputBoundary {
    private final BookmarkListingDataAccessInterface dataAccess;
    private final ViewBookmarksOutputBoundary presenter;

    public ViewBookmarksInteractor(BookmarkListingDataAccessInterface dataAccess,
                                   ViewBookmarksOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        String username = dataAccess.getCurrentUsername();
        User user = null;
        if (username != null) {
            user = dataAccess.getUser(username);
        }

        if (user == null) {
            presenter.prepareFailView("Log in to view your bookmarks.");
            return;
        }

        List<BookmarkedListingData> rows = new ArrayList<>();
        List<Listing> deletedBookmarks = new ArrayList<>();
        List<Listing> bookmarks = user.getBookmarks();

        for (int i = 0; i < bookmarks.size(); i++) {
            Listing savedListing = bookmarks.get(i);
            Listing listing = dataAccess.getListing(savedListing.getId());

            if (listing == null) {
                deletedBookmarks.add(savedListing);
            } else {
                rows.add(new BookmarkedListingData(
                        listing.getId(), listing.getName(), listing.getPrice()));
            }
        }

        if (deletedBookmarks.size() > 0) {
            for (int i = 0; i < deletedBookmarks.size(); i++) {
                Listing deletedListing = deletedBookmarks.get(i);
                user.removeBookmark(deletedListing);
            }
            dataAccess.saveUser(user);
        }
        presenter.prepareSuccessView(new ViewBookmarksOutputData(rows));
    }
}