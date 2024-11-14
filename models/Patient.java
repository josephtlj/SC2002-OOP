package models;

public class Patient extends User {
    // INSTANCE METHODS
    public Patient(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin,
            boolean passwordHashed) {
        super(hospitalId, password, role, salt, isFirstLogin, passwordHashed);
    }
}