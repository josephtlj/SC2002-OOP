package src.controllers;

import src.interfaces.ReplenishmentRequestServiceInterface;

public class ReplenishmentRequestController {
    private final ReplenishmentRequestServiceInterface replenishmentRequestService;

    public ReplenishmentRequestController(ReplenishmentRequestServiceInterface replenishmentRequestService) {
        this.replenishmentRequestService = replenishmentRequestService;
    }

    public boolean handleSubmitReplenishmentRequest(String medicineName, int replenishmentQuantity){
        try {
            replenishmentRequestService.createReplenishmentRequest(medicineName, replenishmentQuantity);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
