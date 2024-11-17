package src.daos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import src.interfaces.PrescriptionDaoInterface;

public class PrescriptionDao implements PrescriptionDaoInterface {
    private static String PRESCRIPTIONDB_PATH;;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PRESCRIPTIONDB_PATH = prop.getProperty("PRESCRIPTIONDB_PATH", "prescriptionDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
