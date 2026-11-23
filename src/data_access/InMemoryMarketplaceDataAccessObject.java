package data_access;

import Entities.Listing;
import Entities.User;
import use_case.create_listing.CreateListingDataAccessInterface;

import java.io.IOException;
import use_case.bookmark_listing.BookmarkListingDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.searchListings.searchListingDataAccessInterface;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class InMemoryMarketplaceDataAccessObject implements CreateListingDataAccessInterface,
        BookmarkListingDataAccessInterface, SignupUserDataAccessInterface, LoginUserDataAccessInterface,
        searchListingDataAccessInterface {
    private User currentUser;
    private final Map<String, User> users = new LinkedHashMap<>();
    private final Map<Integer, Listing> listings = new LinkedHashMap<>();
    private int nextId = 1;

    public InMemoryMarketplaceDataAccessObject(User currentUser) {
        this.currentUser = currentUser;
        if (currentUser != null) {
            save(currentUser);
        }
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

    @Override
    public String getCurrentUsername() {
        return currentUser == null ? null : currentUser.getUsername();
    }

    @Override
    public void setCurrentUsername(String username) {
        currentUser = users.get(username);
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public User getByEmail(String email) {
        for (User user : users.values()) {
            if (email.equalsIgnoreCase(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return getByEmail(email) != null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public void saveUser(User user) {
        save(user);
    }

    @Override
    public Listing getListing(int id) {
        return findListing(id).orElse(null);
    }

    @Override
    public synchronized ArrayList<Listing> findListingsByKeyword(String keyword) {
        String term = keyword.toLowerCase(Locale.ROOT);
        ArrayList<Listing> matches = new ArrayList<>();
        for (Listing item : listings.values()) {
            if (item.getName().toLowerCase(Locale.ROOT).contains(term)
                    || item.getDescription().toLowerCase(Locale.ROOT).contains(term)) {
                matches.add(item);
            }
        }
        return matches;
    }
}
