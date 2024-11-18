package models;

import java.util.UUID;

public class DiagnosisTreatmentRecord {

    // INSTANCE VARIABLES
    private UUID recordId;
    private String diagnosis;
    private String treatment;

    // INSTANCE METHODS
    public UUID getRecordId() {
        return this.recordId;
    }

    public void setRecordId() {
        this.recordId = UUID.randomUUID();
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return this.treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

}