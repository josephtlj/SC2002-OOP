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
    public void updateMedicine(int requestedQuantity, UUID medicineId) {
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
}
