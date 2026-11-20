package interface_adapter.signup;

public SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;

    public SignupPresenter(SignupViewModel signupViewModel,
            LoginViewModel loginViewModel) {
        this.signupViewModel = SignupViewModel signupViewModel;
        this.loginViewModel = LoginViewModel loginViewModel;
    }

    @Override
    public prepareSuccessView(SignupOutputData outputData) {
        SignupState state = signupViewModel.getState();
        state.setErrorMessage(null);
        signupViewModel.firePropertyChanged();
    }

    @Override
    public prepareFailView(String errorMessage) {
        SignupState state = signupViewModel.getState();
        state.setErrorMessage(errorMessage);
        signupViewModel.firePropertyChanged();
    }
}