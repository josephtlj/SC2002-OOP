package src.interfaces;

import src.models.User;

public interface UserServiceInterface {
    User login(String hospitalId, String password);

    void updatePassword(String hospitalId, String newPassword);

    // void resetPassword(String hospitalId);

    boolean isFirstLogin(String hospitalId);

    void logout();
}
