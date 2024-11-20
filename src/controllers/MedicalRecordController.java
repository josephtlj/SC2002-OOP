package src.controllers;

import src.models.MedicalRecord;
import src.interfaces.MedicalRecordServiceInterface;

public class MedicalRecordController {
    private final MedicalRecordServiceInterface medicalRecordService;

    public MedicalRecordController(MedicalRecordServiceInterface medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    
    /** 
     * @param hospitalId
     * @return MedicalRecord
     */
    public MedicalRecord handleViewMedicalRecord(String hospitalId){
        try {
            
            return medicalRecordService.readMedicalRecord(hospitalId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    
    /** 
     * @param hospitalId
     * @param newEmailAddress
     * @return boolean
     */
    public boolean handleUpdateEmailAddress(String hospitalId, String newEmailAddress) {
        try {
            medicalRecordService.updateEmailAddress(hospitalId, newEmailAddress);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    
    /** 
     * @param hospitalId
     * @param newPhoneNumber
     * @return boolean
     */
    public boolean handleUpdatePhoneNumber(String hospitalId, String newPhoneNumber) {
        try {
            medicalRecordService.updatePhoneNumber(hospitalId, newPhoneNumber);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
