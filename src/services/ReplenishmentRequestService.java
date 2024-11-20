package src.services;

import java.util.List;
import java.util.UUID;

import src.models.Medicine;
import src.models.ReplenishmentRequest;
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

    
    /** 
     * @param medicineName
     * @param replenishmentQuantity
     */
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

        replenishmentRequestDao.createReplenishmentRequest(medicineName, replenishmentQuantity,
                medicineFound.getMedicineId());
    }

    
    /** 
     * @param status
     * @return List<ReplenishmentRequest>
     */
    @Override
    public List<ReplenishmentRequest> readAllReplenishmentRequestsByStatus(ReplenishmentRequest.Status status) {
        try {
            return replenishmentRequestDao.getAllReplenishmentRequestsByStatus(status);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void updateReplenishmentRequest(UUID requestId, ReplenishmentRequest.Status status){
        ReplenishmentRequest replenishmentRequest = replenishmentRequestDao.getReplenishmentRequestByRequestId(requestId);

        if (replenishmentRequest == null){
            throw new IllegalArgumentException("Replenishment Request not found.");
        }

        replenishmentRequest.setStatus(status);
        
        replenishmentRequestDao.updateReplenishmentRequest(replenishmentRequest);
    }

    @Override
    public void deleteReplenishmentRequestsByMedicineId(UUID medicineId){
        replenishmentRequestDao.deleteReplenishmentRequestsByMedicineId(medicineId);
    }
}
