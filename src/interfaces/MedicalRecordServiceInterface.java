package src.interfaces;

import src.models.MedicalRecord;

public interface MedicalRecordServiceInterface {
    MedicalRecord readMedicalRecord(String hospitalId);

    void updateEmailAddress(String hospitalId, String newEmailAddress);

    void updatePhoneNumber(String hospitalId, String newPhoneNumber);
}
