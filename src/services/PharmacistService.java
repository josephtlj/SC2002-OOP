package src.services;

import src.models.Pharmacist;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import src.interfaces.PharmacistDaoInterface;
import src.interfaces.PharmacistServiceInterface;

public class PharmacistService implements PharmacistServiceInterface {

    private final PharmacistDaoInterface pharmacistDao;

    public PharmacistService(PharmacistDaoInterface pharmacistDao) {
        this.pharmacistDao = pharmacistDao;
    }

    
    /** 
     * @param hospitalId
     * @return Pharmacist
     */
    @Override
    public Pharmacist readPharmacistByHospitalId(String hospitalId){
        Pharmacist pharmacist = pharmacistDao.getPharmacistByHospitalId(hospitalId);
        if (pharmacist == null) {
            throw new IllegalArgumentException("Pharmacist not found.");
        }

        return pharmacist;
    }

    
    /** 
     * @param hospitalId
     * @param newPassword
     * @param confirmPassword
     */
    @Override
    public void updatePassword(String hospitalId, String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("The new passwords do not match.");
        }

        Pharmacist pharmacist = pharmacistDao.getPharmacistByHospitalId(hospitalId);

        if (pharmacist == null) {
            throw new IllegalArgumentException("Pharmacist not found.");
        }

        if (hashPassword(newPassword, pharmacist.getSalt()).equals(pharmacist.getPassword())) {
            throw new IllegalArgumentException("The new password cannot be the same as the old password.");
        }

        byte[] newSalt = generateSalt();
        String hashedPassword = hashPassword(newPassword, newSalt);
        pharmacist.setSalt(newSalt);
        pharmacist.setPassword(hashedPassword);
        pharmacist.setIsFirstLogin(false);
        pharmacistDao.updatePharmacist(pharmacist);
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
