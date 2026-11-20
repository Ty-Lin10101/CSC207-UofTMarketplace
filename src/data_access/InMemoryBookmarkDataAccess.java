package data_access;

import Entities.Listing;
import Entities.User;
import use_case.bookmark_listing.BookmarkListingDataAccessInterface;

public class InMemoryBookmarkDataAccess implements BookmarkListingDataAccessInterface {
    private User currentUser;
    private final Listing listing;

    public InMemoryBookmarkDataAccess(User currentUser, Listing listing) {
        this.currentUser = currentUser;
        this.listing = listing;
    }

    @Override
    public String getCurrentUsername() {
        if (currentUser == null) {
            return null;
        }
        return currentUser.getUsername();
    }

    @Override
    public User getUser(String username) {
        if (username != null && username.equals(getCurrentUsername())) {
            return currentUser;
        }
        return null;
    }

    @Override
    public Listing getListing(int listingId) {
        if (listing.getId() == listingId) {
            return listing;
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        this.currentUser = user;
    }
}