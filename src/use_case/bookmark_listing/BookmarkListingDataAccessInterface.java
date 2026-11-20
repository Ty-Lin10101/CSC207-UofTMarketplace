package use_case.bookmark_listing;

import Entities.Listing;
import Entities.User;

public interface BookmarkListingDataAccessInterface {
    String getCurrentUsername();
    User getUser(String username);
    Listing getListing(int listingId);

    void saveUser(User user);
}
