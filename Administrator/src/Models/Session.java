package models;

// import HMS.src.models.User;

public class Session {
    // INSTANCE VARIABLES
    private User currentUser;

    // SINGLETON PATTERN
    private static Session currentSession;

    // CONSTRUCTOR
    private Session() {
    }

    public static Session getCurrentSession() {
        if (currentSession == null) {
            currentSession = new Session();
        }
        return currentSession;
    }

    // GETTERS AND SETTERS
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
    }
}