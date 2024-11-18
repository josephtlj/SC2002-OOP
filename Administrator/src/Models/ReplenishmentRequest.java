package Administrator.src.Models;

import java.util.UUID;

public class ReplenishmentRequest {

    // Data members
    private UUID requestId;
    private int requestedQuantity;
    private String status = "Pending";  // Default value is "Pending"

    // Default Constructor
    public ReplenishmentRequest() {
        this.requestId = UUID.randomUUID();  // Default to a random UUID
        this.requestedQuantity = 0;          // Default requested quantity
        // 'status' is already initialized to "Pending"
    }

    // Constructor with parameters
    public ReplenishmentRequest(UUID requestId, int requestedQuantity, String status) {
        this.requestId = requestId;
        this.requestedQuantity = requestedQuantity;
        this.status = status;
    }

    // Getter for requestId
    public UUID getRequestId() {
        return requestId;
    }

    // Setter for requestId
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    // Getter for requestedQuantity
    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    // Setter for requestedQuantity
    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Uncomment the following attributes and methods if we need currently not in mehars
    /*
    private String medicineName;
    private LocalDate requestDate;

    // Getter and Setter for medicineName
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    // Getter and Setter for requestDate
    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
    */

    @Override
    public String toString() {
        return "ReplenishmentRequest{" +
                "requestId=" + requestId +
                ", requestedQuantity=" + requestedQuantity +
                ", status='" + status + '\'' +
                '}';
    }
}
