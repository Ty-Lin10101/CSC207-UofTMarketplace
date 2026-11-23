package profile.use_case.edit_profile;

public interface EditProfileInputBoundary {
    void begin(String userId);
    void save(EditProfileRequest request);
    void cancel(String userId);
}
