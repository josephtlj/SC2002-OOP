package src.interfaces;

import java.util.List;

import src.models.Pharmacist;
import src.models.Staff;

public interface PharmacistDaoInterface {
    Pharmacist getPharmacistByHospitalId(String hospitalId);

    List<Staff> getAllStaffPharmacists();

    void updatePharmacist(Pharmacist pharmacist);
}
