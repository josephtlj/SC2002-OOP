package daos;

import java.io.*;
import java.util.*;

import models.Patient;
// import HMS.src.models.Staff;

public class LoginDao {

    // LOAD NECESSARY PATHS FROM CONFIG.PROPERTIES
    private static String PATIENTDB_PATH;
    // private static String STAFFDB_PATH;

    static {
        // LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("HMS/src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PATIENTDB_PATH = prop.getProperty("PATIENTDB_PATH", "patientDB.csv");
            // STAFFDB_PATH = prop.getProperty("STAFFDB_PATH", "staffDB.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    // READ PATIENT BY HOSPITAL ID
    public Patient getPatientByHospitalId(String userHospitalId) {
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

                    // IF HOSPITALID FOUND, RETURN Patient DATA AS USER OBJECT
                    if (hospitalId.equals(userHospitalId)) {
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

        // READ STAFF BY HOSPITAL ID
        // public Staff getStaffByHospitalId(String userHospitalId) {
        //     try (BufferedReader br = new BufferedReader(new FileReader(USERDB_PATH))) {
        //         // SKIP HEADER ROW
        //         br.readLine();
    
        //         String line;
        //         while ((line = br.readLine()) != null) {
        //             String[] fields = line.split(",");
        //             if (fields.length == 5) {
        //                 String hospitalId = fields[0];
        //                 String password = fields[1];
        //                 Staff.Role role = Patient.Role.valueOf(fields[2]);
        //                 byte[] salt = Base64.getDecoder().decode(fields[3]);
        //                 boolean isFirstLogin = Boolean.parseBoolean(fields[4]);
    
        //                 // IF HOSPITALID FOUND, RETURN Patient DATA AS USER OBJECT
        //                 if (hospitalId.equals(userHospitalId)) {
        //                     return new Staff(hospitalId, password, role, salt, isFirstLogin, true);
        //                 }
        //             }
        //         }
        //         // IF STAFF NOT FOUND
        //         return null;
    
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //         return null;
        //     }
        // }

    // Other CRUD operations can be added here (e.g., createUser, updateUser,
    // deleteUser)
}