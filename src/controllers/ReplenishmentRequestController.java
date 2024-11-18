package src.controllers;

import java.util.List;
import java.util.UUID;

import src.interfaces.ReplenishmentRequestServiceInterface;
import src.models.ReplenishmentRequest;

public class ReplenishmentRequestController {
    private final ReplenishmentRequestServiceInterface replenishmentRequestService;
    private final MedicineController medicineController;

    public ReplenishmentRequestController(ReplenishmentRequestServiceInterface replenishmentRequestService,
    MedicineController medicineController) {
        this.replenishmentRequestService = replenishmentRequestService;
        this.medicineController = medicineController;
    }

    public boolean handleSubmitReplenishmentRequest(String medicineName, int replenishmentQuantity) {
        try {
            replenishmentRequestService.createReplenishmentRequest(medicineName, replenishmentQuantity);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<ReplenishmentRequest> handleGetReplenishmentRequestsByStatus(ReplenishmentRequest.Status status) {
        try {
            return replenishmentRequestService.readAllReplenishmentRequestsByStatus(status);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // ACCEPTED REPLENISHMENT REQUEST
    public void handleReplenishmentRequest(UUID requestId, int requestedQuantity, ReplenishmentRequest.Status status, UUID medicineId){
        try {
            replenishmentRequestService.updateReplenishmentRequest(requestId, status);
            medicineController.handleUpdateMedicineByReplenishmentRequest(requestedQuantity, medicineId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            
        }
    };

    // REJECTED REPLENISHMENT REQUEST
    public void handleReplenishmentRequest(UUID requestId, ReplenishmentRequest.Status status) {
        try {
            replenishmentRequestService.updateReplenishmentRequest(requestId, status);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
    };

    public void handleDeleteReplenishmentRequestsByMedicineId(UUID medicineId){
        try {
            replenishmentRequestService.deleteReplenishmentRequestsByMedicineId(medicineId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
    }
}
