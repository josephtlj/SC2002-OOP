package src.daos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import src.interfaces.ReplenishmentRequestDaoInterface;

public class ReplenishmentRequestDao implements ReplenishmentRequestDaoInterface {
    private static String REPLENISHMENTREQUESTDB_PATH;;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            REPLENISHMENTREQUESTDB_PATH = prop.getProperty("REPLENISHMENTREQUESTDB_PATH", "replenishmentRequestDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
