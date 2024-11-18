package src.interfaces;

import java.util.List;

import src.models.Prescription;

public interface PrescriptionServiceInterface {
    List<Prescription> readAllPrescription();
}
