package profile.use_case;

public interface ProfileOutputBoundary {
    void presentProfile(ProfileResponse response, boolean editing, String message);
    void presentError(String message);
}
