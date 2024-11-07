package HMS.src;
import java.util.UUID;

public class DiagnosisTreatmentRecord {
    // Instance Variables
    private UUID recordId;
    private String diagnosis;
    private String treatment;

    private MedicalRecord medicalRecord;

    // Instance Methods
    public DiagnosisTreatmentRecord() {
        this.recordId = UUID.randomUUID();
        this.diagnosis = null;
        this.treatment = null;
        this.medicalRecord = null;
    }

    public DiagnosisTreatmentRecord(String diagnosis, String treatment, MedicalRecord medicalRecord) {
        this.recordId = UUID.randomUUID();
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.medicalRecord = medicalRecord;
    }

    // GET Methods
    public UUID getRecordId() {
        return this.recordId;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public String getTreatment() {
        return this.treatment;
    }

    public MedicalRecord getMedicalRecord() {
        return this.medicalRecord;
    }

    // SET Methods
    public void setDiagnosis(String diagnosis){
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment){
        this.treatment = treatment;
    }

    // Supporting Functions
}
