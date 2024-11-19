package src.interfaces;

import java.util.List;

import src.models.MedicalRecord;

public interface MedicalRecordDaoInterface {

    List<MedicalRecord> getAllMedicalRecords();

    MedicalRecord getMedicalRecordByHospitalId(String hospitalId);

    void updateMedicalRecord(MedicalRecord medicalRecord);

}
