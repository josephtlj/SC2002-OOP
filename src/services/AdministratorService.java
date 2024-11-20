package src.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import src.models.Administrator;
import src.models.Staff;

import src.interfaces.AdministratorServiceInterface;
import src.interfaces.DoctorDaoInterface;
import src.interfaces.PharmacistDaoInterface;
import src.interfaces.AdministratorDaoInterface;

public class AdministratorService implements AdministratorServiceInterface {
    private final AdministratorDaoInterface administratorDao;
    private final PharmacistDaoInterface pharmacistDao;
    private final DoctorDaoInterface doctorDao;

    public AdministratorService(AdministratorDaoInterface administratorDao, PharmacistDaoInterface pharmacistDao,
            DoctorDaoInterface doctorDao) {
        this.administratorDao = administratorDao;
        this.pharmacistDao = pharmacistDao;
        this.doctorDao = doctorDao;
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
        administrator.setIsFirstLogin(false);
        administratorDao.updateAdministrator(administrator);
    }

    
    /** 
     * @param filter
     * @return List<Staff>
     */
    @Override
    public List<Staff> readHospitalStaffByFilter(int filter) {
        List<Staff> allStaff = new ArrayList<>();

        List<Staff> administratorList = administratorDao.getAllStaffAdministrators();
        List<Staff> pharmacistList = pharmacistDao.getAllStaffPharmacists();
        List<Staff> doctorList = doctorDao.getAllStaffDoctors();

        allStaff.addAll(administratorList);
        allStaff.addAll(pharmacistList);
        allStaff.addAll(doctorList);

        switch (filter) {
            case 1:
                allStaff.sort(Comparator.comparing(Staff::getName));
                break;
            case 2:
                allStaff.sort(Comparator.comparing(Staff::getRole));
                break;
            case 3:
                allStaff.sort(Comparator.comparing(Staff::getGender));
                break;
            case 4:
                allStaff.sort(Comparator.comparing(Staff::getAge));
                break;

            default:
                break;
        }

        return allStaff;
    }

    
    /** 
     * @param hospitalId
     * @return Administrator
     */
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
