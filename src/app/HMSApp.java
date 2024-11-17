package src.app;

import src.models.Session;

import src.views.PharmacistView;
import src.views.PatientView;
import src.views.UserView;

import src.controllers.MedicalRecordController;
import src.controllers.PharmacistController;
import src.controllers.PatientController;
import src.controllers.UserController;

import src.interfaces.MedicalRecordServiceInterface;
import src.interfaces.PharmacistServiceInterface;
import src.interfaces.PatientServiceInterface;
import src.interfaces.UserServiceInterface;

import src.interfaces.MedicalRecordDaoInterface;
import src.interfaces.PharmacistDaoInterface;
import src.interfaces.PatientDaoInterface;
import src.interfaces.UserDaoInterface;

import src.services.MedicalRecordService;
import src.services.PharmacistService;
import src.services.PatientService;
import src.services.UserService;

import src.daos.MedicalRecordDao;
import src.daos.PharmacistDao;
import src.daos.PatientDao;
import src.daos.UserDao;

public class HMSApp {
    public static void main(String[] args) {
        // INSTANTIATE DEPENDENCIES

        MedicalRecordDaoInterface medicalRecordDao = new MedicalRecordDao();
        PharmacistDaoInterface pharmacistDao = new PharmacistDao();
        PatientDaoInterface patientDao = new PatientDao();
        UserDaoInterface userDao = new UserDao();

        MedicalRecordServiceInterface medicalRecordService = new MedicalRecordService(medicalRecordDao);
        PharmacistServiceInterface pharmacistService = new PharmacistService(pharmacistDao);
        PatientServiceInterface patientService = new PatientService(patientDao);
        UserServiceInterface userService = new UserService(userDao, patientDao, pharmacistDao);

        
        MedicalRecordController medicalRecordController = new MedicalRecordController(medicalRecordService);
        PharmacistController pharmacistController = new PharmacistController(pharmacistService);
        PatientController patientController = new PatientController(patientService, medicalRecordController);
        UserController userController = new UserController(userService);

        PharmacistView pharmacistView = new PharmacistView(pharmacistController);
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
                case DOCTOR:
                    break;
                case PHARMACIST:
                    pharmacistView.showMainMenu();
                    break;
                case ADMINISTRATOR:
                    break;
                default:
                    break;
            }
        }

        userView.showFarewellMessage();

    }
}
