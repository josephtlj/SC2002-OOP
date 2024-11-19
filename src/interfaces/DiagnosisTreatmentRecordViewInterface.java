package src.interfaces;

import java.time.LocalDate;

public interface DiagnosisTreatmentRecordViewInterface 
{
    void printUpdateDiagnosisTreatmentRecordView();

    void printUpdateDiagnosis(LocalDate date);

    void printUpdatePrescription(LocalDate date);

    void printUpdateTreatmentPlan(LocalDate date);

    void printGetPatientID();
}