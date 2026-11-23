package use_case.signup;

import Entities.User;

public interface SignupUserDataAccessInterface {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void save(User user);
    void setCurrentUsername(String username);
}
