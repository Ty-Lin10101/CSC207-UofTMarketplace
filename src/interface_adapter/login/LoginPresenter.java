package interface_adapter.login;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;

    public LoginPresenter(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        LoginState state = loginViewModel.getState();
        state.setErrorMessage(null);
        state.setPassword("");
        loginViewModel.firePropertyChanged();
        // hook ViewManagerModel here to switch to the HomeView
    }

    @Override
    public void prepareFailView(String errorMessage) {
        LoginState state = loginViewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setPassword("");
        loginViewModel.firePropertyChanged();
    }
}