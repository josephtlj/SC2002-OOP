import controllers.AdministratorController;
import managers.StaffManager;
import managers.AdministratorAppointmentManager;
import managers.InventoryManager;
import Models.Staff;

public class AdministratorMain {
    public static void main(String[] args) {
        // Initialize Managers
        StaffManager staffManager = new StaffManager();
        InventoryManager inventoryManager = new InventoryManager();
        //AdministratorAppointmentManager appointmentManager = new AdministratorAppointmentManager(new AppointmentManager());

        // Initialize the AdministratorController with the managers
        AdministratorController adminController = new AdministratorController(staffManager, appointmentManager, inventoryManager);


        // Test: Display all staff
        adminController.displayAllStaff();

        // Test: Add inventory item
        adminController.addInventoryItem("Paracetamol", 100, 10);

        // Test: View inventory
        adminController.viewInventory();

        // Add other test commands as needed to simulate different features
    }
}