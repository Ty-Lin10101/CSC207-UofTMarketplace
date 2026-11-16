import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Listing {
    public enum Status {
        Active,
        Pending,
        Sold
    }

    private int id;
    private String name;
    private String description;
    private double price;
    private String condition;
    private List<String> categories;
    private String campus;
    private Status status;
    private User seller;
    private ListingImages images;
    private LocalDateTime timePosted;

    public Listing() {
        this.categories = new ArrayList<>();
        this.images = new ListingImages();
        this.status = Status.Active;
        this.timePosted = LocalDateTime.now();
    }

    public Listing(String name, String description, double price, String condition,
                   List<String> categories, String campus, User seller, String mainImage) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.categories = categories != null ? new ArrayList<>(categories) : new ArrayList<>();
        this.campus = campus;
        this.seller = seller;
        this.images = new ListingImages(mainImage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories != null ? new ArrayList<>(categories) : new ArrayList<>();
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getMainImage() {
        return images.getMainImage();
    }

    public void setMainImage(String mainImage) {
        images.setMainImage(mainImage);
    }

    public List<String> getImageGallery() {
        return images.getGallery();
    }

    public void setImageGallery(List<String> imageGallery) {
        images.setGallery(imageGallery);
    }

    public ListingImages getImages() {
        return images;
    }

    public void setImages(ListingImages images) {
        this.images = images == null ? new ListingImages() : new ListingImages(images);
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
    }

    public boolean addImage(String image) {
        return images.addImage(image);
    }
}