package profile.interface_adapter;

import profile.use_case.ProfileOutputBoundary;
import profile.use_case.ProfileResponse;

public final class ProfilePresenter implements ProfileOutputBoundary {
    private final ProfileViewModel model;

    public ProfilePresenter(ProfileViewModel model) { this.model = model; }

    public void presentProfile(ProfileResponse response, boolean editing, String message) {
        model.show(response, editing, message);
    }

    public void presentError(String message) { model.showError(message); }
}
