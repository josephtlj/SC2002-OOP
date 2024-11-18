package controllers;

import daos.DoctorDao;
import models.ChangePasswordOutcome;
import models.Doctor;
import services.DoctorPasswordService;
import Enum.PasswordErrorType;


public class DoctorPasswordController 
{
    //ATTRIBUTES
    private DoctorPasswordService doctorPasswordService;

    //CONSTRUCTOR
    public DoctorPasswordController(String ID)
    {
        this.doctorPasswordService= new DoctorPasswordService(ID);
    }

    //METHODS
    public ChangePasswordOutcome updateDoctorPassword(String newPassword, String confirmPassword, String ID)
    {
        return doctorPasswordService.updateDoctorPassword(newPassword, confirmPassword, ID);
    }
    
}
