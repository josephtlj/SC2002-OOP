package interfaces;

import java.util.List;

import models.Doctor;

public interface DoctorDaoInterface 
{
    List<Doctor> getAllDoctors();

    Doctor getDoctorByHospitalId(String doctorHospitalId);

    void updateDoctorPasswordByHospitalId(String doctorNewPassword, String doctorHospitalId);

    
}
