package interfaces;

import java.util.List;

import models.MedicalRecord;

public interface MedicalRecordDaoInterface 
{
    List<MedicalRecord> getAllMedicalRecords();

    MedicalRecord getMedicalRecordByHospitalId(String userHospitalId);

    
}
