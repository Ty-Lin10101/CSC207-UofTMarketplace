package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

public class SignupController {
    private final SignupInputBoundary signupInteractor;

    public SignupController(SignupInputBoundary signupInteractor) {
        this.signupInteractor = signupInteractor;
    }

    public void execute(String firstName, String lastName, String username,
                        String email, String password, String repeatPassword, String campus) {
        SignupInputData inputData = new SignupInputData(
                firstName, lastName, username, email, password, repeatPassword, campus
        );
        signupInteractor.execute(inputData);
    }
}