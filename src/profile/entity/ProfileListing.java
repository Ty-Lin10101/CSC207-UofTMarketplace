package profile.entity;

/** Immutable public listing summary supplied by the listings adapter. */
public final class ProfileListing {
    public final String id;
    public final String title;
    public final double price;
    public final boolean active;

    public ProfileListing(String id, String title, double price, boolean active) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.active = active;
    }
}
