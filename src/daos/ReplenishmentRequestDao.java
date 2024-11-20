package src.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    
    /** 
     * @param medicineName
     * @param replenishmentQuantity
     * @param medicineId
     */
    @Override
    public void createReplenishmentRequest(String medicineName, int replenishmentQuantity, UUID medicineId) {
        ReplenishmentRequest replenishmentRequest = new ReplenishmentRequest(UUID.randomUUID(), medicineName,
                replenishmentQuantity,
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

    @Override
    public ReplenishmentRequest getReplenishmentRequestByRequestId(UUID requestId) {
        try (BufferedReader br = new BufferedReader(new FileReader(REPLENISHMENTREQUESTDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                ReplenishmentRequest replenishmentRequest = parseReplenishmentRequest(line);
                if (replenishmentRequest != null && replenishmentRequest.getRequestId().equals(requestId)) {
                    return replenishmentRequest;
                }
            }

            // IF REPLENISHMENT REQUEST NOT FOUND
            return null;

        } catch (IOException e) {
            System.err.println("Error reading replenishment request database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReplenishmentRequest> getAllReplenishmentRequestsByStatus(ReplenishmentRequest.Status status) {
        List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(REPLENISHMENTREQUESTDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                ReplenishmentRequest replenishmentRequest = parseReplenishmentRequest(line);
                if (replenishmentRequest != null && replenishmentRequest.getStatus().equals(status)) {
                    replenishmentRequests.add(replenishmentRequest);
                }
            }

            return replenishmentRequests;

        } catch (IOException e) {
            System.err.println("Error reading replenishment request database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateReplenishmentRequest(ReplenishmentRequest replenishmentRequest) {
        List<String> lines = new ArrayList<>();
        boolean replenishmentRequestFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(REPLENISHMENTREQUESTDB_PATH))) {
            // SKIP HEADER ROW
            String header = br.readLine();
            // KEEP HEADER ROW IN OUTPUT
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 5 && UUID.fromString(fields[0]).equals(replenishmentRequest.getRequestId())) {
                    // UPDATE MATCHING REPLENISHMENT REQUEST RECORD
                    String updatedLine = formatReplenishmentRequestToCSV(replenishmentRequest);
                    lines.add(updatedLine);
                    replenishmentRequestFound = true;
                } else {
                    // KEEP EXISTING REPLENISHMENT REQUEST RECORD
                    lines.add(line);
                }
            }

            if (!replenishmentRequestFound) {
                throw new IllegalArgumentException(
                        "Replenishment Request with requestId " + replenishmentRequest.getRequestId() + " not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the replenishment request database: " + e.getMessage());
            e.printStackTrace();
        }

        // UPDATE REPLENISHMENTREQUESTDB.CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REPLENISHMENTREQUESTDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the replenishment request database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void deleteReplenishmentRequestsByMedicineId(UUID medicineId) {
        List<String> lines = new ArrayList<>();
        boolean requestFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(REPLENISHMENTREQUESTDB_PATH))) {
            // READ HEADER ROW
            String header = br.readLine();
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                ReplenishmentRequest replenishmentRequest = parseReplenishmentRequest(line);

                if (replenishmentRequest != null
                        && replenishmentRequest.getMedicineId().equals(medicineId)
                        && replenishmentRequest.getStatus() == ReplenishmentRequest.Status.PENDING) {
                    // EXCLUDE MATCHING REPLENISHMENTREQUEST OBJECT WITH CORRESPONDING STATUS AND MEDICINEID
                    requestFound = true;
                    continue;
                }

                
                lines.add(line);
            }

            if (!requestFound) {
                System.out.println("No matching replenishment requests found for medicineId: " + medicineId);
            }

        } catch (IOException e) {
            System.err.println("Error reading the replenishment request database: " + e.getMessage());
            e.printStackTrace();
        }

        // REWRITE TO REPLENISHMENTREQUESTDB.CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REPLENISHMENTREQUESTDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the replenishment request database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // HELPER METHOD TO FORMAT REPLENISHMENTREQUEST OBJECT INTO CSV LINE
    private String formatReplenishmentRequestToCSV(ReplenishmentRequest replenishmentRequest) {
        return String.join(",",
                replenishmentRequest.getRequestId().toString(),
                replenishmentRequest.getMedicineName(),
                String.valueOf(replenishmentRequest.getRequestedQuantity()),
                replenishmentRequest.getStatus().name(),
                replenishmentRequest.getMedicineId().toString());
    }

    // HELPER METHOD TO PARSE A REPLENISHMENTREQUEST OBJECT FROM A CSV LINE
    private ReplenishmentRequest parseReplenishmentRequest(String line) {

        try {
            String[] fields = line.split(",");
            if (fields.length != 5) {
                System.err.println("Invalid record format: " + line);
                return null;
            }

            UUID requestId = UUID.fromString(fields[0]);
            String medicineName = fields[1];
            int requestedQuantity = Integer.parseInt(fields[2]);
            ReplenishmentRequest.Status status = ReplenishmentRequest.Status.valueOf(fields[3]);
            UUID medicineId = UUID.fromString(fields[4]);

            return new ReplenishmentRequest(requestId, medicineName, requestedQuantity, status, medicineId);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing record: " + line + " - " + e.getMessage());
            return null;
        }
    }
}