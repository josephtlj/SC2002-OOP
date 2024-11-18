package src.models;

public class Administrator extends Staff {
    // INSTANCE METHODS
    public Administrator(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin, String name,
            Gender gender,
            int age) {
        super(hospitalId, password, role, salt, isFirstLogin, name, gender, age);
    }
}
