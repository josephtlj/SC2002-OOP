package Doctor.DiagnosisTreatmentRecord;

import daos.DiagnosisTreatmentRecordDao;
import java.time.*;

public class DiagnosisTreatmentRecordManager 
{
    //ATTRIBUTES
    DiagnosisTreatmentRecordDao treatRecDao;

    //CONSTRUCTOR
    public DiagnosisTreatmentRecordManager(DiagnosisTreatmentRecordDao treatRecDao)
    {
        this.treatRecDao= treatRecDao;
    }

    //METHODS

    //EDIT DIAGNOSIS TREATMENT RECORD

    //EDIT DIAGNOSIS
    public void editDiagnosis(String diagnosis)
    {
        //implementation
    }

    //EDIT PRESCRIPTION
    public void editPresription(String prescription)
    {
        //implementation
    }

    //EDIT TREATMENT PLAN
    public void editTreatmentPlan(String treatPlan)
    {
        //implementation
    }

    //GET METHODS

    public String getDiagnosis(LocalDate date)
    {
        return treatRecDao.getDiagnosisTreatmentRecord(date).getDiagnosis();
    }

    public String getPrescription(LocalDate date)
    {
        return treatRecDao.getDiagnosisTreatmentRecord(date).getPrescription();
    }

    public String getTreatmentPlan(LocalDate date)
    {
        return treatRecDao.getDiagnosisTreatmentRecord(date).getTreatmentPlan();
    }

    //SET METHODS

    public void setDiagnosis(String diagnosis, LocalDate date)
    {
        treatRecDao.updateDiagnosisTreatmentRecord(diagnosis,null,null,date);
    }

    public void setPrescription(String prescription, LocalDate date)
    {
        treatRecDao.updateDiagnosisTreatmentRecord(null,prescription,null,date);
    }

    public void setTreatmentPlan(String treatmentPlan, LocalDate date)
    {
        treatRecDao.updateDiagnosisTreatmentRecord(null,null,treatmentPlan,date);
    }
}
