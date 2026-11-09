import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListingService implements ListingInterface {
    private final Listing listing;

    public ListingService(Listing listing) {
        if (listing == null) {
            throw new IllegalArgumentException("Listing cannot be null.");
        }
        this.listing = listing;
        if (listing.getImageGallery() == null) {
            listing.setImageGallery(new ArrayList<>());
        }
        if (listing.getMainImage() != null && !listing.getMainImage().trim().isEmpty() && !listing.getImageGallery().contains(listing.getMainImage())) {
            listing.getImageGallery().add(listing.getMainImage());
        }
    }

    @Override
    public int getId() {
        return listing.getId();
    }

    @Override
    public String getName() {
        return listing.getName();
    }

    @Override
    public void setName(String name) {
        listing.setName(name);
    }

    @Override
    public String getDescription() {
        return listing.getDescription();
    }

    @Override
    public void setDescription(String description) {
        listing.setDescription(description);
    }

    @Override
    public double getPrice() {
        return listing.getPrice();
    }

    @Override
    public void setPrice(double price) {
        listing.setPrice(price);
    }

    @Override
    public String getCondition() {
        return listing.getCondition();
    }

    @Override
    public void setCondition(String condition) {
        listing.setCondition(condition);
    }

    @Override
    public List<String> getCategories() {
        return listing.getCategories();
    }

    @Override
    public void setCategories(List<String> categories) {
        listing.setCategories(categories);
    }

    @Override
    public String getCampus() {
        return listing.getCampus();
    }

    @Override
    public void setCampus(String campus) {
        listing.setCampus(campus);
    }

    @Override
    public Listing.Status getStatus() {
        return listing.getStatus();
    }

    @Override
    public void setStatus(Listing.Status status) {
        listing.setStatus(status);
    }

    @Override
    public User getSeller() {
        return listing.getSeller();
    }

    @Override
    public void setSeller(User seller) {
        listing.setSeller(seller);
    }

    @Override
    public String getMainImage() {
        return listing.getMainImage();
    }

    @Override
    public void setMainImage(String mainImage) {
        listing.setMainImage(mainImage);
        if (mainImage != null && !mainImage.trim().isEmpty()) {
            if (!listing.getImageGallery().contains(mainImage)) {
                addImage(mainImage);
            }
            listing.setMainImage(mainImage);
        }
    }

    @Override
    public List<String> getImageGallery() {
        return listing.getImageGallery();
    }

    @Override
    public void setImageGallery(List<String> imageGallery) {
        listing.setImageGallery(imageGallery);
        if (listing.getMainImage() == null || listing.getMainImage().trim().isEmpty()) {
            if (!listing.getImageGallery().isEmpty()) {
                listing.setMainImage(listing.getImageGallery().get(0));
            }
        }
    }

    @Override
    public LocalDateTime getTimePosted() {
        return listing.getTimePosted();
    }

    @Override
    public void setTimePosted(LocalDateTime timePosted) {
        listing.setTimePosted(timePosted);
    }

    @Override
    public boolean addImage(String image) {
        if (image == null || image.trim().isEmpty()) {
            return false;
        }

        List<String> gallery = listing.getImageGallery();
        if (gallery == null) {
            gallery = new ArrayList<>();
            listing.setImageGallery(gallery);
        }

        if (gallery.size() >= 10) {
            return false;
        }

        if (!gallery.contains(image)) {
            gallery.add(image);
        }

        if (listing.getMainImage() == null || listing.getMainImage().trim().isEmpty()) {
            listing.setMainImage(image);
        }

        return true;
    }
}