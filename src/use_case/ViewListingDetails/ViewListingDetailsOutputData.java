package use_case.ViewListingDetails;
import Entities.Listing;

import java.util.List;

public class ViewListingDetailsOutputData {

    private int id;
    private final String title;
    private String description;
    private double price;
    private String condition;
    private List<String> categories;
    private String campus;
    private Listing.Status status;
    private String sellerName;
    private float sellerRating;

    private String mainImage;
    private List<String> imageGallery;

    public ViewListingDetailsOutputData(int id, String title, String description, double price, String condition, List<String> categories, String campus, Listing.Status status, String sellerName, float sellerRating, String mainImage, List<String> imageGallery) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.categories = categories;
        this.campus = campus;
        this.status = status;
        this.sellerName = sellerName;
        this.sellerRating = sellerRating;
        this.mainImage = mainImage;
        this.imageGallery = imageGallery;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getCampus() {
        return campus;
    }

    public Listing.Status getStatus() {
        return status;
    }

    public String getSellerName() {
        return sellerName;
    }

    public float getSellerRating() {
        return sellerRating;
    }

    public String getMainImage() {
        return mainImage;
    }

    public List<String> getImageGallery() {
        return imageGallery;
    }
}