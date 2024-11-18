package src.controllers;

import java.util.List;

import src.models.Prescription;

import src.interfaces.PrescriptionServiceInterface;


public class PrescriptionController {
    private final PrescriptionServiceInterface prescriptionService;

    public PrescriptionController(PrescriptionServiceInterface prescriptionService){
        this.prescriptionService = prescriptionService;
    }

    // public List<Prescription> handleSubmitReplenishmentRequest(){
    //     try {
    //         prescriptionService.updatePassword(hospitalId, newPassword, confirmPassword);
    //         return true;
    //     } catch (IllegalArgumentException e) {
    //         System.out.println(e.getMessage());
    //         return false;
    //     }
    // }
}
