package src.interfaces;

import src.models.Pharmacist;

public interface PharmacistDaoInterface {
    Pharmacist getPharmacistByHospitalId(String hospitalId);

    void updatePharmacist(Pharmacist pharmacist);
}
