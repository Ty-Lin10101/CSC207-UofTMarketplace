package profile.entity;

import java.util.List;
import java.util.Objects;

/** Public account projection. Account authentication remains in UC1/UC2. */
public final class PublicProfile {
    public final String userId;
    public final ProfileDetails details;
    public final double rating;
    public final List<ProfileListing> listings;

    public PublicProfile(String userId, ProfileDetails details, double rating,
                         List<ProfileListing> listings) {
        this.userId = Objects.requireNonNull(userId);
        this.details = Objects.requireNonNull(details);
        this.rating = rating;
        this.listings = List.copyOf(listings);
    }

    public PublicProfile withDetails(ProfileDetails updated) {
        return new PublicProfile(userId, updated, rating, listings);
    }
}
