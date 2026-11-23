package Entities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

/** Supplies the salted password hash already expected by User.setPassword. */
final class PasswordHasher {
    private static final int ITERATIONS = 600_000;
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordHasher() {
    }

    static String hash(String password) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        Base64.Encoder base64 = Base64.getEncoder();
        return "pbkdf2-sha256$" + ITERATIONS + "$"
                + base64.encodeToString(salt) + "$" + base64.encodeToString(derive(password, salt));
    }

    static boolean matches(String password, String stored) {
        if (password == null || stored == null) {
            return false;
        }
        String[] parts = stored.split("[$]", -1);
        if (parts.length != 4 || !"pbkdf2-sha256".equals(parts[0])
                || !Integer.toString(ITERATIONS).equals(parts[1])) {
            return false;
        }
        byte[] salt = Base64.getDecoder().decode(parts[2]);
        return java.security.MessageDigest.isEqual(Base64.getDecoder().decode(parts[3]), derive(password, salt));
    }

    private static byte[] derive(String password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, 256);

        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Password hashing is unavailable.", exception);
        } finally {
            spec.clearPassword();
        }
    }
}
