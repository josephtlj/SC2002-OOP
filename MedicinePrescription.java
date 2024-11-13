import java.util.UUID;

public class MedicinePrescription 
{

    // Data members (fields)
    private UUID prescriptionId;
    private String medicineName;
    private String status = "Pending";  // Default status is "Pending"

    // Constructor
    public MedicinePrescription(UUID prescriptionId, String medicineName) 
    {
        this.prescriptionId = prescriptionId;
        this.medicineName = medicineName;
    }

    // Getter for prescriptionId
    public UUID getPrescriptionId() 
    {
        return prescriptionId;
    }

    // Setter for prescriptionId
    public void setPrescriptionId(UUID prescriptionId) 
    {
        this.prescriptionId = prescriptionId;
    }

    // Getter for medicineName
    public String getMedicineName() 
    {
        return medicineName;
    }

    // Setter for medicineName
    public void setMedicineName(String medicineName) 
    {
        this.medicineName = medicineName;
    }

    // Getter for status
    public String getStatus() 
    {
        return status;
    }

    // Setter for status
    public void setStatus(String status) 
    {
        this.status = status;
    }
}
