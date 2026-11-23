package data_access;

import Entities.Listing;
import Entities.User;
import profile.entity.ProfileDetails;
import profile.entity.ProfileListing;
import profile.entity.PublicProfile;
import profile.use_case.ProfileReader;
import profile.use_case.ProfileWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Presents the marketplace accounts and their actual listings to the profile use cases. */
public final class MarketplaceProfiles implements ProfileReader, ProfileWriter {
    private final InMemoryMarketplaceDataAccessObject store;

    public MarketplaceProfiles(InMemoryMarketplaceDataAccessObject store) {
        this.store = store;
    }

    @Override
    public Optional<PublicProfile> findById(String username) {
        User user = store.getUser(username);
        if (user == null) {
            return Optional.empty();
        }
        List<ProfileListing> items = new ArrayList<>();
        for (Listing item : user.getListings()) {
            items.add(new ProfileListing(String.valueOf(item.getId()), item.getName(), item.getPrice(),
                    item.getStatus() == Listing.Status.Active));
        }
        String name = (user.getFirstName() + " " + user.getLastName()).trim();
        ProfileDetails details = new ProfileDetails(name, user.getProfileImage(), user.getBio(), user.getCampus());
        return Optional.of(new PublicProfile(username, details, user.getUserRating(), items));
    }

    @Override
    public Optional<PublicProfile> updateDetails(String username, ProfileDetails details) {
        User user = store.getUser(username);
        if (user == null || !username.equals(store.getCurrentUsername())) {
            return Optional.empty();
        }
        String[] names = details.displayName.split(" ", 2);
        user.setFirstName(names[0]);
        user.setLastName(names.length == 2 ? names[1] : "");
        user.setProfileImage(details.picture);
        user.setBio(details.bio);
        user.setCampus(details.campus);
        return findById(username);
    }
}
