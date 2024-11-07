package HMS.src.models;

import java.util.UUID;

public class DiagnosisTreatmentRecord {

    // INSTANCE VARIABLES
    private UUID recordId;
    private String diagnosis;
    private String treatment;

    // INSTANCE METHODS
    public UUID getRecordId(){
        return this.recordId;
    }

    public String getDiagnosis(){
        return this.diagnosis;
    }

    public String getTreatment(){
        return this.treatment;
    }

    public void setRecordId(){
        this.recordId = UUID.randomUUID();
    }

    public void setDiagnosis(String diagnosis){
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment){
        this.treatment = treatment;
    }
}
