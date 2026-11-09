import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String profileImage;
    private String bio;
    private String campus;
    private List<Listing> listings;
    private List<Listing> bookmarks;
    private List<Review> reviews;
    private float userRating;
    private LocalDateTime accountCreatedDate;

    public User() {
        this.listings = new ArrayList<>();
        this.bookmarks = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.userRating = 0.0f;
        this.accountCreatedDate = LocalDateTime.now();
    }

    public User(String firstName, String lastName, String username, String email, String password,
                String profileImage, String bio, String campus) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.bio = bio;
        this.campus = campus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings != null ? listings : new ArrayList<>();
    }

    public List<Listing> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Listing> bookmarks) {
        this.bookmarks = bookmarks != null ? bookmarks : new ArrayList<>();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews != null ? reviews : new ArrayList<>();
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public LocalDateTime getAccountCreatedDate() {
        return accountCreatedDate;
    }

    public void setAccountCreatedDate(LocalDateTime accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
    }
}
