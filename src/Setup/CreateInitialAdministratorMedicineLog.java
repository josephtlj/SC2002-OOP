package Setup;


import java.util.ArrayList;
import java.util.List;
import Models.AdministratorMedicineLog; // Add this import at the top of the CreateInitialAdministratorMedicineLog class


public class CreateInitialAdministratorMedicineLog {

    // List to hold the AdministratorMedicineLog entries
    private List<AdministratorMedicineLog> medicineLogList;

    // Constructor to initialize the log list
    public CreateInitialAdministratorMedicineLog() {
        this.medicineLogList = new ArrayList<>();
    }

    // Method to create and add initial medicine log entries
    public void createInitialLogs() {
        // Create some initial log entries
        AdministratorMedicineLog log1 = new AdministratorMedicineLog("Admin approved the new stock of Paracetamol.");
        AdministratorMedicineLog log2 = new AdministratorMedicineLog("Admin reviewed the low stock of Ibuprofen.");
        AdministratorMedicineLog log3 = new AdministratorMedicineLog("Admin updated the expiry date for Aspirin.");

        // Add them to the list
        medicineLogList.add(log1);
        medicineLogList.add(log2);
        medicineLogList.add(log3);

        System.out.println("Initial Administrator Medicine Logs created.");
    }

    // Method to retrieve the list of logs (optional, if needed elsewhere)
    public List<AdministratorMedicineLog> getMedicineLogList() {
        return medicineLogList;
    }

    // Optional: Method to print all the logs for verification
    public void printAllLogs() {
        for (AdministratorMedicineLog log : medicineLogList) {
            System.out.println(log);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        CreateInitialAdministratorMedicineLog logCreator = new CreateInitialAdministratorMedicineLog();
        logCreator.createInitialLogs();
        logCreator.printAllLogs(); // This will print all the logs created
    }
}
