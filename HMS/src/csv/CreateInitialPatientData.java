package HMS.src.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import HMS.src.models.Patient;
import HMS.src.models.User.Role;

public class CreateInitialPatientData {

    // GENERATE A LIST OF PATIENT OBJECTS BASED ON THE NUMBER REQUIRED
    public static List<Patient> generatePatients(int numberOfPatients) {
        List<Patient> patients = new ArrayList<>();

        for (int i = 0; i < numberOfPatients; i++) {
            // GENERATE A NEW PATIENT WITH A UNIQUE HOSPITALID
            String hospitalId = null; // GENERATED IN PATIENT CONSTRUCTOR
            String defaultPassword = "password"; // INITIAL PASSWORD BEFORE HASHING
            byte[] salt = null; // GENERATED IN PATIENT CONSTRUCTOR
            boolean isFirstLogin = true;

            boolean isHashed = false;

            Patient patient = new Patient(hospitalId, defaultPassword, Role.PATIENT, salt, isFirstLogin, isHashed);

            patients.add(patient);
        }

        return patients;
    }

    // SAVING LIST OF PATIENT OBJECTS GENERATED AS A CSV FILE
    public static void savePatientsToCSV(List<Patient> patients, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV header
            writer.write("hospitalId,password,role,salt,isFirstLogin\n");

            // WRITE EACH PATIENT OBJECT TO CSV FILE
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
        // GENERATE 10 PATIENT OBJECTS
        List<Patient> patients = generatePatients(10);

        // SAVE PATIENTS TO CSV FILE
        savePatientsToCSV(patients, "HMS/data/patientData.csv");

    }

}
