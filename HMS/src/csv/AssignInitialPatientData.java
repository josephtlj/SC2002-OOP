package HMS.src.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import HMS.src.management.Patient;
import HMS.src.management.User.Role;

public class AssignInitialPatientData {

    public void assignPatientData(Patient[] patientList, String folderPath, String fileName) {
        Path filePath = Paths.get(".", folderPath, fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toString()))) {
            int index = 0;
            String line;
            boolean firstRow = true; // FLAG TO SKIP HEADER ROW OF DATA FILE

            while ((line = br.readLine()) != null && index < patientList.length) {
                if (firstRow) {
                    // SKIPS THE HEADER ROW OF DATA FILE
                    firstRow = false;
                    continue;
                }

                String[] parts = line.split(",");

                // PATIENT
                if (parts.length == 5) {
                    // PARSE EACH PART OF LINE INTO PATIENT OBJECT PROPERTIES
                    String hospitalId = parts[0];
                    String password = parts[1];
                    Role role = Role.valueOf(parts[2].toUpperCase()); // CONVERT ROLE FROM STRING TO ROLE ENUM
                    byte[] salt = Base64.getDecoder().decode(parts[3]); // DECODE TO BASE64 SALT
                    boolean isFirstLogin = Boolean.parseBoolean(parts[4]);

                    boolean isHashed = true;

                    // CREATE NEW PATIENT OBJECT AND ADD IT TO PATIENTLIST
                    Patient patient = new Patient(hospitalId, password, role, salt, isFirstLogin, isHashed);
                    // System.out.print(patient.verifyPassword("password"));
                    patientList[index] = patient;
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
