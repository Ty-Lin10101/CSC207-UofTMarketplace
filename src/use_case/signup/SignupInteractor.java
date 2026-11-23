package use_case.signup;

import Entities.User;

public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccess;
    private final SignupOutputBoundary signupPresenter;
    private static final String PASSWORD_SYMBOLS = ".!@#$%^&*()-=";

    public SignupInteractor(SignupUserDataAccessInterface userDataAccess,
                            SignupOutputBoundary signupPresenter) {
        this.userDataAccess = userDataAccess;
        this.signupPresenter = signupPresenter;
    }

    @Override
    public void execute(SignupInputData inputData) {
        if (inputData.getFirstName().isBlank() || inputData.getLastName().isBlank()
                || inputData.getUsername().isEmpty() || inputData.getEmail().isEmpty()
                || inputData.getCampus().isBlank() || "Select a campus...".equals(inputData.getCampus())) {
            signupPresenter.prepareFailView("All fields are required.");
            return;
        }

        if ((inputData.getFirstName().trim() + " " + inputData.getLastName().trim()).length() > 80) {
            signupPresenter.prepareFailView("Your profile name must be 80 characters or fewer.");
            return;
        }

        String email = inputData.getEmail().trim().toLowerCase();
        String username = inputData.getUsername().trim().toLowerCase(); // force usernames to lowercase to make it
                                                                        // easier to store and check
        String password = inputData.getPassword();

        if (!isUofTEmail(email)) {
            signupPresenter.prepareFailView("Please enter a valid UofT email (@mail.utoronto.ca or @utoronto.ca).");
            return;
        }

        if (!isValidUsername(username)) {
            signupPresenter.prepareFailView("Username must contain only alphanumeric characters or underscores.");
            return;
        }

        if (username.matches("^_+$")) {
            signupPresenter.prepareFailView("Username cannot contain only underscores.");
            return;
        }

        if (userDataAccess.existsByUsername(username)) {
            signupPresenter.prepareFailView("Username is already taken.");
            return;
        }

        if (userDataAccess.existsByEmail(email)) {
            signupPresenter.prepareFailView("An account with this UofT email already exists.");
            return;
        }

        if (password == null) {
            signupPresenter.prepareFailView("Please set a password.");
            return;
        }

        if (!password.equals(inputData.getRepeatPassword())) {
            signupPresenter.prepareFailView("Passwords do not match.");
            return;
        }

        if (!isStrongPassword(password)) {
            signupPresenter.prepareFailView("Password must be at least 8 characters and include a combination of " +
                                            "uppercase and lowercase letters, numbers, and accepted symbols (.!@#$%^&*()-=).");
                                            // I couldn't figure out how to allow every possible symbol lol so we have this
            return;
        }

        User newUser = new User(
                inputData.getFirstName().trim(),
                inputData.getLastName().trim(),
                username,
                email,
                password, "", "",
                inputData.getCampus().trim()
        );
        userDataAccess.save(newUser);
        userDataAccess.setCurrentUsername(newUser.getUsername());

        SignupOutputData outputData = new SignupOutputData(newUser.getUsername(), newUser.getEmail());
        signupPresenter.prepareSuccessView(outputData);
    }

    private boolean isUofTEmail(String email) {
        return email.endsWith("@mail.utoronto.ca") || email.endsWith("@utoronto.ca");
    }

    private boolean isValidUsername(String username) {
        return username != null && username.matches("^[a-zA-Z0-9_]+$");
    }

    private boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpperCaseLetter = false;
        boolean hasLowerCaseLetter = false;
        boolean hasSymbol = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpperCaseLetter = true;
            else if (Character.isLowerCase(c)) hasLowerCaseLetter = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (PASSWORD_SYMBOLS.indexOf(c) != -1) hasSymbol = true;
        }
        return hasUpperCaseLetter && hasLowerCaseLetter && hasSymbol && hasDigit;
    }
}