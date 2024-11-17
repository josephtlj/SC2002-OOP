package models;

public class Patient extends User 
{
    // INSTANCE METHODS
    public Patient(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin, boolean passwordHashed, String gender, int age) {
        super(hospitalId, password, role,salt, isFirstLogin,passwordHashed, gender,age);
    }
}
