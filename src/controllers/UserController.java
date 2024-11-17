package src.controllers;

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
}
