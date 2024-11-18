package src.services;

import java.util.List;

import src.models.Medicine;

import src.interfaces.ReplenishmentRequestServiceInterface;
import src.interfaces.ReplenishmentRequestDaoInterface;

import src.interfaces.MedicineDaoInterface;

public class ReplenishmentRequestService implements ReplenishmentRequestServiceInterface {
    private final ReplenishmentRequestDaoInterface replenishmentRequestDao;
    private final MedicineDaoInterface medicineDao;

    public ReplenishmentRequestService(ReplenishmentRequestDaoInterface replenishmentRequestDao,
            MedicineDaoInterface medicineDao) {
        this.replenishmentRequestDao = replenishmentRequestDao;
        this.medicineDao = medicineDao;
    }

    @Override
    public void createReplenishmentRequest(String medicineName, int replenishmentQuantity) {

        List<Medicine> medicineList = medicineDao.getAllMedicine();

        if (medicineList == null || medicineList.isEmpty()) {
            throw new IllegalArgumentException("Medicine not found.");
        }

        Medicine medicineFound = null;

        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equals(medicineName)) {
                medicineFound = medicine;
                break;
            }
        }

        if (medicineFound == null) {
            throw new IllegalArgumentException("Medicine with name '" + medicineName + "' not found.");
        }

        replenishmentRequestDao.createReplenishmentRequest(replenishmentQuantity, medicineFound.getMedicineId());
    }
}
