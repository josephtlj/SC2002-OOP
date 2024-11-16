package src.app;

import src.views.UserView;
import src.controllers.UserController;
import src.interfaces.UserServiceInterface;
import src.models.Session;
import src.interfaces.UserDaoInterface;
import src.services.UserService;
import src.daos.UserDao;

public class HMSApp {
    public static void main(String[] args) {
        // INSTANTIATE DEPENDENCIES
        UserDaoInterface userDao = new UserDao();
        UserServiceInterface userService = new UserService(userDao);
        UserController userController = new UserController(userService);
        UserView userView = new UserView(userController);

        // MAIN PROGRAM
        boolean programStart = true;
        Session currentSession = Session.getCurrentSession();

        while (programStart) {
            userView.showWelcomeMessage();

            if (!currentSession.isLoggedIn()) {
                int initialChoice = userView.showInitialMenu();

                if (initialChoice == 2) {
                    programStart = false;
                    break;
                }
                userView.userLogin();
            }

            switch (currentSession.getCurrentUser().getRole()) {
                case PATIENT:
                    
                    break;
            
                default:
                    break;
            }
        }

        userView.showFarewellMessage();

    }
}
