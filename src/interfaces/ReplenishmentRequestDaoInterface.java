package src.interfaces;

import java.util.UUID;

public interface ReplenishmentRequestDaoInterface {
    void createReplenishmentRequest(int replenishmentQuantity, UUID medicineId);
}
