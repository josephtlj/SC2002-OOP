package models;

import Doctor.User;

// import HMS.src.models.User;

public class Session {
    // INSTANCE VARIABLES
    private static User currentUser;

    // INSTANCE METHODS
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }
}