package controllers;

import models.Patient;
import models.MedicalRecord;

import daos.PatientDao;
import daos.MedicalRecordDao;

import java.util.List;

public class PatientController {
    private PatientDao patientDao;
    private MedicalRecordDao medicalRecordDao;

    public PatientController() {
        this.patientDao = new PatientDao();
        this.medicalRecordDao = new MedicalRecordDao();
    }

    // RETRIEVE ALL PATIENTS FROM DATABASE
    public List<Patient> getAllPatients() {
        return patientDao.getAllPatients();
    }

    // RETRIEVE SPECIFIC PATIENT FROM DATABASE USING HOSPITAL ID
    public Patient getPatientByHospitalId(String patientHospitalId) {
        return patientDao.getPatientByHospitalId(patientHospitalId);
    }

    // UPDATING PATIENT'S PASSWORD
    public boolean updatePatientPassword(String patientNewPassword, String patientConfirmPassword, String patientHospitalId) {
        if (!patientNewPassword.equals(patientConfirmPassword)) {
            System.out.println("Passwords do not match. Kindly try again.");
            return false;
        } else {
            Patient currentPatient = patientDao.getPatientByHospitalId(patientHospitalId);
            if (currentPatient.hashPassword(patientNewPassword, currentPatient.getSalt())
                    .equals(currentPatient.getPassword())) {
                System.out.println(
                        "New password must not be the same as old password. Kindly try again.");
                return false;
            } else {
                patientDao.updatePatientPasswordByHospitalId(patientNewPassword, patientHospitalId);
                return true;
            }
        }

    }

    // RETRIEVE SPECIFIC PATIENT'S MEDICAL RECORD FROM DATABASE
    public MedicalRecord viewMedicalRecord(String patientHospitalId) {
        return medicalRecordDao.getMedicalRecordByHospitalId(patientHospitalId);
    };

    // public void updatePersonalInformation();
    // public AppointmentSlot[] viewAppointmentSlot();
    // public void scheduleAppointment();
    // public void rescheduleAppointment();
    // public void cancelAppointment();
    // public Appointment[] viewScheduledAppointment();
    // public AppointmentOutcomeRecord[] viewPastAppointmentOutcomeRecord();
}