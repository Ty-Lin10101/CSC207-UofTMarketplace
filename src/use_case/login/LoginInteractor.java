package use_case.login;

import Entities.User;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccess;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccess,
                           LoginOutputBoundary loginPresenter) {
        this.userDataAccess = userDataAccess;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData inputData) {
        if ( inputData.getEmail().isEmpty() ) {
            loginPresenter.prepareFailView("Please enter an email");
            return;
        }

        if ( inputData.getPassword() == null || inputData.getPassword().isEmpty()) {
            loginPresenter.prepareFailView("Please enter a password.");
            return;
        }

        String email = inputData.getEmail().trim().toLowerCase();
        String password = inputData.getPassword();

        User user = userDataAccess.getByEmail(email);

        if (user == null || !user.matchesPassword(password)) {
            loginPresenter.prepareFailView("Incorrect email or password");
            return;
        }

        userDataAccess.setCurrentUsername(user.getUsername());
        LoginOutputData outputData = new LoginOutputData(user.getUsername(), user.getEmail());
        loginPresenter.prepareSuccessView(outputData);
    }
}