package src.interfaces;

import src.models.Administrator;

public interface AdministratorServiceInterface {
    void updatePassword(String hospitalId, String newPassword, String confirmPassword);

    Administrator readAdministratorByHospitalId(String hospitalId);

}
