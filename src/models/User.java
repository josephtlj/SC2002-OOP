package src.models;

public class User {
    public enum Role {
        PATIENT,
        DOCTOR,
        PHARMACIST,
        ADMINISTRATOR
    }

    // INSTANCE VARIABLES
    private String hospitalId;
    private String password;
    private byte[] salt;
    private Role role;
    private boolean isFirstLogin;

    // CONSTRUCTOR
    public User(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin) {
        this.hospitalId = hospitalId;
        this.password = password;
        this.role = role;
        this.salt = salt;
        this.isFirstLogin = isFirstLogin;
    }

    // GETTERS AND SETTERS
    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    // OVERRIDDEN METHODS (OPTIONAL)
    // @Override
    // public String toString() {
    // return "User{" +
    // "hospitalId='" + hospitalId + '\'' +
    // ", role=" + role +
    // ", isFirstLogin=" + isFirstLogin +
    // '}';
    // }
}
