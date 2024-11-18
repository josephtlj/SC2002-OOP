package interfaces;

import java.time.LocalDate;
import java.util.List;

import models.DiagnosisTreatmentRecord;

public interface DiagnosisTreatmentRecordDaoInterface 
{
    boolean updateDiagnosisTreatmentRecord(String diagnosis, String prescription, String treatmentPlan, LocalDate date);

    DiagnosisTreatmentRecord getDiagnosisTreatmentRecord(LocalDate date);

    List<DiagnosisTreatmentRecord> getAllDiagnosisTreatmentRecords(String patientID);

    void addDiagnosisTreatmentRecord(DiagnosisTreatmentRecord treatRec);

    
}
