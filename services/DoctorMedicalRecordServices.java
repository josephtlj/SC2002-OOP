package services;

import java.util.List;

import daos.MedicalRecordDao;
import models.MedicalRecord;

public class DoctorMedicalRecordServices 
{
    //ATTRIBUTES
    MedicalRecordDao medicalRecordDao= new MedicalRecordDao();

    //METHODS
    public MedicalRecord getMedicalRecord(String patientID)
    {
        return medicalRecordDao.getMedicalRecordByHospitalId(patientID);
    }

    public List<MedicalRecord> getAllMedicalRecords()
    {
        return medicalRecordDao.getAllMedicalRecords();
    }
}
