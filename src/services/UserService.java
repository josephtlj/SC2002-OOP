package src.services;

import src.models.Session;
import src.models.User;
import src.interfaces.UserServiceInterface;
import src.interfaces.UserDaoInterface;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserService implements UserServiceInterface {
    private final UserDaoInterface userDao;

    public UserService(UserDaoInterface userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String hospitalId, String password) {
        User user = userDao.getPatientByHospitalId(hospitalId);
        System.out.println(hashPassword(password, user.getSalt()));
        System.out.println(user.getPassword());
        if (user == null || !Objects.equals(hashPassword(password, user.getSalt()), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials!");
        }

        Session.getCurrentSession().setCurrentUser(user);

        return user;
    }

    @Override
    public void updatePassword(String hospitalId, String newPassword) {
        User user = userDao.getUserByHospitalId(hospitalId);
        if (user != null) {
            byte[] newSalt = generateSalt();
            String hashedPassword = hashPassword(newPassword, newSalt);
            user.setPassword(hashedPassword);
            userDao.updateUser(user);
        } else {
            throw new IllegalArgumentException("User not found!");
        }
    }

    // @Override
    // public void resetPassword(String hospitalId) {
    // User user = userDao.getUserByHospitalId(hospitalId);
    // if (user != null) {
    // user.setPassword("password");
    // userDao.updateUser(user);
    // } else {
    // throw new IllegalArgumentException("User not found!");
    // }
    // }

    @Override
    public boolean isFirstLogin(String hospitalId) {
        User user = userDao.getUserByHospitalId(hospitalId);
        return user != null && user.getIsFirstLogin();
    }

    @Override
    public void logout() {
        Session.getCurrentSession().logout();
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
