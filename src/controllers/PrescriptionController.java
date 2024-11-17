package src.controllers;

import src.interfaces.PrescriptionServiceInterface;

public class PrescriptionController {
    private final PrescriptionServiceInterface prescriptionService;

    public PrescriptionController(PrescriptionServiceInterface prescriptionService){
        this.prescriptionService = prescriptionService;
    }
}
