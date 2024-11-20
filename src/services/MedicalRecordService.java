package src.services;

import src.interfaces.MedicalRecordServiceInterface;
import src.models.MedicalRecord;
import src.interfaces.MedicalRecordDaoInterface;

public class MedicalRecordService implements MedicalRecordServiceInterface {
    private final MedicalRecordDaoInterface medicalRecordDao;

    public MedicalRecordService(MedicalRecordDaoInterface medicalRecordDao) {
        this.medicalRecordDao = medicalRecordDao;
    }

    
    /** 
     * @param hospitalId
     * @return MedicalRecord
     */
    @Override
    public MedicalRecord readMedicalRecord(String hospitalId) {
        MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecordByHospitalId(hospitalId);
        if (medicalRecord == null) {
            throw new IllegalArgumentException("Medical Record not found.");
        }

        return medicalRecord;
    }

    
    /** 
     * @param hospitalId
     * @param newEmailAddress
     */
    @Override
    public void updateEmailAddress(String hospitalId, String newEmailAddress) {
        MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecordByHospitalId(hospitalId);

        if (medicalRecord == null) {
            throw new IllegalArgumentException("Medical Record not found.");
        }

        if (newEmailAddress.equals(medicalRecord.getEmailAddress())) {
            throw new IllegalArgumentException("The new email address cannot be the same as the old email address.");
        }

        medicalRecord.setEmailAddress(newEmailAddress);
        medicalRecordDao.updateMedicalRecord(medicalRecord);
    }

    @Override
    public void updatePhoneNumber(String hospitalId, String newPhoneNumber) {
        MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecordByHospitalId(hospitalId);

        if (medicalRecord == null) {
            throw new IllegalArgumentException("Medical Record not found.");
        }

        if (newPhoneNumber.equals(medicalRecord.getPhoneNumber())) {
            throw new IllegalArgumentException("The new phone number cannot be the same as the old phone number.");
        }

        medicalRecord.setPhoneNumber(newPhoneNumber);
        medicalRecordDao.updateMedicalRecord(medicalRecord);
    }
}
