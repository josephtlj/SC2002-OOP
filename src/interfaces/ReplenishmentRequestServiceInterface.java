package src.interfaces;

import java.util.List;
import java.util.UUID;

import src.models.ReplenishmentRequest;

public interface ReplenishmentRequestServiceInterface {
    void createReplenishmentRequest(String medicineName, int medicineQuantity);

    List<ReplenishmentRequest> readAllReplenishmentRequestsByStatus(ReplenishmentRequest.Status status);

    void updateReplenishmentRequest(UUID requestId, ReplenishmentRequest.Status status);

    void deleteReplenishmentRequestsByMedicineId(UUID medicineId);
}
