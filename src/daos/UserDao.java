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
