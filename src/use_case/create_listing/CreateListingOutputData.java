package use_case.create_listing;

import Entities.Listing;
import Entities.User;

import java.util.List;

public final class CreateListingOutputData {
    private final int listingId;
    private final String title;
    private final String description;
    private final double price;
    private final String category;
    private final String condition;
    private final String campus;
    private final String status;
    private final List<String> photos;
    private final String sellerName;

    CreateListingOutputData(Listing listing) {
        this.listingId = listing.getId();
        this.title = listing.getName();
        this.description = listing.getDescription();
        this.price = listing.getPrice();
        this.category = listing.getCategories().get(0);
        this.condition = listing.getCondition();
        this.campus = listing.getCampus();
        this.status = listing.getStatus().name();
        this.photos = List.copyOf(listing.getImageGallery());

        User seller = listing.getSeller();
        String firstName = seller.getFirstName() == null ? "" : seller.getFirstName();
        String lastName = seller.getLastName() == null ? "" : seller.getLastName();
        String fullName = (firstName + " " + lastName).strip();
        this.sellerName = fullName.isEmpty() ? seller.getUsername() : fullName;
    }

    public int getListingId() {
        return listingId;
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

    public String getCategory() {
        return category;
    }

    public String getCondition() {
        return condition;
    }

    public String getCampus() {
        return campus;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getSellerName() {
        return sellerName;
    }
}
