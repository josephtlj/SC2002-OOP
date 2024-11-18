package src.daos;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import src.models.ReplenishmentRequest;

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

    @Override
    public void createReplenishmentRequest(int replenishmentQuantity, UUID medicineId) {
        ReplenishmentRequest replenishmentRequest = new ReplenishmentRequest(UUID.randomUUID(), replenishmentQuantity,
                ReplenishmentRequest.Status.PENDING, medicineId);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPLENISHMENTREQUESTDB_PATH, true))) {
            
            String csvLine = formatReplenishmentRequestToCSV(replenishmentRequest);

            // WRITE NEW REPLENISHMENTREQUEST OBJECT TO REPLENISHMENTREQUESTDB.CSV
            writer.write(csvLine);
            writer.newLine();

            System.out.println("Replenishment request successfully added.");
        } catch (IOException e) {
            System.err.println("Error writing to the replenishment request database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // HELPER METHOD TO FORMAT REPLENISHMENTREQUEST OBJECT INTO CSV LINE
    private String formatReplenishmentRequestToCSV(ReplenishmentRequest replenishmentRequest) {
        return String.join(",",
                replenishmentRequest.getRequestId().toString(),
                String.valueOf(replenishmentRequest.getRequestedQuantity()),
                replenishmentRequest.getStatus().name(),
                replenishmentRequest.getMedicineId().toString()
        );
    }
}