package src.interfaces;

import src.models.Administrator;

public interface AdministratorDaoInterface {
    Administrator getAdministratorByHospitalId(String hospitalId);

    void updateAdministrator(Administrator administrator);
}
