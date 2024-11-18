package Administrator.src.daos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import models.Staff;

public class StaffDAO {
    private static final String FILE_NAME = "staffData.csv";

    // Save a list of Staff objects to the CSV file
    public void saveAll(List<Staff> staffList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Staff staff : staffList) {
                writer.println(toCsvString(staff));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all Staff objects from the CSV file
    public List<Staff> loadAll() {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                staffList.add(fromCsvString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    // Convert a Staff object to a CSV string
    private String toCsvString(Staff staff) {
        return staff.getHospitalId() + "," +
               staff.getPassword() + "," +
               staff.getRole() + "," +
               staff.getDepartment() + "," +
               staff.isFirstLogin();
    }

    // Convert a CSV string to a Staff object
    private Staff fromCsvString(String csv) {
        String[] parts = csv.split(",");
        String hospitalId = parts[0];  // Read hospitalId as the identifier
        String password = parts[1];
        String role = parts[2];
        String department = parts[3];
        boolean isFirstLogin = Boolean.parseBoolean(parts[4]);

        // Assuming the User class constructor handles the password, role, salt, and isFirstLogin parameters.
        return new Staff(hospitalId, password, role, null, isFirstLogin, department) {
            // Provide an anonymous subclass of Staff as the constructor is abstract
        };
    }

    // Add a new staff member to the CSV file
    public void addStaff(Staff staff) {
        List<Staff> staffList = loadAll();
        staffList.add(staff);
        saveAll(staffList);
    }

    // Find a staff member by their hospitalId
    public Staff findStaffById(String hospitalId) {
        List<Staff> staffList = loadAll();
        for (Staff staff : staffList) {
            if (staff.getHospitalId().equals(hospitalId)) {
                return staff;
            }
        }
        return null;
    }

    // Update a staff member in the CSV file
    public void updateStaff(Staff updatedStaff) {
        List<Staff> staffList = loadAll();
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getHospitalId().equals(updatedStaff.getHospitalId())) {
                staffList.set(i, updatedStaff);
                break;
            }
        }
        saveAll(staffList);
    }

    // Remove a staff member from the CSV file
    public void deleteStaff(String hospitalId) {
        List<Staff> staffList = loadAll();
        staffList.removeIf(staff -> staff.getHospitalId().equals(hospitalId));
        saveAll(staffList);
    }
}
