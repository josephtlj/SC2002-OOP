package src.services;

import java.time.LocalDate;

import src.daos.DiagnosisTreatmentRecordDao;
import src.models.DiagnosisTreatmentRecord;

public class DiagnosisTreatmentRecordService 
{
    //ATTRIBUTES
    DiagnosisTreatmentRecordDao diagnosisTreatmentRecordDao;

    //CONSTRUCTOR
    public DiagnosisTreatmentRecordService(String patientID)
    {
        this.diagnosisTreatmentRecordDao= new DiagnosisTreatmentRecordDao(patientID);
    }

    
    /** 
     * @param treatRec
     */
    //METHODS
    public void addDiagnosisTreatmentRecord(DiagnosisTreatmentRecord treatRec)
    {
        diagnosisTreatmentRecordDao.addDiagnosisTreatmentRecord(treatRec);
    }

    //GET METHODS

    public String getDiagnosis(LocalDate date)
    {
        return diagnosisTreatmentRecordDao.getDiagnosisTreatmentRecord(date).getDiagnosis();
    }

    public String getPrescription(LocalDate date)
    {
        return diagnosisTreatmentRecordDao.getDiagnosisTreatmentRecord(date).getPrescription();
    }

    public String getTreatmentPlan(LocalDate date)
    {
        return diagnosisTreatmentRecordDao.getDiagnosisTreatmentRecord(date).getTreatmentPlan();
    }

    //SET METHODS

    public boolean setDiagnosis(String diagnosis, LocalDate date)
    {
        return diagnosisTreatmentRecordDao.updateDiagnosisTreatmentRecord(diagnosis,null,null,date);
    }

    public boolean setPrescription(String prescription, LocalDate date)
    {
        return diagnosisTreatmentRecordDao.updateDiagnosisTreatmentRecord(null,prescription,null,date);
    }

    public boolean setTreatmentPlan(String treatmentPlan, LocalDate date)
    {
        return diagnosisTreatmentRecordDao.updateDiagnosisTreatmentRecord(null,null,treatmentPlan,date);
    }
}