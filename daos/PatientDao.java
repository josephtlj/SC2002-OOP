package daos;

import java.io.*;
import java.util.*;

import models.Patient;

public class PatientDao {

    // LOAD NECESSARY PATHS FROM CONFIG.PROPERTIES
    private static String PATIENTDB_PATH;

    static {
        // LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("HMS/src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PATIENTDB_PATH = prop.getProperty("PATIENTDB_PATH", "patientDB.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // READ ALL PATIENTS
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENTDB_PATH))) {
            // Skip header row
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 5) {
                    String hospitalId = fields[0];
                    String password = fields[1];
                    Patient.Role role = Patient.Role.valueOf(fields[2]);
                    byte[] salt = Base64.getDecoder().decode(fields[3]);
                    boolean isFirstLogin = Boolean.parseBoolean(fields[4]);

                    // Create a new Patient object and add it to the list
                    Patient patient = new Patient(hospitalId, password, role, salt, isFirstLogin, true);
                    patients.add(patient);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return patients;
    }

    // READ PATIENT BY HOSPITAL ID
    public Patient getPatientByHospitalId(String patientHospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENTDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 5) {
                    String hospitalId = fields[0];
                    String password = fields[1];
                    Patient.Role role = Patient.Role.valueOf(fields[2]);
                    byte[] salt = Base64.getDecoder().decode(fields[3]);
                    boolean isFirstLogin = Boolean.parseBoolean(fields[4]);

                    // IF HOSPITALID FOUND, RETURN PATIENT DATA AS USER OBJECT
                    if (hospitalId.equals(patientHospitalId)) {
                        return new Patient(hospitalId, password, role, salt, isFirstLogin, true);
                    }
                }
            }
            // IF PATIENT NOT FOUND
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE PATIENT'S PASSWORD USING HOSPITALID
    public void updatePatientPasswordByHospitalId(String patientNewPassword, String patientHospitalId) {
        List<Patient> patients = getAllPatients();

        // Loop through the list of patients and update the password if the hospitalId
        // matches
        for (Patient patient : patients) {
            if (patient.getHospitalId().equals(patientHospitalId)) {
                patient.setPassword(patientNewPassword);
                patient.setIsFirstLogin(false);
                break; // Exit the loop once the patient is found and updated
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENTDB_PATH))) {
            // Write header row first
            writer.write("hospitalId,password,role,salt,isFirstLogin\n");

            // Write each patient's data
            for (Patient patient : patients) {
                StringBuilder sb = new StringBuilder();
                sb.append(patient.getHospitalId()).append(",")
                        .append(patient.getPassword()).append(",")
                        .append(patient.getRole().name()).append(",")
                        .append(Base64.getEncoder().encodeToString(patient.getSalt())).append(",")
                        .append(patient.getIsFirstLogin()).append("\n");
                writer.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}