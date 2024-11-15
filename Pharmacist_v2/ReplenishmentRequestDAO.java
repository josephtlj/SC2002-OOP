import java.io.*;
import java.util.*;

public class ReplenishmentRequestDAO {

    private UUID requestId;
    private UUID medicineId;  // Added medicineId to link replenishment to a specific medicine
    private int requestedQuantity;
    private String status;  // Default value is "Pending"
    private static final String requestFile = "Pharmacist_v2/ReplenishmentRequest.csv";  // Path for request data
    private static List<ReplenishmentRequestDAO> requests = new ArrayList<>();

    // Default Constructor
    public ReplenishmentRequestDAO() {
        this.requestId = UUID.randomUUID(); // Default to a random UUID
        this.medicineId = UUID.randomUUID();  // Default to a random UUID
        this.requestedQuantity = 0;  // Default to 0 quantity
        this.status = "Pending";  // Default to "Pending" status
    }

    // Constructor with parameters
    public ReplenishmentRequestDAO(UUID requestId, UUID medicineId, int requestedQuantity, String status) {
        this.requestId = requestId;
        this.medicineId = medicineId;
        this.requestedQuantity = requestedQuantity;
        this.status = status;
    }

    // Getter and Setter methods
    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // CRUD Operations: Load requests from CSV
    public static void loadRequestsFromCSV() throws IOException {
        requests.clear();  // Clear existing requests before loading

        try (BufferedReader br = new BufferedReader(new FileReader(requestFile))) {
            String line;
            br.readLine();  // Skip header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);  // Use -1 to keep empty trailing fields
                if (values.length < 4) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;  // Skip invalid lines
                }

                UUID requestId = UUID.fromString(values[0].trim());
                UUID medicineId = UUID.fromString(values[1].trim());
                int requestedQuantity = values[2].trim().isEmpty() ? 0 : Integer.parseInt(values[2].trim());
                String status = values[3].trim().isEmpty() ? "Pending" : values[3].trim();

                ReplenishmentRequestDAO request = new ReplenishmentRequestDAO(requestId, medicineId, requestedQuantity, status);
                requests.add(request);
            }
        }
    }

    // CRUD Operations: Save requests to CSV
    public static void saveRequestsToCSV() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(requestFile))) {
            bw.write("requestId,medicineId,requestedQuantity,status");
            bw.newLine();

            for (ReplenishmentRequestDAO request : requests) {
                StringBuilder sb = new StringBuilder();
                sb.append(request.getRequestId()).append(",");
                sb.append(request.getMedicineId()).append(",");
                sb.append(request.getRequestedQuantity()).append(",");
                sb.append(request.getStatus());
                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }

    // CRUD Operations: Find request by ID
    public static ReplenishmentRequestDAO findRequestById(UUID requestId) {
        for (ReplenishmentRequestDAO request : requests) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }
        return null;  // Return null if not found
    }

    // CRUD Operations: Add a new request
    public static void addRequest(ReplenishmentRequestDAO newRequest) {
        requests.add(newRequest);
    }

    // Get all requests
    public static List<ReplenishmentRequestDAO> getRequests() {
        return requests;
    }
}
