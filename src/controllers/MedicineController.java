package src.controllers;

import src.interfaces.MedicineServiceInterface;

public class MedicineController {
    private final MedicineServiceInterface medicineService;

    public MedicineController(MedicineServiceInterface medicineService) {
        this.medicineService = medicineService;
    }
}
