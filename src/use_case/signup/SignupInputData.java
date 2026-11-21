package use_case.signup;

public class SignupInputData {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private final String password;
    private final String repeatPassword;
    private final String campus;

    public SignupInputData(String firstName, String lastName, String username,
                           String email, String password, String repeatPassword, String campus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.campus = campus;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRepeatPassword() { return repeatPassword; }
    public String getCampus() { return campus; }
}