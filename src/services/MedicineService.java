package src.services;

import java.util.List;
import java.util.UUID;

import src.models.Medicine;

import src.interfaces.MedicineServiceInterface;

import src.interfaces.MedicineDaoInterface;

public class MedicineService implements MedicineServiceInterface {
    private final MedicineDaoInterface medicineDao;

    public MedicineService(MedicineDaoInterface medicineDao) {
        this.medicineDao = medicineDao;
    }

    @Override
    public List<Medicine> readAllMedication() {
        try {
            return medicineDao.getAllMedicine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void createNewMedicine(String medicineName, int medicineQuantity, int medicineAlert) {
        List<Medicine> medicineList = medicineDao.getAllMedicine();

        if (medicineList == null || medicineList.isEmpty()) {
            throw new IllegalArgumentException("Medicine not found.");
        }

        boolean medicineFound = false;

        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineName().equals(medicineName)) {
                medicineFound = true;
                break;
            }
        }

        if (medicineFound) {
            throw new IllegalArgumentException("Medicine with name '" + medicineName + "' already exists.");
        }

        medicineDao.createMedicine(medicineName, medicineQuantity, medicineAlert);
    }

    @Override
    public void updateMedicine(int medicineQuantity, int medicineAlert, UUID medicineId) {
        try {
            Medicine medicine = medicineDao.getMedicineByMedicineId(medicineId);

            if (medicine == null) {
                throw new IllegalArgumentException("Medicine not found.");
            }

            medicine.setMedicineQuantity(medicineQuantity);
            medicine.setMedicineAlert(medicineAlert);

            medicineDao.updateMedicine(medicine);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void updateMedicineByReplenishmentRequest(int requestedQuantity, UUID medicineId) {
        try {
            Medicine medicine = medicineDao.getMedicineByMedicineId(medicineId);

            if (medicine == null) {
                throw new IllegalArgumentException("Medicine not found.");
            }

            int newQuantity = medicine.getMedicineQuantity() + requestedQuantity;

            medicine.setMedicineQuantity(newQuantity);

            medicineDao.updateMedicine(medicine);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    public void deleteMedicineByMedicineId(UUID medicineId) {
        try {
            medicineDao.deleteMedicineByMedicineId(medicineId);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}
