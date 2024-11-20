package src.services;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import src.models.Patient;

import src.interfaces.PatientServiceInterface;

import src.interfaces.PatientDaoInterface;

public class PatientService implements PatientServiceInterface {
    private final PatientDaoInterface patientDao;

    public PatientService(PatientDaoInterface patientDao) {
        this.patientDao = patientDao;
    }

    
    /** 
     * @param hospitalId
     * @return Patient
     */
    @Override
    public Patient readPatientByHospitalId(String hospitalId) {
        Patient patient = patientDao.getPatientByHospitalId(hospitalId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found.");
        }

        return patient;
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

        Patient patient = patientDao.getPatientByHospitalId(hospitalId);

        if (patient == null) {
            throw new IllegalArgumentException("Patient not found.");
        }

        if (hashPassword(newPassword, patient.getSalt()).equals(patient.getPassword())) {
            throw new IllegalArgumentException("The new password cannot be the same as the old password.");
        }

        byte[] newSalt = generateSalt();
        String hashedPassword = hashPassword(newPassword, newSalt);
        patient.setSalt(newSalt);
        patient.setPassword(hashedPassword);
        patient.setIsFirstLogin(false);
        patientDao.updatePatient(patient);
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
