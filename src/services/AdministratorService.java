package src.services;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import src.models.Administrator;

import src.interfaces.AdministratorServiceInterface;

import src.interfaces.AdministratorDaoInterface;

public class AdministratorService implements AdministratorServiceInterface {
    private final AdministratorDaoInterface administratorDao;

    public AdministratorService(AdministratorDaoInterface administratorDao) {
        this.administratorDao = administratorDao;
    }

    @Override
    public void updatePassword(String hospitalId, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("The new passwords do not match.");
        }

        Administrator administrator = administratorDao.getAdministratorByHospitalId(hospitalId);

        if (administrator == null) {
            throw new IllegalArgumentException("Administrator not found.");
        }

        if (hashPassword(newPassword, administrator.getSalt()).equals(administrator.getPassword())) {
            throw new IllegalArgumentException("The new password cannot be the same as the old password.");
        }

        byte[] newSalt = generateSalt();
        String hashedPassword = hashPassword(newPassword, newSalt);
        administrator.setSalt(newSalt);
        administrator.setPassword(hashedPassword);
        administratorDao.updateAdministrator(administrator);
    }

    @Override
    public Administrator readAdministratorByHospitalId(String hospitalId) {
        Administrator administrator = administratorDao.getAdministratorByHospitalId(hospitalId);
        if (administrator == null) {
            throw new IllegalArgumentException("Administrator not found.");
        }

        return administrator;
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
