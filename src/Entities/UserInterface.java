import java.time.LocalDateTime;
import java.util.List;

public interface UserInterface {
    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getUsername();
    void setUsername(String username);

    String getEmail();
    void setEmail(String email);

    String getPassword();
    void setPassword(String password);

    boolean verifyPassword(String rawPassword);

    String getProfileImage();
    void setProfileImage(String profileImage);

    String getBio();
    void setBio(String bio);

    String getCampus();
    void setCampus(String campus);

    List<Listing> getListings();
    void setListings(List<Listing> listings);

    List<Listing> getBookmarks();
    void setBookmarks(List<Listing> bookmarks);

    List<Review> getReviews();
    void setReviews(List<Review> reviews);

    float getUserRating();
    void setUserRating(float userRating);

    LocalDateTime getAccountCreatedDate();
    void setAccountCreatedDate(LocalDateTime accountCreatedDate);

    void addListing(Listing listing);
    void addBookmark(Listing listing);
    boolean removeBookmark(Listing listing);
    void addReview(Review review);
}
