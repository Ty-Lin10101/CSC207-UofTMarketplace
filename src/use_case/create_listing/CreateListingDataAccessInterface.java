package use_case.create_listing;

import Entities.Listing;
import Entities.User;

import java.io.IOException;
import java.util.Optional;

public interface CreateListingDataAccessInterface {
    Optional<User> getCurrentUser();

    /**
     * Assigns an ID and saves the listing with its seller as one operation.
     * A failed save leaves both the listing store and the seller unchanged.
     */
    void saveListing(Listing listing) throws IOException;
}
