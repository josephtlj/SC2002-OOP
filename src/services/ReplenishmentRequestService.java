package src.services;

import src.interfaces.ReplenishmentRequestDaoInterface;
import src.interfaces.ReplenishmentRequestServiceInterface;

public class ReplenishmentRequestService implements ReplenishmentRequestServiceInterface {
    private final ReplenishmentRequestDaoInterface replenishmentRequestDao;

    public ReplenishmentRequestService(ReplenishmentRequestDaoInterface replenishmentRequestDao) {
        this.replenishmentRequestDao = replenishmentRequestDao;
    }

}
