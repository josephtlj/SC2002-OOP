package src.models;

public class Patient extends User {
    // INSTANCE METHODS
    public Patient(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin) {
        super(hospitalId, password, role, salt, isFirstLogin);
    }
}