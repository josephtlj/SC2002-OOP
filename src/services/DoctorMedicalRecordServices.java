package src.services;

import java.util.List;

import src.daos.MedicalRecordDao;
import src.models.MedicalRecord;

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