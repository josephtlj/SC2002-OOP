package src.interfaces;

import java.util.List;

import src.models.Doctor;
import src.models.Staff;

public interface DoctorDaoInterface 
{
    List<Doctor> getAllDoctors();

    Doctor getDoctorByHospitalId(String doctorHospitalId);

    List<Staff> getAllStaffDoctors();

    void updateDoctorPasswordByHospitalId(String doctorNewPassword, byte[] newSalt, String doctorHospitalId);

    
}