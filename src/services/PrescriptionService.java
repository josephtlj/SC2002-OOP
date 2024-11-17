package src.services;

import src.interfaces.PrescriptionDaoInterface;
import src.interfaces.PrescriptionServiceInterface;

public class PrescriptionService implements PrescriptionServiceInterface {
    private final PrescriptionDaoInterface prescriptionDao;

    public PrescriptionService(PrescriptionDaoInterface prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }
}
