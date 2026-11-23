package profile.use_case.edit_profile;

/** Target ID is checked against the trusted session to reject stale or forged forms. */
public final class EditProfileRequest {
    public final String userId;
    public final String displayName;
    public final String picture;
    public final String bio;
    public final String campus;

    public EditProfileRequest(String userId, String displayName, String picture, String bio, String campus) {
        this.userId = userId;
        this.displayName = displayName;
        this.picture = picture;
        this.bio = bio;
        this.campus = campus;
    }
}
