package src.interfaces;

import java.util.List;

import src.models.Administrator;
import src.models.Staff;

public interface AdministratorDaoInterface {
    Administrator getAdministratorByHospitalId(String hospitalId);

    List<Staff> getAllStaffAdministrators();

    void updateAdministrator(Administrator administrator);
}
