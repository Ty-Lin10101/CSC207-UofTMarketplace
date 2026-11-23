package profile.use_case.view_profile;

import profile.use_case.*;

public final class ViewProfileInteractor implements ViewProfileInputBoundary {
    private final ProfileReader profiles;
    private final CurrentUser session;
    private final ProfileOutputBoundary output;

    public ViewProfileInteractor(ProfileReader profiles, CurrentUser session, ProfileOutputBoundary output) {
        this.profiles = profiles;
        this.session = session;
        this.output = output;
    }

    public void viewOwnProfile() {
        String id = session.userId();
        if (id == null || id.isBlank()) {
            output.presentError("Log in to view your profile.");
            return;
        }
        execute(id);
    }

    public void execute(String userId) {
        if (userId == null || userId.isBlank()) {
            output.presentError("This user is no longer available");
            return;
        }
        try {
            profiles.findById(userId).ifPresentOrElse(
                    profile -> output.presentProfile(new ProfileResponse(profile,
                            userId.equals(session.userId())), false, ""),
                    () -> output.presentError("This user is no longer available"));
        } catch (ProfileStorageException exception) {
            output.presentError("Unable to load profile. Please try again.");
        }
    }
}
