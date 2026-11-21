package interface_adapter.ViewListingDetails;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ViewListingDetailsViewModel {
    private int id;
    private String title = "";
    private String description = "";
    private String priceText = "";
    private String condition = "";
    private List<String> categories = new ArrayList<>();
    private String campus = "";
    private String statusText = "";
    private String sellerName = "";
    private String sellerRatingText = "";
    private String mainImage;
    private List<String> imageGallery = new ArrayList<>();
    private String errorMessage;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setSellerRatingText(String sellerRatingText) {
        this.sellerRatingText = sellerRatingText;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setImageGallery(List<String> imageGallery) {
        this.imageGallery = imageGallery;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("listing", null, this);
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

    public String getPriceText() {
        return priceText;
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

    public String getStatusText() {
        return statusText;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerRatingText() {
        return sellerRatingText;
    }

    public String getMainImage() {
        return mainImage;
    }

    public List<String> getImageGallery() {
        return imageGallery;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public PropertyChangeSupport getSupport() {
        return support;
    }

    public void clearListing() {
        id = 0;
        title = "";
        description = "";
        priceText = "";
        condition = "";
        categories = new ArrayList<>();
        campus = "";
        statusText = "";
        sellerName = "";
        sellerRatingText = "";
        mainImage = null;
        imageGallery = new ArrayList<>();
    }

}