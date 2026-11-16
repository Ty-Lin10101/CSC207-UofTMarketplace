import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListingBuilder {
    private int id;
    private String name;
    private String description;
    private double price;
    private String condition;
    private List<String> categories = new ArrayList<>();
    private String campus;
    private Listing.Status status = Listing.Status.Active;
    private User seller;
    private ListingImages images = new ListingImages();
    private LocalDateTime timePosted = LocalDateTime.now();

    public ListingBuilder id(int id) {
        this.id = id;
        return this;
    }

    public ListingBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ListingBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ListingBuilder price(double price) {
        this.price = price;
        return this;
    }

    public ListingBuilder condition(String condition) {
        this.condition = condition;
        return this;
    }

    public ListingBuilder categories(List<String> categories) {
        this.categories = categories == null ? new ArrayList<>() : new ArrayList<>(categories);
        return this;
    }

    public ListingBuilder campus(String campus) {
        this.campus = campus;
        return this;
    }

    public ListingBuilder status(Listing.Status status) {
        this.status = status;
        return this;
    }

    public ListingBuilder seller(User seller) {
        this.seller = seller;
        return this;
    }

    public ListingBuilder mainImage(String mainImage) {
        images.setMainImage(mainImage);
        return this;
    }

    public ListingBuilder imageGallery(List<String> imageGallery) {
        images.setGallery(imageGallery);
        return this;
    }

    public ListingBuilder images(ListingImages images) {
        this.images = images == null ? new ListingImages() : new ListingImages(images);
        return this;
    }

    public ListingBuilder timePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
        return this;
    }

    public Listing build() {
        Listing listing = new Listing();
        listing.setId(id);
        listing.setName(name);
        listing.setDescription(description);
        listing.setPrice(price);
        listing.setCondition(condition);
        listing.setCategories(categories);
        listing.setCampus(campus);
        listing.setStatus(status);
        listing.setSeller(seller);
        listing.setImages(images);
        listing.setTimePosted(timePosted);
        return listing;
    }
}
