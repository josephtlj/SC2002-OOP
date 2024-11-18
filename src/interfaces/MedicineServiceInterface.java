package src.interfaces;

import java.util.List;
import java.util.UUID;

import src.models.Medicine;

public interface MedicineServiceInterface {
     List<Medicine> readAllMedication();

     void updateMedicine(int requestedQuantity, UUID medicineId);
}
