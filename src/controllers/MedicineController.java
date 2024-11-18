package src.controllers;

import java.util.List;
import java.util.UUID;

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

    public void handleAddNewMedication(String medicineName, int medicineQuantity, int medicineAlert) {
        try {
            medicineService.createNewMedicine(medicineName, medicineQuantity, medicineAlert);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleUpdateMedication(int medicineQuantity, int medicineAlert, UUID medicineId) {
        try {
            medicineService.updateMedicine(medicineQuantity, medicineAlert, medicineId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleDeleteMedication(UUID medicineId) {
        try {
            medicineService.deleteMedicineByMedicineId(medicineId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleUpdateMedicineByReplenishmentRequest(int requestedQuantity, UUID medicineId) {
        try {
            medicineService.updateMedicineByReplenishmentRequest(requestedQuantity, medicineId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
