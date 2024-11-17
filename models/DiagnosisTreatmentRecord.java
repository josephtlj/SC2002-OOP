package models;

import Doctor.Appointment.Appointment;

public class DiagnosisTreatmentRecord 
{
    // Attributes
    private String diagnosis;
    private String prescription;
    private String treatmentPlan;
    private Appointment appointment;

    // Constructor
    public DiagnosisTreatmentRecord(String diagnosis, String prescription,String treatmentPlan, Appointment appointment) 
    {
        this.diagnosis = diagnosis;
        this.prescription= prescription;
        this.treatmentPlan= treatmentPlan;
        this.appointment= appointment;
    }

    // Methods
    public void setAppointment(Appointment appointment)
    {
        this.appointment=appointment;
    }

    public Appointment getAppointment()
    {
        return this.appointment;
    }
    public void setDiagnosis(String diagnosis) 
    {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() 
    {
        return this.diagnosis;
    }

    public void setTreatmentPlan(String treatmentPlan) 
    {
        this.treatmentPlan = treatmentPlan;
    }

    public String getTreatmentPlan() 
    {
        return this.treatmentPlan;
    }

    public void setPrescription(String prescription)
    {
        this.prescription= prescription;
    }

    public String getPrescription()
    {
        return this.prescription;
    }

}
