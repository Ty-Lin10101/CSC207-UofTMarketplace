package profile.interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import profile.use_case.ProfileResponse;

public final class ProfileViewModel {
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private ProfileResponse profile;
    private boolean editing;
    private String message = "";

    public ProfileResponse getProfile() { return profile; }
    public boolean isEditing() { return editing; }
    public String getMessage() { return message; }

    public void show(ProfileResponse profile, boolean editing, String message) {
        this.profile = profile;
        this.editing = editing;
        this.message = message;
        changes.firePropertyChange("profile", null, profile);
    }

    public void showError(String message) {
        this.message = message;
        // A separate event preserves the user's unsaved form after validation/storage errors.
        changes.firePropertyChange("error", null, message);
    }

    public void addListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }
}
