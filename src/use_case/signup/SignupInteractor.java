package use_case.signup;

import entity.User;

public class SignupInteractor implements SignupInputBoundary {
    private final SignupDataAccessInterface userDataAccess
    private final SignupOutputBoundary userPresenter;

    public SignupInteractor(SignupDataAccessInterface userDataAccess,
                            SignupOutputBoundary userPresenter) {
        this.userDataAccess = SignupDataAccessInterface userDataAccess;
        this.userPresenter = SignupOutputBoundary userPresenter;
    }

    @Override
    public void execute(SignupInputData inputData) {
        String email = inputData.getEmail().toLowerCase();
        String username = inputData.getUsername().toLowerCase();
        String password = inputData.getPassword();
        String firstName = inputData.getFirstName();
        String lastName = inputData.getLastName();
        String campus = inputData.getCampus();
        String repeatPassword = inputData.getRepeatPassword();

        if ( email.isEmpty() ||
            username.isEmpty() ||
            password.isEmpty() ||
            firstName.isEmpty() ||
            lastName.isEmpty() ||
            campus.isEmpty() ||
            repeatPassword.isEmpty
        ) {
            SignupPresenter.prepare
        }
    }
}