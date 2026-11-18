package profile.use_case.edit_profile;

import profile.entity.ProfileDetails;
import profile.use_case.*;

public final class EditProfileInteractor implements EditProfileInputBoundary {
    private final ProfileReader reader;
    private final ProfileWriter writer;
    private final CurrentUser session;
    private final ProfileOutputBoundary output;

    public EditProfileInteractor(ProfileReader reader, ProfileWriter writer,
                                 CurrentUser session, ProfileOutputBoundary output) {
        this.reader = reader;
        this.writer = writer;
        this.session = session;
        this.output = output;
    }

    public void begin(String userId) {
        load(userId, true);
    }

    public void cancel(String userId) {
        // Drafts live in the view. Cancellation performs no writes.
        load(userId, false);
    }

    private void load(String userId, boolean editing) {
        if (!authorized(userId)) {
            return;
        }
        try {
            reader.findById(userId).ifPresentOrElse(
                    profile -> output.presentProfile(new ProfileResponse(profile, true), editing, ""),
                    () -> output.presentError("This user is no longer available"));
        } catch (ProfileStorageException exception) {
            output.presentError("Unable to load profile. Please try again.");
        }
    }

    public void save(EditProfileRequest request) {
        if (request == null) {
            output.presentError("Provide profile details.");
            return;
        }
        if (!authorized(request.userId)) {
            return;
        }
        final ProfileDetails details;
        try {
            details = new ProfileDetails(request.displayName, request.picture, request.bio, request.campus);
        } catch (IllegalArgumentException exception) {
            output.presentError(exception.getMessage());
            return;
        }
        try {
            writer.updateDetails(request.userId, details).ifPresentOrElse(
                    profile -> output.presentProfile(new ProfileResponse(profile, true), false, "Profile saved."),
                    () -> output.presentError("This user is no longer available"));
        } catch (ProfileStorageException exception) {
            output.presentError("Unable to save profile. Your changes are still in the form; please retry.");
        }
    }

    private boolean authorized(String targetId) {
        String actor = session.userId();
        if (actor == null || actor.isBlank()) {
            output.presentError("Log in to edit your profile.");
            return false;
        }
        if (!actor.equals(targetId)) {
            output.presentError("You can only edit your own profile.");
            return false;
        }
        return true;
    }
}
