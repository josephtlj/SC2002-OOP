package src.controllers;

import java.util.List;

import src.interfaces.MedicineServiceInterface;
import src.models.Medicine;

public class MedicineController {
    private final MedicineServiceInterface medicineService;

    public MedicineController(MedicineServiceInterface medicineService) {
        this.medicineService = medicineService;
    }

    public List<Medicine> handleViewMedicationInventory() {
        try {
            return medicineService.readAllMedication();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
