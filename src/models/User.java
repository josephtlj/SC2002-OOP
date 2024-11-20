package src.models;

public class User {
    public enum Role {
        ADMINISTRATOR, 
        DOCTOR,
        PATIENT,
        PHARMACIST,

    }

    
    private String hospitalId;
    private String password;
    private byte[] salt;
    private Role role;
    private boolean isFirstLogin;

    
    public User(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin) {
        this.hospitalId = hospitalId;
        this.password = password;
        this.role = role;
        this.salt = salt;
        this.isFirstLogin = isFirstLogin;
    }

    
    /** 
     * @return String
     */
    
    public String getHospitalId() {
        return hospitalId;
    }

    
    /** 
     * @param hospitalId
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    
    /** 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    
    /** 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
    /** 
     * @return byte[]
     */
    public byte[] getSalt() {
        return salt;
    }

    
    /** 
     * @param salt
     */
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    
    /** 
     * @return Role
     */
    public Role getRole() {
        return role;
    }

    
    /** 
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    
    /** 
     * @return boolean
     */
    public boolean getIsFirstLogin() {
        return isFirstLogin;
    }

    
    /** 
     * @param firstLogin
     */
    public void setIsFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }
}
