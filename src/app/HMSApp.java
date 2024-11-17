package src.app;

import src.models.Session;
import src.views.PatientView;
import src.views.UserView;
import src.controllers.MedicalRecordController;
import src.controllers.PatientController;
import src.controllers.UserController;
import src.interfaces.UserServiceInterface;
import src.interfaces.PatientServiceInterface;
import src.interfaces.MedicalRecordDaoInterface;
import src.interfaces.MedicalRecordServiceInterface;
import src.interfaces.PatientDaoInterface;
import src.interfaces.UserDaoInterface;
import src.services.UserService;
import src.services.MedicalRecordService;
import src.services.PatientService;
import src.daos.UserDao;
import src.daos.MedicalRecordDao;
import src.daos.PatientDao;

public class HMSApp {
    public static void main(String[] args) {
        // INSTANTIATE DEPENDENCIES

        MedicalRecordDaoInterface medicalRecordDao = new MedicalRecordDao();
        PatientDaoInterface patientDao = new PatientDao();
        UserDaoInterface userDao = new UserDao();

        MedicalRecordServiceInterface medicalRecordService = new MedicalRecordService(medicalRecordDao);
        PatientServiceInterface patientService = new PatientService(patientDao);
        UserServiceInterface userService = new UserService(userDao, patientDao);

        MedicalRecordController medicalRecordController = new MedicalRecordController(medicalRecordService);
        PatientController patientController = new PatientController(patientService, medicalRecordController);
        UserController userController = new UserController(userService);

        PatientView patientView = new PatientView(patientController);
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
                userView.showUserLogin();
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
