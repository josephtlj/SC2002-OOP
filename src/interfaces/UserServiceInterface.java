package src.interfaces;

import src.models.User;

public interface UserServiceInterface {
    User login(String hospitalId, String password);

    void logout();

    boolean isFirstLogin(String hospitalId);

}
