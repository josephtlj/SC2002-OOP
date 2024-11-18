package src.interfaces;

import java.util.List;
import java.util.UUID;

import src.models.ReplenishmentRequest;

public interface ReplenishmentRequestDaoInterface {
    void createReplenishmentRequest(String medicineName, int replenishmentQuantity, UUID medicineId);

    ReplenishmentRequest getReplenishmentRequestByRequestId(UUID requestId);

    List<ReplenishmentRequest> getAllReplenishmentRequestsByStatus(ReplenishmentRequest.Status status);

    void updateReplenishmentRequest(ReplenishmentRequest replenishmentRequest);

    
}
