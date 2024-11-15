package Models;

import java.util.UUID;

public abstract class Staff extends User {
    // Staff-specific attributes
    private String hospitalId;  // Unique identifier for staff
    private String department;   // Department specific to staff

    // Constructor
    public Staff(String hospitalId, String password, String role, byte[] salt, boolean isFirstLogin, String department) {
        super(hospitalId, password, role, salt, isFirstLogin);  // Call the superclass constructor (User)
        this.hospitalId = hospitalId;  // Use hospitalId as the unique identifier
        this.department = department;  // Initialize department
    }

    // Getters and Setters
    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Log in integration
    public void logIn() {
        // Logic for logging in a staff member
    }

    // Log out integration
    public void logOut() {
        // Logic for logging out a staff member
    }

}
