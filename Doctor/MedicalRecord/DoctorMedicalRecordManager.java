package Doctor.MedicalRecord;

import daos.MedicalRecordDao;
import java.util.*;
import models.MedicalRecord;

public class DoctorMedicalRecordManager 
{
    //ATTRIBUTES
    MedicalRecordDao doctorMedicalRecordDao;

    //CONSTRUCTOR
    public DoctorMedicalRecordManager(MedicalRecordDao doctorMedicalRecordDao)
    {
        this.doctorMedicalRecordDao= doctorMedicalRecordDao;
    }

    //METHODS

    //GET ALL MEDICAL RECORDS
    public List<MedicalRecord> getAllMedicalRecords()
    {
        return doctorMedicalRecordDao.getAllMedicalRecords();
    }

    //UPDATE MEDICAL REDORD
    public boolean updateMedicalRecord()
    {
        
    }
}
