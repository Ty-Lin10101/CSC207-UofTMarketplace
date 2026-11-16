public class UserService {
    private final User user;
    private final UsernameRegistry usernameRegistry;

    public UserService(User user, UsernameRegistry usernameRegistry) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (usernameRegistry == null) {
            throw new IllegalArgumentException("Username registry cannot be null.");
        }
        this.user = user;
        this.usernameRegistry = usernameRegistry;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        String trimmedUsername = username.trim();
        String oldUsername = user.getUsername();

        if (!usernameRegistry.updateUsername(oldUsername, trimmedUsername)) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        user.setUsername(trimmedUsername);
    }

    public void setEmail(String email) {
        if (email == null || !isUofTEmail(email)) {
            throw new IllegalArgumentException("Email must be a valid UofT email address.");
        }
        user.setEmail(email.trim());
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        user.setPassword(password);
    }

    public boolean verifyPassword(String rawPassword) {
        return PasswordHasher.matches(rawPassword, user.getPassword());
    }

    private static boolean isUofTEmail(String email) {
        String trimmedEmail = email.trim();
        return trimmedEmail.matches("^[A-Za-z0-9._%+-]+@(?:mail\\.)?utoronto\\.ca$");
    }

}