package src.interfaces;

import java.util.List;
import java.util.UUID;

import src.models.Medicine;

public interface MedicineServiceInterface {
     List<Medicine> readAllMedication();

     void createNewMedicine(String medicineName, int medicineQuantity, int medicineAlert);

     void updateMedicine(int medicineQuantity, int medicineAlert, UUID medicineId);

     void updateMedicineByReplenishmentRequest(int requestedQuantity, UUID medicineId);

     void deleteMedicineByMedicineId(UUID medicineId);
}
