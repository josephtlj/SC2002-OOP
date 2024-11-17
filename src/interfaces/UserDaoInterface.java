package src.interfaces;

import src.models.User;

public interface UserDaoInterface {
    User getUserByHospitalId(String hospitalId);

    void updateUser(User user);

    void deleteUser(String hospitalId);

    void createUser(User user);
}
