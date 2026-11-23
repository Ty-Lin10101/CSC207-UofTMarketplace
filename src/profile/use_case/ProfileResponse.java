package profile.use_case;

import java.util.List;
import java.util.stream.Collectors;
import profile.entity.PublicProfile;

/** Output data contains public values only, with no mutable entity references. */
public final class ProfileResponse {
    public final String userId;
    public final String displayName;
    public final String picture;
    public final String bio;
    public final String campus;
    public final double rating;
    public final boolean editable;
    public final List<ListingData> listings;

    public ProfileResponse(PublicProfile profile, boolean editable) {
        userId = profile.userId;
        displayName = profile.details.displayName;
        picture = profile.details.picture;
        bio = profile.details.bio;
        campus = profile.details.campus;
        rating = profile.rating;
        this.editable = editable;
        listings = List.copyOf(profile.listings.stream().filter(item -> item.active)
                .map(item -> new ListingData(item.id, item.title, item.price))
                .collect(Collectors.toList()));
    }

    public static final class ListingData {
        public final String id;
        public final String title;
        public final double price;

        public ListingData(String id, String title, double price) {
            this.id = id;
            this.title = title;
            this.price = price;
        }
    }
}
