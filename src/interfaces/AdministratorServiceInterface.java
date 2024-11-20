package src.interfaces;

import java.util.List;

import src.models.Administrator;
import src.models.Staff;

public interface AdministratorServiceInterface {
    void updatePassword(String hospitalId, String newPassword, String confirmPassword);

    Administrator readAdministratorByHospitalId(String hospitalId);

    List<Staff> readHospitalStaffByFilter(int filter);

}
