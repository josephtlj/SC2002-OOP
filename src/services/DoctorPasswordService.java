package src.services;

import src.utils.ENUM.PasswordErrorType;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import src.daos.DoctorDao;
import src.models.ChangePasswordOutcome;
import src.models.Doctor;

public class DoctorPasswordService 
{
    //ATTRIBUTES
    private DoctorDao doctorDao;

    //CONSTRUCTOR
    public DoctorPasswordService(String ID)
    {
        this.doctorDao= new DoctorDao();
    }

    public Doctor readDoctorByHospitalId(String hospitalId){
        Doctor doctor = doctorDao.getDoctorByHospitalId(hospitalId);
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor not found.");
        }
        return doctor;
    }

    public ChangePasswordOutcome updateDoctorPassword(String newPassword, String confirmPassword, String ID)
    {
        ChangePasswordOutcome passwordOutcome= new ChangePasswordOutcome(false, PasswordErrorType.NILL);//why is there an error here
        if(!newPassword.equals(confirmPassword))
        {
            passwordOutcome.setPasswordErrorType(PasswordErrorType.NO_MATCH);
            return passwordOutcome;
        }
        else
        {
            Doctor currentDoctor = doctorDao.getDoctorByHospitalId(ID);
            if (Objects.equals(hashPassword(newPassword, currentDoctor.getSalt()), currentDoctor.getPassword()))
            {
                passwordOutcome.setPasswordErrorType(PasswordErrorType.SAME_AS_OLD);
                return passwordOutcome;
            }
            else{
                byte[] newSalt = generateSalt();
                String hashedNewPassword = hashPassword(confirmPassword, newSalt);

                doctorDao.updateDoctorPasswordByHospitalId(hashedNewPassword, newSalt, ID);
            }
        }
        //no password change error
        passwordOutcome.setOutcome(true);
        return passwordOutcome;
    }

    // SUPPORTING METHODS
    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        try {
            SecureRandom.getInstanceStrong().nextBytes(salt);
        } catch (Exception e) {
            throw new RuntimeException("Error generating salt!", e);
        }
        return salt;
    }

    public String hashPassword(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password!", e);
        }
    }
}