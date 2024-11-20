package src.controllers;

import src.models.MedicalRecord;
import src.interfaces.PatientServiceInterface;

public class PatientController {
    private final PatientServiceInterface patientService;
    private final MedicalRecordController medicalRecordController;

    public PatientController(PatientServiceInterface patientService, MedicalRecordController medicalRecordController) {
        this.patientService = patientService;
        this.medicalRecordController = medicalRecordController;
    }

    
    /** 
     * @param hospitalId
     * @return MedicalRecord
     */
    public MedicalRecord handleViewMedicalRecord(String hospitalId) {
        try {
            return medicalRecordController.handleViewMedicalRecord(hospitalId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    
    /** 
     * @param hospitalId
     * @param newPassword
     * @param confirmPassword
     * @return boolean
     */
    public boolean handleUpdatePassword(String hospitalId, String newPassword, String confirmPassword) {
        try {
            patientService.updatePassword(hospitalId, newPassword, confirmPassword);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean handleUpdateEmailAddress(String hospitalId, String newEmailAddress) {
        try {
            medicalRecordController.handleUpdateEmailAddress(hospitalId, newEmailAddress);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean handleUpdatePhoneNumber(String hospitalId, String newPhoneNumber) {
        try {
            medicalRecordController.handleUpdatePhoneNumber(hospitalId, newPhoneNumber);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
