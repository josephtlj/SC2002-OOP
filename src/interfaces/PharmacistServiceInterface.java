package src.interfaces;

import src.models.Pharmacist;

public interface PharmacistServiceInterface {
    Pharmacist readPharmacistByHospitalId(String hospitalId);

    void updatePassword(String hospitalId, String newPassword, String confirmPassword);
}
