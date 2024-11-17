package src.controllers;

import src.interfaces.PatientServiceInterface;

public class PatientController {
    private final PatientServiceInterface patientService;

    public PatientController(PatientServiceInterface patientService) {
        this.patientService = patientService;
    }

    public boolean handleUpdatePassword(String hospitalId, String newPassword, String confirmPassword) {
        try {
            patientService.updatePassword(hospitalId, newPassword, confirmPassword);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
