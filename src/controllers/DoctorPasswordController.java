package src.controllers;

import src.daos.DoctorDao;
import src.models.ChangePasswordOutcome;
import src.models.Doctor;
import src.services.DoctorPasswordService;
import src.utils.ENUM.PasswordErrorType;


public class DoctorPasswordController 
{
    //ATTRIBUTES
    private DoctorPasswordService doctorPasswordService;

    //CONSTRUCTOR
    public DoctorPasswordController(String ID)
    {
        this.doctorPasswordService= new DoctorPasswordService(ID);
    }

    
    /** 
     * @param newPassword
     * @param confirmPassword
     * @param ID
     * @return ChangePasswordOutcome
     */
    //METHODS
    public ChangePasswordOutcome updateDoctorPassword(String newPassword, String confirmPassword, String ID)
    {
        return doctorPasswordService.updateDoctorPassword(newPassword, confirmPassword, ID);
    }
    
}