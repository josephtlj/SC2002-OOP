package services;

import Enum.PasswordErrorType;
import daos.DoctorDao;
import models.ChangePasswordOutcome;
import models.Doctor;

public class DoctorPasswordService 
{
    //ATTRIBUTES
    private DoctorDao doctorDao;

    //CONSTRUCTOR
    public DoctorPasswordService(String ID)
    {
        this.doctorDao= new DoctorDao();
    }

    public ChangePasswordOutcome updateDoctorPassword(String newPassword, String confirmPassword, String ID)
    {
        ChangePasswordOutcome passwordOutcome= new ChangePasswordOutcome(false, PasswordErrorType.NILL);

        if (newPassword.length() < 8 || confirmPassword.length() < 8) 
        {
            passwordOutcome.setPasswordErrorType(PasswordErrorType.NOT_ENOUGH_CHAR);
            return passwordOutcome;
        }
        if(!newPassword.equals(confirmPassword))
        {
            passwordOutcome.setPasswordErrorType(PasswordErrorType.NO_MATCH);
            return passwordOutcome;
        }
        else
        {
            Doctor currentDoctor = doctorDao.getDoctorByHospitalId(ID);
            if (currentDoctor.hashPassword(newPassword, currentDoctor.getSalt()).equals(currentDoctor.getPassword()))
            {
                passwordOutcome.setPasswordErrorType(PasswordErrorType.SAME_AS_OLD);
                return passwordOutcome;
            }
        }
        //no password change error
        passwordOutcome.setOutcome(true);
        return passwordOutcome;
    }
}
