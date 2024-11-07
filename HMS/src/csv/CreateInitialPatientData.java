package HMS.src.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import HMS.src.management.Patient;
import HMS.src.management.User.Role;

public class CreateInitialPatientData {

    public static List<Patient> generatePatients(int numberOfPatients) {
        List<Patient> patients = new ArrayList<>();

        for (int i = 0; i < numberOfPatients; i++) {
            // Generate a new Patient with a unique hospital ID and a default password
            String hospitalId = null; // Generate in Patient constructor
            String defaultPassword = "password"; // Initial password before hashing
            byte[] salt = null; // Salt will be generated in the constructor
            boolean isFirstLogin = true;

            boolean isHashed = false;

            Patient patient = new Patient(hospitalId, defaultPassword, Role.PATIENT, salt, isFirstLogin, isHashed);
            patients.add(patient);
        }

        return patients;
    }

    public static void savePatientsToCSV(List<Patient> patients, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV header
            writer.write("hospitalId,password,role,salt,isFirstLogin\n");

            // Write each patient's data to the CSV
            for (Patient patient : patients) {
                String hospitalId = patient.getHospitalId();
                String password = patient.getPassword(); // Get hashed password
                String salt = Base64.getEncoder().encodeToString(patient.getSalt()); // Get salt in Base64
                String role = patient.getRole().name(); // Convert enum to String
                String isFirstLogin = String.valueOf(patient.getIsFirstLogin());

                // Write the patient details in CSV format
                writer.write(String.format("%s,%s,%s,%s,%s\n", hospitalId, password, role, salt, isFirstLogin));
            }

            System.out.println("Patients saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving patients to CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Generate 100 Patient objects
        List<Patient> patients = generatePatients(100);

        // Save the patients to the CSV file
        savePatientsToCSV(patients, "HMS/data/patientData.csv");

        // Print out each patient's details to verify (optional)
        for (Patient patient : patients) {
            System.out.println("Hospital ID: " + patient.getHospitalId());
            System.out.println("Role: " + patient.getRole());
            System.out.println("First Login: " + patient.getIsFirstLogin());
            System.out.println("====================================");
        }
    }

}
