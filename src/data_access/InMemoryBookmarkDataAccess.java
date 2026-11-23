package data_access;

import Entities.Listing;
import Entities.User;
import use_case.bookmark_listing.BookmarkListingDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

public class InMemoryBookmarkDataAccess implements BookmarkListingDataAccessInterface {
    private User currentUser;
    private final List<Listing> listings = new ArrayList<>();

    public InMemoryBookmarkDataAccess(User currentUser, Listing listing) {
        this.currentUser = currentUser;
        addListing(listing);
    }

    public void addListing(Listing listing) {
        listings.add(listing);
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
        for (int i = 0; i < listings.size(); i++) {
            Listing listing = listings.get(i);
            if (listing.getId() == listingId) {
                return listing;
            }
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        this.currentUser = user;
    }
}