package src.models;

import java.util.UUID;

public class ReplenishmentRequest {

    public enum Status{
        PENDING,
        CONFIRMED
    }

    // INSTANCE VARIABLES
    private UUID requestId;
    private int requestedQuantity;
    private Status status;

    private UUID medicineId;

    // CONSTRUCTOR
    public ReplenishmentRequest(UUID requestId, int requestedQuantity, Status status, UUID medicineId) {
        this.requestId = requestId;
        this.requestedQuantity = requestedQuantity;
        this.status = status;
        this.medicineId = medicineId;
    }

    // GETTERS AND SETTERS
    public UUID getRequestId(){
        return this.requestId;
    }

    public void setRequestId(UUID requestId){
        this.requestId = requestId;
    }

    public int getRequestedQuantity(){
        return this.requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity){
        this.requestedQuantity = requestedQuantity;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public UUID getMedicineId(){
        return this.medicineId;
    }

    public void setMedicineId(UUID medicineId){
        this.medicineId = medicineId;
    }
}
