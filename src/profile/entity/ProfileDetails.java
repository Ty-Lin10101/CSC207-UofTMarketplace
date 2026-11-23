package profile.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Editable public information; never includes credentials or account identifiers. */
public final class ProfileDetails {
    public static final List<String> CAMPUSES = Collections.unmodifiableList(
            Arrays.asList("St. George", "Scarborough", "Mississauga"));
    public final String displayName;
    public final String picture;
    public final String bio;
    public final String campus;

    public ProfileDetails(String displayName, String picture, String bio, String campus) {
        this.displayName = text(displayName).trim();
        this.picture = text(picture).trim();
        this.bio = text(bio).trim();
        this.campus = text(campus).trim();
        if (this.displayName.isEmpty() || this.displayName.length() > 80) {
            throw new IllegalArgumentException("Display name must contain 1 to 80 characters.");
        }
        if (this.bio.length() > 1000) {
            throw new IllegalArgumentException("Bio must contain at most 1000 characters.");
        }
        if (this.picture.length() > 2048) {
            throw new IllegalArgumentException("Profile picture path is too long.");
        }
        if (!CAMPUSES.contains(this.campus)) {
            throw new IllegalArgumentException("Choose a UofT campus.");
        }
    }

    private static String text(String value) {
        return value == null ? "" : value;
    }
}
