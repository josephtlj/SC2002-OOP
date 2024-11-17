package src.services;

import src.models.Session;
import src.models.User;
import src.interfaces.UserServiceInterface;
import src.interfaces.UserDaoInterface;
import src.interfaces.PatientDaoInterface;

import java.util.Base64;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserService implements UserServiceInterface {
    private final UserDaoInterface userDao;
    private final PatientDaoInterface patientDao;

    public UserService(UserDaoInterface userDao, PatientDaoInterface patientDao) {
        this.userDao = userDao;
        this.patientDao = patientDao;
    }

    @Override
    public User login(String hospitalId, String password) {
        String rolePrefix = hospitalId.substring(0, 2);

        User user = null;
        ;

        switch (rolePrefix) {
            case "PA":
                user = patientDao.getPatientByHospitalId(hospitalId);
                break;

            default:
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

    @Override
    public boolean isFirstLogin(String hospitalId) {
        User user = userDao.getUserByHospitalId(hospitalId);
        return user != null && user.getIsFirstLogin();
    }

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
