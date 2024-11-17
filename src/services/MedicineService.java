package src.services;

import src.interfaces.MedicineServiceInterface;
import src.models.Medicine;

import java.util.List;

import src.interfaces.MedicineDaoInterface;

public class MedicineService implements MedicineServiceInterface {
    private final MedicineDaoInterface medicineDao;

    public MedicineService(MedicineDaoInterface medicineDao){
        this.medicineDao = medicineDao;
    }

    @Override
    public List<Medicine> readAllMedication(){
        try{
            return medicineDao.getAllMedicine();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
