import java.time.LocalDate;
import java.util.UUID;

public class DiagnosisTreatmentRecord 
{
    // Attributes
    private String recordId;
    private String diagnosis;
    private String treatment;
    private LocalDate recordDate; 

    // Constructor
    public DiagnosisTreatmentRecord(String diagnosis, String treatment, LocalDate recordDate) 
    {
        this.recordId = generateRecordId();
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.recordDate = recordDate; 
    }

    // Methods
    public String generateRecordId() 
    {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    public String getRecordId() 
    {
        return recordId;
    }

    public void setDiagnosis(String diagnosis) 
    {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() 
    {
        return diagnosis;
    }

    public void setTreatment(String treatment) 
    {
        this.treatment = treatment;
    }

    public String getTreatment() 
    {
        return treatment;
    }

    public LocalDate getRecordDate() 
    {
        return recordDate;
    }
}
