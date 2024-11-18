package src.interfaces;

import src.models.Patient;

public interface PatientServiceInterface {

    Patient readPatientByHospitalId(String hospitalId);

    void updatePassword(String hospitalId, String newPassword, String confirmPassword);

}
