package Administrator.src.managers;

// currently an error at filter method since staff class extends from user class attributes like Role, Gender and Age and dont have user class to integrate yet
import daos.StaffDAO;
import models.Staff;

import java.util.List;

public class StaffManager{

    private StaffDAO staffDAO;

    // Constructor
    public StaffManager() {
        this.staffDAO = new StaffDAO();
    }

    // Add a new staff member
    public void addStaff(Staff staff) {
        staffDAO.addStaff(staff); 
    }

    // Update an existing staff member
    public void updateStaff(Staff updatedStaff) {
        staffDAO.updateStaff(updatedStaff);  
    }

    // Remove a staff member
    public void deleteStaff(String hospitalId) {
        staffDAO.deleteStaff(hospitalId);  
    }

    // Find a staff member by their hospital ID
    public Staff findStaffById(String hospitalId) {
        return staffDAO.findStaffById(hospitalId); 
    }

    // Get a list of all staff members
    public List<Staff> getAllStaff() {
        return staffDAO.loadAll();  
    }

    // Filter staff by role, gender, age, and department
    public List<Staff> filterStaff(String role, String gender, Integer age, String department) {
        List<Staff> allStaff = staffDAO.loadAll();

        // Filter staff based on the given parameters (only non-null values are used)
        return allStaff.stream()
                .filter(staff -> (role == null || staff.getRole().equalsIgnoreCase(role)) &&  // Role filter
                                 (gender == null || staff.getGender().equalsIgnoreCase(gender)) &&  // Gender filter
                                 (age == null || staff.getAge() == age) &&  // Age filter 
                                 (department == null || staff.getDepartment().equalsIgnoreCase(department)))  // Department filter
                .toList();
    }
}
