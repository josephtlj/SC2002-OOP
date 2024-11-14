package models;

public class Staff extends User {
    // INSTANCE METHODS
    public Staff(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin,
            boolean passwordHashed) {
        super(hospitalId, password, role, salt, isFirstLogin, passwordHashed);
    }
}