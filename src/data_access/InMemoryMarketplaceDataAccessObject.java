package data_access;

import Entities.Listing;
import Entities.User;
import use_case.create_listing.CreateListingDataAccessInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class InMemoryMarketplaceDataAccessObject implements CreateListingDataAccessInterface {
    private final User currentUser;
    private final Map<Integer, Listing> listings = new HashMap<>();
    private int nextId = 1;

    public InMemoryMarketplaceDataAccessObject(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    @Override
    public synchronized void saveListing(Listing listing) throws IOException {
        if (currentUser == null || listing.getSeller() != currentUser) {
            throw new IOException("The active seller does not own this listing.");
        }
        if (listing.getId() != 0) {
            throw new IOException("The listing already has an ID.");
        }

        listing.setId(nextId++);
        currentUser.addListing(listing);
        listings.put(listing.getId(), listing);
    }

    public synchronized Optional<Listing> findListing(int listingId) {
        return Optional.ofNullable(listings.get(listingId));
    }
}
