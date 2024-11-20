package src.controllers;

import src.models.DiagnosisTreatmentRecord;
import src.services.DiagnosisTreatmentRecordService;

import java.time.*;

public class DiagnosisTreatmentRecordController 
{
    //ATTRIBUTES
    DiagnosisTreatmentRecordService diagnosisTreatmentRecordService;

    //CONSTRUCTOR
    public DiagnosisTreatmentRecordController(String patientID)
    {
        this.diagnosisTreatmentRecordService= new DiagnosisTreatmentRecordService(patientID);
    }

    
    /** 
     * @param treatRec
     */
    //METHODS

    //EDIT DIAGNOSIS TREATMENT RECORD

    //ADD DIAGNOSIS TREATMENT RECORD
    public void addDiagnosisTreatmentRecord(DiagnosisTreatmentRecord treatRec)
    {
        diagnosisTreatmentRecordService.addDiagnosisTreatmentRecord(treatRec);
    }


    
    /** 
     * @param date
     * @return String
     */
    //GET METHODS

    public String getDiagnosis(LocalDate date)
    {
        return diagnosisTreatmentRecordService.getDiagnosis(date);
    }

    
    /** 
     * @param date
     * @return String
     */
    public String getPrescription(LocalDate date)
    {
        return diagnosisTreatmentRecordService.getPrescription(date);
    }

    public String getTreatmentPlan(LocalDate date)
    {
        return diagnosisTreatmentRecordService.getTreatmentPlan(date);
    }

    //SET METHODS

    public void setDiagnosis(String diagnosis, LocalDate date)
    {
        diagnosisTreatmentRecordService.setDiagnosis(diagnosis,date);
    }

    public void setPrescription(String prescription, LocalDate date)
    {
        diagnosisTreatmentRecordService.setPrescription(prescription,date);
    }

    public void setTreatmentPlan(String treatmentPlan, LocalDate date)
    {
        diagnosisTreatmentRecordService.setTreatmentPlan(treatmentPlan,date);
    }
}