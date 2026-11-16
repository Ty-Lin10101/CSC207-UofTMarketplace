public class UserProfile {
    private String firstName;
    private String lastName;
    private String profileImage;
    private String bio;
    private String campus;

    public UserProfile() {
    }

    public UserProfile(String firstName, String lastName, String profileImage, String bio, String campus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.bio = bio;
        this.campus = campus;
    }

    public UserProfile(UserProfile other) {
        this(other.firstName, other.lastName, other.profileImage, other.bio, other.campus);
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
}
