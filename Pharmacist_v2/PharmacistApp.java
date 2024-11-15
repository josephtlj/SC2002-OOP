import java.time.LocalDate;

public class PharmacistApp {

    public static void main(String[] args) {
        // Sample data for AppointmentOutcomeRecordDAO
        String appointmentId = "A123";
        LocalDate date = LocalDate.now(); // You can set a specific date if needed
        String serviceType = "Consultation";

        // Initialize DAOs for Medicine, Prescription, ReplenishmentRequest, and AppointmentOutcomeRecord
        MedicineDAO medicineDAO = new MedicineDAO();
        PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
        ReplenishmentRequestDAO replenishmentDAO = new ReplenishmentRequestDAO();
        AppointmentOutcomeRecordDAO appointmentDAO = new AppointmentOutcomeRecordDAO(appointmentId, date, serviceType);

        // Initialize the Controller with DAOs
        PharmacistController controller = new PharmacistController(appointmentDAO, medicineDAO,replenishmentDAO, prescriptionDAO);

        // Initialize the View with Controller
        PharmacistView view = new PharmacistView(controller);

        // Display the menu to the pharmacist
        view.displayMenu();
    }
}
