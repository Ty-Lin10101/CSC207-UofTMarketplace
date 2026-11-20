package Entities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListingImages {
    private String mainImage;
    private List<String> gallery;

    public ListingImages() {
        this.gallery = new ArrayList<>();
    }

    public ListingImages(String mainImage) {
        this();
        setMainImage(mainImage);
    }

    public ListingImages(ListingImages other) {
        this.mainImage = other.mainImage;
        this.gallery = new ArrayList<>(other.gallery);
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        if (!gallery.contains(mainImage)) {
            gallery.add(mainImage);
        }
        this.mainImage = mainImage;
    }

    public List<String> getGallery() {
        return Collections.unmodifiableList(new ArrayList<>(gallery));
    }

    public void setGallery(List<String> gallery) {
        List<String> replacement = new ArrayList<>();
        if (gallery != null) {
            for (String image : gallery) {
                    replacement.add(image);
                }
            }
        this.gallery = replacement;
        if (mainImage == null || !this.gallery.contains(mainImage)) {
            mainImage = this.gallery.isEmpty() ? null : this.gallery.get(0);
        }
    }

    public boolean addImage(String image) {
        if (image == null || image.trim().isEmpty()) {
            return false;
        }
        if (gallery.contains(image)) {
            return true;
        }
        if (gallery.size() >= 10) {
            return false;
        }
        gallery.add(image);
        if (mainImage == null || mainImage.trim().isEmpty()) {
            mainImage = image;
        }
        return true;
    }
}
