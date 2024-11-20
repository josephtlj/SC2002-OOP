package src.services;

import java.util.List;

import src.daos.MedicalRecordDao;
import src.models.MedicalRecord;

public class DoctorMedicalRecordServices 
{
    //ATTRIBUTES
    MedicalRecordDao medicalRecordDao= new MedicalRecordDao();

    
    /** 
     * @param patientID
     * @return MedicalRecord
     */
    //METHODS
    public MedicalRecord getMedicalRecord(String patientID)
    {
        return medicalRecordDao.getMedicalRecordByHospitalId(patientID);
    }

    
    /** 
     * @return List<MedicalRecord>
     */
    public List<MedicalRecord> getAllMedicalRecords()
    {
        return medicalRecordDao.getAllMedicalRecords();
    }
}