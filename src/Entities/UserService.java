import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserService implements UserInterface {
    private static final Set<String> USED_USERNAMES = new HashSet<>();
    private final User user;

    public UserService(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        this.user = user;
        if (user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
            USED_USERNAMES.add(user.getUsername().trim());
        }
    }

    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        String trimmedUsername = username.trim();
        String oldUsername = user.getUsername();

        if (oldUsername != null && oldUsername.equals(trimmedUsername)) {
            return;
        }
        if (USED_USERNAMES.contains(trimmedUsername)) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (oldUsername != null) {
            USED_USERNAMES.remove(oldUsername);
        }

        user.setUsername(trimmedUsername);
        USED_USERNAMES.add(trimmedUsername);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        if (email == null || !isUofTEmail(email)) {
            throw new IllegalArgumentException("Email must be a valid UofT email address.");
        }
        user.setEmail(email.trim());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        user.setPassword(hashPassword(password));
    }

    @Override
    public boolean verifyPassword(String rawPassword) {
        return rawPassword != null && hashPassword(rawPassword).equals(user.getPassword());
    }

    @Override
    public String getProfileImage() {
        return user.getProfileImage();
    }

    @Override
    public void setProfileImage(String profileImage) {
        user.setProfileImage(profileImage);
    }

    @Override
    public String getBio() {
        return user.getBio();
    }

    @Override
    public void setBio(String bio) {
        user.setBio(bio);
    }

    @Override
    public String getCampus() {
        return user.getCampus();
    }

    @Override
    public void setCampus(String campus) {
        user.setCampus(campus);
    }

    @Override
    public List<Listing> getListings() {
        return user.getListings();
    }

    @Override
    public void setListings(List<Listing> listings) {
        user.setListings(listings != null ? new ArrayList<>(listings) : new ArrayList<>());
    }

    @Override
    public List<Listing> getBookmarks() {
        return user.getBookmarks();
    }

    @Override
    public void setBookmarks(List<Listing> bookmarks) {
        user.setBookmarks(bookmarks != null ? new ArrayList<>(bookmarks) : new ArrayList<>());
    }

    @Override
    public List<Review> getReviews() {
        return user.getReviews();
    }

    @Override
    public void setReviews(List<Review> reviews) {
        user.setReviews(reviews != null ? new ArrayList<>(reviews) : new ArrayList<>());
        user.setUserRating(getUserRating());
    }

    @Override
    public float getUserRating() {
        List<Review> reviews = user.getReviews();
        if (reviews == null || reviews.isEmpty()) {
            user.setUserRating(0.0f);
            return 0.0f;
        }

        float total = 0.0f;
        for (Review review : reviews) {
            total += review.getStars();
        }

        float rating = total / reviews.size();
        user.setUserRating(rating);
        return rating;
    }

    @Override
    public void setUserRating(float userRating) {
        user.setUserRating(userRating);
    }

    @Override
    public LocalDateTime getAccountCreatedDate() {
        return user.getAccountCreatedDate();
    }

    @Override
    public void setAccountCreatedDate(LocalDateTime accountCreatedDate) {
        user.setAccountCreatedDate(accountCreatedDate);
    }

    @Override
    public void addListing(Listing listing) {
        if (listing == null) {
            return;
        }
        List<Listing> listings = user.getListings();
        if (!listings.contains(listing)) {
            listings.add(listing);
        }
    }

    @Override
    public void addBookmark(Listing listing) {
        if (listing == null) {
            return;
        }
        List<Listing> bookmarks = user.getBookmarks();
        if (!bookmarks.contains(listing)) {
            bookmarks.add(listing);
        }
    }

    @Override
    public boolean removeBookmark(Listing listing) {
        if (listing == null) {
            return false;
        }
        return user.getBookmarks().remove(listing);
    }

    @Override
    public void addReview(Review review) {
        if (review == null) {
            return;
        }
        List<Review> reviews = user.getReviews();
        if (!reviews.contains(review)) {
            reviews.add(review);
        }
        user.setUserRating(getUserRating());
    }

    private static boolean isUofTEmail(String email) {
        String trimmedEmail = email.trim();
        return trimmedEmail.matches("^[A-Za-z0-9._%+-]+@(?:mail\\.)?utoronto\\.ca$");
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available.", e);
        }
    }
}