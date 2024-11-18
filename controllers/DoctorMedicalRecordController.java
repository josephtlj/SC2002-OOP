package controllers;

import models.MedicalRecord;
import services.DoctorMedicalRecordServices;

import java.util.*;

public class DoctorMedicalRecordController 
{
    //ATTRIBUTES
    DoctorMedicalRecordServices doctorMedicalRecordServices= new DoctorMedicalRecordServices();


    //METHODS
    public MedicalRecord getMedicalRecord(String patientID)
    {
        return doctorMedicalRecordServices.getMedicalRecord(patientID);
    }

    public List<MedicalRecord> getAllMedicalRecords()
    {
        return doctorMedicalRecordServices.getAllMedicalRecords();
    }

}
