package use_case.login;

import Entities.User;

public interface LoginUserDataAccessInterface {
    User getByEmail(String email);
    void setCurrentUsername(String username);
}