package Doctor;

import daos.DoctorDao;

public class DoctorPasswordManager 
{
    //ATTRIBUTES
    private DoctorDao doctorDao;

    //CONSTRUCTOR
    public DoctorPasswordManager(DoctorDao doctorDao)
    {
        this.doctorDao= doctorDao;
    }

    //METHODS
    public ChangePasswordOutcome updateDoctorPassword(String newPassword, String confirmPassword, String ID)
    {
        ChangePasswordOutcome passwordOutcome= new ChangePasswordOutcome(false, PasswordErrorType.NILL);
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
