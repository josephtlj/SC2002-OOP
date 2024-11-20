package src.controllers;

import java.util.List;
import java.util.UUID;

import src.models.ReplenishmentRequest;
import src.models.Staff;
import src.models.Medicine;

import src.interfaces.AdministratorServiceInterface;

public class AdministratorController {
    private final AdministratorServiceInterface administratorService;
    private final ReplenishmentRequestController replenishmentRequestController;
    private final MedicineController medicineController;

    public AdministratorController(AdministratorServiceInterface administratorService,
            ReplenishmentRequestController replenishmentRequestController, MedicineController medicineController) {
        this.administratorService = administratorService;
        this.replenishmentRequestController = replenishmentRequestController;
        this.medicineController = medicineController;
    }

    
    /** 
     * @param hospitalId
     * @param newPassword
     * @param confirmPassword
     * @return boolean
     */
    public boolean handleUpdatePassword(String hospitalId, String newPassword, String confirmPassword) {
        try {
            administratorService.updatePassword(hospitalId, newPassword, confirmPassword);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    
    /** 
     * @param filter
     * @return List<Staff>
     */
    public List<Staff> handleViewHospitalStaff(int filter){
        try {
            return administratorService.readHospitalStaffByFilter(filter);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    
    /** 
     * @return List<Medicine>
     */
    public List<Medicine> handleViewMedicationInventory() {
        try {
            return medicineController.handleViewMedicationInventory();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean handleAddNewMedication(String medicineName, int medicineQuantity, int medicineAlert) {
        try {

            if (medicineName == null || medicineName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid input: Medicine name cannot be empty.");
            }
            if (medicineQuantity <= 0) {
                throw new IllegalArgumentException("Invalid input: Medicine quantity must be a positive number.");
            }
            if (medicineAlert <= 0) {
                throw new IllegalArgumentException("Invalid input: Medicine alert quantity must be a positive number.");
            }

            medicineController.handleAddNewMedication(medicineName, medicineQuantity, medicineAlert);
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;

        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean handleUpdateMedication(int medicineQuantity, int medicineAlert,
            UUID medicineId) {
        try {

            if (medicineQuantity <= 0) {
                throw new IllegalArgumentException("Invalid input: Medicine quantity must be a positive number.");
            }
            if (medicineAlert <= 0) {
                throw new IllegalArgumentException("Invalid input: Medicine alert quantity must be a positive number.");
            }

            medicineController.handleUpdateMedication(medicineQuantity, medicineAlert, medicineId);
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;

        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
            e.printStackTrace();
            return false;
        }
    }

    public void handleDeleteMedication(UUID medicineId) {
        try {
            medicineController.handleDeleteMedication(medicineId);
            replenishmentRequestController.handleDeleteReplenishmentRequestsByMedicineId(medicineId);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
            e.printStackTrace();
        }
    }

    public List<ReplenishmentRequest> handleGetReplenishmentRequestsByStatus(ReplenishmentRequest.Status status) {
        try {

            return replenishmentRequestController.handleGetReplenishmentRequestsByStatus(status);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // APPROVED REPLENISHMENT REQUEST
    public void handleReplenishmentRequest(UUID requestId, int requestedQuantity, ReplenishmentRequest.Status status,
            UUID medicineId) {
        try {
            replenishmentRequestController.handleReplenishmentRequest(requestId, requestedQuantity, status, medicineId);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // REJECTED REPLENISHMENT REQUEST
    public void handleReplenishmentRequest(UUID requestId, ReplenishmentRequest.Status status) {
        try {
            replenishmentRequestController.handleReplenishmentRequest(requestId, status);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
