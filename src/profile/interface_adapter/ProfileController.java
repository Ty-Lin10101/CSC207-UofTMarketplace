package profile.interface_adapter;

import profile.use_case.edit_profile.EditProfileInputBoundary;
import profile.use_case.edit_profile.EditProfileRequest;
import profile.use_case.view_profile.ViewProfileInputBoundary;

public final class ProfileController {
    private final ViewProfileInputBoundary view;
    private final EditProfileInputBoundary edit;

    public ProfileController(ViewProfileInputBoundary view, EditProfileInputBoundary edit) {
        this.view = view;
        this.edit = edit;
    }

    public void viewProfile(String id) { view.execute(id); }
    public void myProfile() { view.viewOwnProfile(); }
    public void edit(String id) { edit.begin(id); }
    public void cancel(String id) { edit.cancel(id); }

    public void save(String id, String name, String picture, String bio, String campus) {
        edit.save(new EditProfileRequest(id, name, picture, bio, campus));
    }
}
