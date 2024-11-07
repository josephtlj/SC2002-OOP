package HMS.src.models;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// USER MODEL
public class User {
    public enum Role {
        PATIENT,
        DOCTOR,
        PHARMACIST,
        ADMINISTRATOR
    }

    // INSTANCE VARIABLES
    private String hospitalId;
    private byte[] salt;
    private String password;
    private Role role;
    private boolean isFirstLogin;

    // INSTANCE METHODS
    public User(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin,
            boolean passwordHashed) {
        this.hospitalId = (hospitalId != null) ? hospitalId : generateHospitalId(role);
        this.salt = (salt != null) ? salt : generateSalt();
        this.password = (password != null) ? ((passwordHashed) ? password : hashPassword(password, this.salt))
                : hashPassword("password", this.salt);
        this.role = role;
        this.isFirstLogin = isFirstLogin;
    }

    // GET METHODS
    public String getHospitalId() {
        return this.hospitalId;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }

    public boolean getIsFirstLogin() {
        return this.isFirstLogin;
    }

    // SET METHODS
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.salt = generateSalt();
        this.password = hashPassword(password, this.salt);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setIsFirstLogin(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    // SUPPORTING METHODS
    public static String generateHospitalId(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        String prefix = role.name().substring(0, 2).toUpperCase();
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
        return password.equals(hashedAttempt);
    }

}
