package src.controllers;

import java.util.List;
import java.util.UUID;

import src.models.ReplenishmentRequest;

import src.interfaces.AdministratorServiceInterface;

public class AdministratorController {
    private final AdministratorServiceInterface administratorService;
    private final ReplenishmentRequestController replenishmentRequestController;

    public AdministratorController(AdministratorServiceInterface administratorService,
            ReplenishmentRequestController replenishmentRequestController) {
        this.administratorService = administratorService;
        this.replenishmentRequestController = replenishmentRequestController;

    }

    public boolean handleUpdatePassword(String hospitalId, String newPassword, String confirmPassword) {
        try {
            administratorService.updatePassword(hospitalId, newPassword, confirmPassword);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
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
    public void handleReplenishmentRequest(UUID requestId, int requestedQuantity, ReplenishmentRequest.Status status, UUID medicineId) {
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
