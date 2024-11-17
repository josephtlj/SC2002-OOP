package src.controllers;

import java.util.List;

import src.models.Medicine;
import src.interfaces.MedicineServiceInterface;
import src.interfaces.PharmacistServiceInterface;

public class PharmacistController {
    private final PharmacistServiceInterface pharmacistService;
    private final MedicineServiceInterface medicineService;

    public PharmacistController(PharmacistServiceInterface pharmacistService,
            MedicineServiceInterface medicineService) {
        this.pharmacistService = pharmacistService;
        this.medicineService = medicineService;
    }

    public boolean handleUpdatePassword(String hospitalId, String newPassword, String confirmPassword) {
        try {
            pharmacistService.updatePassword(hospitalId, newPassword, confirmPassword);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
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
