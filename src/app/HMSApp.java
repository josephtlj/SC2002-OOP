package src.app;

import src.models.Doctor;
import src.models.Session;

import src.services.DoctorPasswordService;
import src.views.DoctorView;

import src.views.PharmacistView;
import src.views.AdministratorView;
import src.views.PatientView;
import src.views.UserView;

import src.controllers.PrescriptionController;
import src.controllers.ReplenishmentRequestController;
import src.controllers.MedicalRecordController;
import src.controllers.MedicineController;
import src.controllers.AdministratorController;
import src.controllers.AppointmentOutcomeRecordController;
import src.controllers.PharmacistController;
import src.controllers.PatientController;
import src.controllers.UserController;

import src.interfaces.PrescriptionServiceInterface;
import src.interfaces.ReplenishmentRequestDaoInterface;
import src.interfaces.ReplenishmentRequestServiceInterface;
import src.interfaces.MedicineServiceInterface;
import src.interfaces.MedicalRecordServiceInterface;
import src.interfaces.AdministratorServiceInterface;
import src.interfaces.AppointmentOutcomeRecordServiceInterface;
import src.interfaces.PharmacistServiceInterface;
import src.interfaces.PatientServiceInterface;
import src.interfaces.UserServiceInterface;

import src.services.PrescriptionService;
import src.services.ReplenishmentRequestService;
import src.services.MedicineService;
import src.services.MedicalRecordService;
import src.services.AdministratorService;
import src.services.AppointmentOutcomeRecordServices;
import src.services.PharmacistService;
import src.services.PatientService;
import src.services.UserService;

import src.interfaces.PrescriptionDaoInterface;
import src.interfaces.MedicineDaoInterface;
import src.interfaces.MedicalRecordDaoInterface;
import src.interfaces.AdministratorDaoInterface;
import src.interfaces.PharmacistDaoInterface;
import src.interfaces.PatientDaoInterface;
import src.interfaces.UserDaoInterface;

import src.daos.ReplenishmentRequestDao;
import src.daos.PrescriptionDao;
import src.daos.MedicineDao;
import src.daos.MedicalRecordDao;
import src.daos.AdministratorDao;
import src.daos.AppointmentOutcomeRecordDao;
import src.daos.DoctorDao;
import src.daos.PharmacistDao;
import src.daos.PatientDao;
import src.daos.UserDao;

public class HMSApp {
    public static void main(String[] args) {
        // INSTANTIATE DEPENDENCIES
        PrescriptionDaoInterface prescriptionDao = new PrescriptionDao();
        MedicineDaoInterface medicineDao = new MedicineDao();
        ReplenishmentRequestDaoInterface replenishmentRequestDao = new ReplenishmentRequestDao();
        MedicalRecordDaoInterface medicalRecordDao = new MedicalRecordDao();
        AdministratorDaoInterface administratorDao = new AdministratorDao();
        PharmacistDaoInterface pharmacistDao = new PharmacistDao();
        PatientDaoInterface patientDao = new PatientDao();
        UserDaoInterface userDao = new UserDao();

        DoctorDao doctorDao = new DoctorDao();
        PrescriptionServiceInterface prescriptionService = new PrescriptionService(prescriptionDao);
        MedicineServiceInterface medicineService = new MedicineService(medicineDao);
        ReplenishmentRequestServiceInterface replenishmentRequestService = new ReplenishmentRequestService(
                replenishmentRequestDao, medicineDao);
        MedicalRecordServiceInterface medicalRecordService = new MedicalRecordService(medicalRecordDao);
        AdministratorServiceInterface administratorService = new AdministratorService(administratorDao, pharmacistDao, doctorDao);
        PharmacistServiceInterface pharmacistService = new PharmacistService(pharmacistDao);
        PatientServiceInterface patientService = new PatientService(patientDao);
        UserServiceInterface userService = new UserService(userDao, patientService, pharmacistService,
                administratorService);

        PrescriptionController prescriptionController = new PrescriptionController(prescriptionService);
        MedicineController medicineController = new MedicineController(medicineService);
        ReplenishmentRequestController replenishmentRequestController = new ReplenishmentRequestController(
                replenishmentRequestService, medicineController);
        MedicalRecordController medicalRecordController = new MedicalRecordController(medicalRecordService);
        AdministratorController administratorController = new AdministratorController(administratorService,
                replenishmentRequestController, medicineController);
        PharmacistController pharmacistController = new PharmacistController(pharmacistService, medicineController,
                replenishmentRequestController);
        PatientController patientController = new PatientController(patientService, medicalRecordController);
        UserController userController = new UserController(userService);

        AdministratorView administratorView = new AdministratorView(administratorController);
        PharmacistView pharmacistView = new PharmacistView(pharmacistController);
        PatientView patientView = new PatientView(patientController);
        UserView userView = new UserView(userController);

        DoctorView doctorView = new DoctorView();

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
                    DoctorPasswordService doctorService = new DoctorPasswordService(
                            Session.getCurrentSession().getCurrentUser().getHospitalId());
                    Doctor doctor = doctorService
                            .readDoctorByHospitalId(Session.getCurrentSession().getCurrentUser().getHospitalId());
                    doctorView.displayDoctorLoginView(doctor);
                    break;
                case PHARMACIST:
                    pharmacistView.showMainMenu();
                    break;
                case ADMINISTRATOR:
                    administratorView.showMainMenu();
                    break;
            }
        }

        userView.showFarewellMessage();

    }
}
