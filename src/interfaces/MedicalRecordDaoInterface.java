package src.interfaces;

import src.models.MedicalRecord;

public interface MedicalRecordDaoInterface {
    MedicalRecord getMedicalRecordByHospitalId(String hospitalId);

    void updateMedicalRecord(MedicalRecord medicalRecord);

}
