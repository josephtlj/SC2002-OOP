package src.app;

import src.models.Session;

import src.views.PharmacistView;
import src.views.PatientView;
import src.views.UserView;

import src.controllers.PrescriptionController;
import src.controllers.ReplenishmentRequestController;
import src.controllers.MedicalRecordController;
import src.controllers.MedicineController;
import src.controllers.PharmacistController;
import src.controllers.PatientController;
import src.controllers.UserController;

import src.interfaces.PrescriptionServiceInterface;
import src.interfaces.ReplenishmentRequestDaoInterface;
import src.interfaces.ReplenishmentRequestServiceInterface;
import src.interfaces.MedicineServiceInterface;
import src.interfaces.MedicalRecordServiceInterface;
import src.interfaces.PharmacistServiceInterface;
import src.interfaces.PatientServiceInterface;
import src.interfaces.UserServiceInterface;

import src.services.PrescriptionService;
import src.services.ReplenishmentRequestService;
import src.services.MedicineService;
import src.services.MedicalRecordService;
import src.services.PharmacistService;
import src.services.PatientService;
import src.services.UserService;

import src.interfaces.PrescriptionDaoInterface;
import src.interfaces.MedicineDaoInterface;
import src.interfaces.MedicalRecordDaoInterface;
import src.interfaces.PharmacistDaoInterface;
import src.interfaces.PatientDaoInterface;
import src.interfaces.UserDaoInterface;

import src.daos.ReplenishmentRequestDao;
import src.daos.PrescriptionDao;
import src.daos.MedicineDao;
import src.daos.MedicalRecordDao;
import src.daos.PharmacistDao;
import src.daos.PatientDao;
import src.daos.UserDao;

public class HMSApp {
    public static void main(String[] args) {
        // INSTANTIATE DEPENDENCIES
        ReplenishmentRequestDaoInterface replenishmentRequestDao = new ReplenishmentRequestDao();
        PrescriptionDaoInterface prescriptionDao = new PrescriptionDao();
        MedicineDaoInterface medicineDao = new MedicineDao();
        MedicalRecordDaoInterface medicalRecordDao = new MedicalRecordDao();
        PharmacistDaoInterface pharmacistDao = new PharmacistDao();
        PatientDaoInterface patientDao = new PatientDao();
        UserDaoInterface userDao = new UserDao();

        ReplenishmentRequestServiceInterface replenishmentRequestService = new ReplenishmentRequestService(replenishmentRequestDao, medicineDao);
        PrescriptionServiceInterface prescriptionService = new PrescriptionService(prescriptionDao);
        MedicineServiceInterface medicineService = new MedicineService(medicineDao);
        MedicalRecordServiceInterface medicalRecordService = new MedicalRecordService(medicalRecordDao);
        PharmacistServiceInterface pharmacistService = new PharmacistService(pharmacistDao);
        PatientServiceInterface patientService = new PatientService(patientDao);
        UserServiceInterface userService = new UserService(userDao, patientDao, pharmacistDao);

        ReplenishmentRequestController replenishmentRequestController = new ReplenishmentRequestController(replenishmentRequestService);
        PrescriptionController prescriptionController = new PrescriptionController(prescriptionService);
        MedicineController medicineController = new MedicineController(medicineService);
        MedicalRecordController medicalRecordController = new MedicalRecordController(medicalRecordService);
        PharmacistController pharmacistController = new PharmacistController(pharmacistService, medicineController, replenishmentRequestController);
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
