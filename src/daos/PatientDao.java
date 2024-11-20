package src.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import src.models.Patient;
import src.interfaces.PatientDaoInterface;

public class PatientDao implements PatientDaoInterface {
    private static String PATIENTDB_PATH;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PATIENTDB_PATH = prop.getProperty("PATIENTDB_PATH", "patientDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    
    /** 
     * @param hospitalId
     * @return Patient
     */
    @Override
    public Patient getPatientByHospitalId(String hospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENTDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Patient patient = parsePatient(line);
                if (patient != null && patient.getHospitalId().equals(hospitalId)) {
                    return patient;
                }
            }

            // IF PATIENT NOT FOUND
            return null;

        } catch (IOException e) {
            System.err.println("Error reading patient database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    
    /** 
     * @param patient
     */
    public void updatePatient(Patient patient) {
        List<String> lines = new ArrayList<>();
        boolean patientFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(PATIENTDB_PATH))) {
            // SKIP HEADER ROW
            String header = br.readLine();
             // KEEP HEADER ROW IN OUTPUT
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 5 && fields[0].equals(patient.getHospitalId())) {
                    // UPDATE MATCHING PATIENT RECORD
                    String updatedLine = formatPatientToCSV(patient);
                    lines.add(updatedLine);
                    patientFound = true;
                } else {
                    // KEEP EXISTING PATIENT RECORD
                    lines.add(line);
                }
            }

            if (!patientFound) {
                throw new IllegalArgumentException(
                        "Patient with hospitalId " + patient.getHospitalId() + " not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the patient database: " + e.getMessage());
            e.printStackTrace();
        }

        // UPDATE PATIENTDB.CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENTDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the patient database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    /** 
     * @param line
     * @return Patient
     */
    // HELPER METHOD TO PARSE A PATIENT OBJECT FROM A CSV LINE
    private Patient parsePatient(String line) {
        try {
            String[] fields = line.split(",");
            if (fields.length != 5) {
                System.err.println("Invalid record format: " + line);
                return null;
            }

            String hospitalId = fields[0];
            String password = fields[1];
            Patient.Role role = Patient.Role.valueOf(fields[2]);
            byte[] salt = Base64.getDecoder().decode(fields[3]);
            boolean isFirstLogin = Boolean.parseBoolean(fields[4]);

            return new Patient(hospitalId, password, role, salt, isFirstLogin);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing record: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // HELPER METHOD TO FORMAT PATIENT OBJECT INTO CSV LINE
    private String formatPatientToCSV(Patient patient) {
        return String.join(",",
                patient.getHospitalId(),
                patient.getPassword(),
                patient.getRole().name(),
                Base64.getEncoder().encodeToString(patient.getSalt()),
                String.valueOf(patient.getIsFirstLogin()));
    }
}
