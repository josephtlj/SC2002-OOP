package Doctor.MedicalRecord;

import daos.MedicalRecordDao;
import models.MedicalRecord;
import java.util.*;

public class DoctorMedicalRecordManager 
{
    //ATTRIBUTES
    MedicalRecordDao medicalRecordDao;

    //CONSTRUCTOR
    public DoctorMedicalRecordManager(MedicalRecordDao medicalRecordDao)
    {
        this.medicalRecordDao= medicalRecordDao;
    }

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
