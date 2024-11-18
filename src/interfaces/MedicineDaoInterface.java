package src.interfaces;

import java.util.List;
import java.util.UUID;

import src.models.Medicine;

public interface MedicineDaoInterface {
    void createMedicine(String medicineName, int medicineQuantity, int medicineAlert);

    List<Medicine> getAllMedicine();

    Medicine getMedicineByMedicineId(UUID medicineId);

    void updateMedicine(Medicine medicine);

    void deleteMedicineByMedicineId(UUID medicineId);
}
