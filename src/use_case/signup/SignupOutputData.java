package use_case.signup;

public class SignupOutputData {
    private final String username;
    private final String email;

    public SignupOutputData(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
}