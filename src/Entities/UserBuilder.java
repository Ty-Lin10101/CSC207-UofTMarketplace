package Entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserBuilder {
    private UserProfile profile = new UserProfile();
    private String username;
    private String email;
    private String password;
    private List<Listing> listings = new ArrayList<>();
    private List<Listing> bookmarks = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private LocalDateTime accountCreatedDate = LocalDateTime.now();

    public UserBuilder firstName(String firstName) {
        profile.setFirstName(firstName);
        return this;
    }

    public UserBuilder lastName(String lastName) {
        profile.setLastName(lastName);
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder profileImage(String profileImage) {
        profile.setProfileImage(profileImage);
        return this;
    }

    public UserBuilder bio(String bio) {
        profile.setBio(bio);
        return this;
    }

    public UserBuilder campus(String campus) {
        profile.setCampus(campus);
        return this;
    }

    public UserBuilder profile(UserProfile profile) {
        this.profile = profile == null ? new UserProfile() : new UserProfile(profile);
        return this;
    }

    public UserBuilder listings(List<Listing> listings) {
        this.listings = listings == null ? new ArrayList<>() : new ArrayList<>(listings);
        return this;
    }

    public UserBuilder bookmarks(List<Listing> bookmarks) {
        this.bookmarks = bookmarks == null ? new ArrayList<>() : new ArrayList<>(bookmarks);
        return this;
    }

    public UserBuilder reviews(List<Review> reviews) {
        this.reviews = reviews == null ? new ArrayList<>() : new ArrayList<>(reviews);
        return this;
    }

    public UserBuilder accountCreatedDate(LocalDateTime accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
        return this;
    }

    public User build() {
        User user = new User();
        user.setProfile(profile);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setListings(listings);
        user.setBookmarks(bookmarks);
        user.setReviews(reviews);
        user.setAccountCreatedDate(accountCreatedDate);
        return user;
    }
}
