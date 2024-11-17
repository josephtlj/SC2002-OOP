package src.daos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import src.interfaces.MedicineDaoInterface;

public class MedicineDao implements MedicineDaoInterface {
    private static String MEDICINEDB_PATH;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            MEDICINEDB_PATH = prop.getProperty("MEDICINEDB_PATH", "medicineDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    } 

}
