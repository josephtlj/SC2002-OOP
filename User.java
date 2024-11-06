// User Model
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

abstract class User {
    // Instance Variables
    private String hospitalId;
    private String password;
    private final String role;
    private byte[] salt;
    private boolean isFirstLogin;

    // Instance Methods
    public User() {
        this.hospitalId = generateHospitalId(null);
        setPassword("password");
        this.role = null;
        this.isFirstLogin = true;
    }

    public User(String role) {
        this.hospitalId = generateHospitalId(role);
        setPassword("password");
        this.role = role;
        this.isFirstLogin = true;
    }

    // GET Methods
    public String getHospitalId() {
        return this.hospitalId;
    }

    public String getRole() {
        return this.role;
    }

    public boolean getIsFirstLogin() {
        return this.isFirstLogin;
    }

    // SET Methods
    public void setPassword(String password) {
        this.salt = generateSalt();
        this.password = hashPassword(password, this.salt);
    }

    // Supporting Methods
    public static String generateHospitalId(String role) {
        String prefix = role.substring(0, 2).toUpperCase();
        String uniquePart = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        return prefix + uniquePart;
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        try {
            SecureRandom.getInstanceStrong().nextBytes(salt);
        } catch (Exception e) {
            throw new RuntimeException("Error generating salt!", e);
        }
        return salt;
    }

    protected String hashPassword(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password!", e);
        }
    }

    public boolean verifyPassword(String passwordAttempt) {
        String hashedAttempt = hashPassword(passwordAttempt, this.salt);
        if (password.equals(hashedAttempt)) {
            return true;
        }
        return false;
    }

    public abstract void logout();

}
