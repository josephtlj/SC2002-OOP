package src.controllers;

import src.models.Session;
import src.models.User;
import src.interfaces.UserServiceInterface;

public class UserController {
    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    public boolean handleLogin(String hospitalId, String password) {
        try {
            userService.login(hospitalId, password);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void handleLogout() {
        userService.logout();
        System.out.println("Logged out successfully!");
    }

    // public void showCurrentUser() {
    // if (Session.getCurrentSession().isLoggedIn()) {
    // User currentUser = Session.getCurrentSession().getCurrentUser();
    // System.out.println("Current User: " + currentUser.getHospitalId());
    // } else {
    // System.out.println("No user is logged in.");
    // }
    // }
}
