package src.interfaces;

import java.util.List;

import src.models.Doctor;

public interface DoctorDaoInterface 
{
    List<Doctor> getAllDoctors();

    Doctor getDoctorByHospitalId(String doctorHospitalId);

    void updateDoctorPasswordByHospitalId(String doctorNewPassword, byte[] newSalt, String doctorHospitalId);

    
}