package interface_adapter.signup;

import use_case.signup.SignupOutputBoundary;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;

    public SignupPresenter(SignupViewModel signupViewModel,
            LoginViewModel loginViewModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        SignupState state = signupViewModel.getState();
        state.setErrorMessage(null);
        signupViewModel.firePropertyChanged();

        loginViewModel.getState().setEmail(outputData.getEmail());
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        SignupState state = signupViewModel.getState();
        state.setErrorMessage(errorMessage);
        signupViewModel.firePropertyChanged();
    }
}