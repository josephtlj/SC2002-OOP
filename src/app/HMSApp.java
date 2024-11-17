package src.app;

import src.models.Session;
import src.views.PatientView;
import src.views.UserView;
import src.controllers.PatientController;
import src.controllers.UserController;
import src.interfaces.UserServiceInterface;
import src.interfaces.PatientServiceInterface;
import src.interfaces.PatientDaoInterface;
import src.interfaces.UserDaoInterface;
import src.services.UserService;
import src.services.PatientService;
import src.daos.UserDao;
import src.daos.PatientDao;

public class HMSApp {
    public static void main(String[] args) {
        // INSTANTIATE DEPENDENCIES
        UserDaoInterface userDao = new UserDao();
        PatientDaoInterface patientDao = new PatientDao();
        UserServiceInterface userService = new UserService(userDao, patientDao);
        PatientServiceInterface patientService = new PatientService(patientDao);
        UserController userController = new UserController(userService);
        PatientController patientController = new PatientController(patientService);
        UserView userView = new UserView(userController);
        PatientView patientView = new PatientView(patientController);

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
                    patientView.showMainMenu();
                    break;

                default:
                    break;
            }
        }

        userView.showFarewellMessage();

    }
}
