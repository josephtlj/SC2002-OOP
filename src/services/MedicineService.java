package src.services;

import src.interfaces.MedicineServiceInterface;
import src.interfaces.MedicineDaoInterface;

public class MedicineService implements MedicineServiceInterface {
    private final MedicineDaoInterface medicineDao;

    public MedicineService(MedicineDaoInterface medicineDao){
        this.medicineDao = medicineDao;
    }
}
