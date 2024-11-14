package Setup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import Models.ReplenishmentRequest;

public class ReplenishmentManager {

    // Class-level variable for the replenishment requests list
    private List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

    // Method to create initial replenishment requests
    public void createInitialReplenishmentRequests() {
        // Example initial replenishment requests
        ReplenishmentRequest request1 = new ReplenishmentRequest(
                UUID.randomUUID(),  // Generate a unique UUID for requestID
                "Paracetamol", 
                100, 
                LocalDate.now(), 
                "Pending"
        );

        ReplenishmentRequest request2 = new ReplenishmentRequest(
                UUID.randomUUID(),  // Generate a unique UUID for requestID
                "Ibuprofen", 
                200, 
                LocalDate.now(), 
                "Pending"
        );

        ReplenishmentRequest request3 = new ReplenishmentRequest(
                UUID.randomUUID(),  // Generate a unique UUID for requestID
                "Aspirin", 
                150, 
                LocalDate.now(), 
                "Pending"
        );

        // Add requests to the replenishment request list
        replenishmentRequests.add(request1);
        replenishmentRequests.add(request2);
        replenishmentRequests.add(request3);

        System.out.println("Initial replenishment requests created.");
    }

    // Method to get all replenishment requests (optional, if needed elsewhere)
    public List<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentRequests;
    }
}
