package src.models;

import java.util.UUID;

public class Prescription {

    public enum Status{
        PENDING,
        DISPENSED
    }

    // INSTANCE VARIABLES
    private UUID prescriptionId;
    private String medicineName;
    private Status status;
    private String medicineId;

    // CONSTRUCTOR
    public Prescription(UUID prescriptionId, String medicineName, Status status, String medicineId){
        this.prescriptionId = prescriptionId;
        this.medicineName = medicineName;
        this.status = status;
        this.medicineId = medicineId;
    }

    
    /** 
     * @return UUID
     */
    // GETTERS AND SETTERS
    public UUID getPrescriptionId(){
        return this.prescriptionId;
    }

    
    /** 
     * @param prescriptionId
     */
    public void setPrescriptionId(UUID prescriptionId){
        this.prescriptionId = prescriptionId;
    }

    public String getMedicineName(){
        return this.medicineName;
    }

    public void setMedicineName(String medicineName){
        this.medicineName = medicineName;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public String getMedicineId(){
        return this.medicineId;
    }

    public void setMedicineId(String medicineId){
        this.medicineId = medicineId;
    }
}
