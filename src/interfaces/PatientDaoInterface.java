package src.interfaces;

import src.models.Patient;

public interface PatientDaoInterface {

    Patient getPatientByHospitalId(String hospitalId);

    void updatePatient(Patient patient);

    void deletePatient(String hospitalId);

    void createPatient(Patient patient);
}
