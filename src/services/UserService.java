package src.services;

import src.models.Session;
import src.models.User;

import src.interfaces.UserServiceInterface;
import src.interfaces.PatientServiceInterface;
import src.interfaces.PharmacistServiceInterface;
import src.interfaces.AdministratorServiceInterface;

import src.interfaces.UserDaoInterface;
import src.interfaces.PatientDaoInterface;
import src.interfaces.PharmacistDaoInterface;
import src.interfaces.AdministratorDaoInterface;

import java.util.Base64;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserService implements UserServiceInterface {
    private final UserDaoInterface userDao;
    private final PatientServiceInterface patientService;
    private final PharmacistServiceInterface pharmacistService;
    private final AdministratorServiceInterface administratorService;

    public UserService(UserDaoInterface userDao, PatientServiceInterface patientService,
            PharmacistServiceInterface pharmacistService, AdministratorServiceInterface administratorService) {
        this.userDao = userDao;
        this.patientService = patientService;
        this.pharmacistService = pharmacistService;
        this.administratorService = administratorService;
    }

    
    /** 
     * @param hospitalId
     * @param password
     * @return User
     */
    @Override
    public User login(String hospitalId, String password) {
        String rolePrefix = hospitalId.substring(0, 2);

        User user = null;

        switch (rolePrefix) {
            case "PA":
                user = patientService.readPatientByHospitalId(hospitalId);
                break;
            case "PH":
                user = pharmacistService.readPharmacistByHospitalId(hospitalId);
                break;
            case "AD":
                user = administratorService.readAdministratorByHospitalId(hospitalId);
                break;
            default:
                DoctorPasswordService doctorPasswordService = new DoctorPasswordService(hospitalId);
                try {
                    user = doctorPasswordService.readDoctorByHospitalId(hospitalId);
                } catch (Exception e) {
                    System.out.println(e);
                }

                break;
        }

        if (user == null || !Objects.equals(hashPassword(password, user.getSalt()), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials!");
        }

        Session.getCurrentSession().setCurrentUser(user);

        return user;
    }

    @Override
    public void logout() {
        Session.getCurrentSession().logout();
    }

    
    /** 
     * @param hospitalId
     * @return boolean
     */
    @Override
    public boolean isFirstLogin(String hospitalId) {
        User user = userDao.getUserByHospitalId(hospitalId);
        return user != null && user.getIsFirstLogin();
    }

    
    /** 
     * @param password
     * @param salt
     * @return String
     */
    // SUPPORTING METHODS
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
