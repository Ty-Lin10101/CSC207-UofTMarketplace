package Entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private UserProfile profile;
    private String username;
    private String email;
    private String password;
    private List<Listing> listings;
    private List<Listing> bookmarks;
    private List<Review> reviews;
    private float userRating;
    private LocalDateTime accountCreatedDate;

    public User() {
        this.profile = new UserProfile();
        this.listings = new ArrayList<>();
        this.bookmarks = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.userRating = 0.0f;
        this.accountCreatedDate = LocalDateTime.now();
    }

    public User(String firstName, String lastName, String username, String email, String password,
                String profileImage, String bio, String campus) {
        this();
        this.profile = new UserProfile(firstName, lastName, profileImage, bio, campus);
        this.username = username;
        this.email = email;
        setPassword(password);
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile == null ? new UserProfile() : new UserProfile(profile);
    }

    public String getFirstName() {
        return profile.getFirstName();
    }

    public void setFirstName(String firstName) {
        profile.setFirstName(firstName);
    }

    public String getLastName() {
        return profile.getLastName();
    }

    public void setLastName(String lastName) {
        profile.setLastName(lastName);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : PasswordHasher.hash(password);
    }

    public String getProfileImage() {
        return profile.getProfileImage();
    }

    public void setProfileImage(String profileImage) {
        profile.setProfileImage(profileImage);
    }

    public String getBio() {
        return profile.getBio();
    }

    public void setBio(String bio) {
        profile.setBio(bio);
    }

    public String getCampus() {
        return profile.getCampus();
    }

    public void setCampus(String campus) {
        profile.setCampus(campus);
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings != null ? new ArrayList<>(listings) : new ArrayList<>();
    }

    public List<Listing> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Listing> bookmarks) {
        this.bookmarks = bookmarks != null ? new ArrayList<>(bookmarks) : new ArrayList<>();
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(new ArrayList<>(reviews));
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>();
        if (reviews != null) {
            for (Review review : reviews) {
                if (review != null) {
                    this.reviews.add(review);
                }
            }
        }
        updateUserRating();
    }

    public float getUserRating() {
        return userRating;
    }

    public LocalDateTime getAccountCreatedDate() {
        return accountCreatedDate;
    }

    public void setAccountCreatedDate(LocalDateTime accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
    }

    public void addListing(Listing listing) {
        if (listing != null && !listings.contains(listing)) {
            listings.add(listing);
        }
    }

    public void addBookmark(Listing listing) {
        if (listing != null && !bookmarks.contains(listing)) {
            bookmarks.add(listing);
        }
    }

    public boolean removeBookmark(Listing listing) {
        return listing != null && bookmarks.remove(listing);
    }

    public void addReview(Review review) {
        if (review != null && !reviews.contains(review)) {
            reviews.add(review);
            updateUserRating();
        }
    }

    private void updateUserRating() {
        if (reviews.isEmpty()) {
            userRating = 0.0f;
            return;
        }

        float totalStars = 0.0f;
        for (Review review : reviews) {
            totalStars += review.getStarRating();
        }
        userRating = totalStars / reviews.size();
    }
}
