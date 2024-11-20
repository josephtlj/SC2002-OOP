package src.services;

import java.util.List;

import src.models.Prescription;

import src.interfaces.PrescriptionDaoInterface;
import src.interfaces.PrescriptionServiceInterface;

public class PrescriptionService implements PrescriptionServiceInterface {
    private final PrescriptionDaoInterface prescriptionDao;

    public PrescriptionService(PrescriptionDaoInterface prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    
    /** 
     * @return List<Prescription>
     */
    @Override
    public List<Prescription> readAllPrescription() {
        try {
            return prescriptionDao.getAllPrescriptions();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
