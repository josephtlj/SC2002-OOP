package src.models;

public class Pharmacist extends Staff {
    // INSTANCE METHODS
    public Pharmacist(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin, String name,
            Gender gender,
            int age) {
        super(hospitalId, password, role, salt, isFirstLogin, name, gender, age);
    }
}
