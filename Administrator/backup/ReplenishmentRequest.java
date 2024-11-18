package Administrator.backup;

import java.time.LocalDate;
import java.util.UUID;

public class ReplenishmentRequest {
    private UUID requestID;
    private String medicineName;
    private int quantity;
    private LocalDate requestDate;
    private String status;

    public ReplenishmentRequest(UUID requestID, String medicineName, int quantity, LocalDate requestDate, String status) {
        this.requestID = requestID;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.requestDate = requestDate;
        this.status = status;
    }

    // Getters and Setters
    public UUID getRequestID() {
        return requestID;
    }

    public void setRequestID(UUID requestID) {
        this.requestID = requestID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReplenishmentRequest{" +
                "requestID=" + requestID +
                ", medicineName='" + medicineName + '\'' +
                ", quantity=" + quantity +
                ", requestDate=" + requestDate +
                ", status='" + status + '\'' +
                '}';
    }
}
