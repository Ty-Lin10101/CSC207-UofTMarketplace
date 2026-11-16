public interface UsernameRegistry {
    /**
     * Atomically changes the stored username, returning false if the new name is taken.
     * A null current username represents a new account.
     */
    boolean updateUsername(String currentUsername, String newUsername);
}
