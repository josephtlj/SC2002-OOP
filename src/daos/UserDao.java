package src.daos;

import src.models.User;
import src.models.Patient;
import src.interfaces.UserDaoInterface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

public class UserDao implements UserDaoInterface {
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

    public Patient getPatientByHospitalId(String userHospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENTDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                Patient patient = parsePatient(line);
                if (patient != null && patient.getHospitalId().equals(userHospitalId)) {
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

    // HELPER METHOD TO PARSE A PATIENT FROM A CSV LINE
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

    @Override
    public User getUserByHospitalId(String hospitalId) {
        // return userDatabase.get(hospitalId);
        return null;
    }

    @Override
    public void updateUser(User user) {
        // userDatabase.put(user.getHospitalId(), user);
    }

    @Override
    public void deleteUser(String hospitalId) {
        // userDatabase.remove(hospitalId);
    }

    @Override
    public void createUser(User user) {
        // userDatabase.put(user.getHospitalId(), user);
    }
}
