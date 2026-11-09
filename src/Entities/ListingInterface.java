import java.time.LocalDateTime;
import java.util.List;

public interface ListingInterface {
    int getId();

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    double getPrice();
    void setPrice(double price);

    String getCondition();
    void setCondition(String condition);

    List<String> getCategories();
    void setCategories(List<String> categories);

    String getCampus();
    void setCampus(String campus);

    Listing.Status getStatus();
    void setStatus(Listing.Status status);

    User getSeller();
    void setSeller(User seller);

    String getMainImage();
    void setMainImage(String mainImage);

    List<String> getImageGallery();
    void setImageGallery(List<String> imageGallery);

    LocalDateTime getTimePosted();
    void setTimePosted(LocalDateTime timePosted);

    boolean addImage(String image);
}
