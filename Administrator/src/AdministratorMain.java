package Administrator.src;
//import controllers.AdministratorController;

/*public class AdministratorMain {
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
}*/
import controllers.AdministratorMenu;
import managers.StaffManager;
import managers.AdministratorAppointmentManager;
import managers.InventoryManager;
import managers.ReplenishmentRequestManager;

public class AdministratorMain {
    public static void main(String[] args) {
        // Initialize Managers
        StaffManager staffManager = new StaffManager();
        InventoryManager inventoryManager = new InventoryManager();
        AdministratorAppointmentManager appointmentManager = new AdministratorAppointmentManager();
        ReplenishmentRequestManager replenishmentRequestManager = new ReplenishmentRequestManager();

        // Initialize the AdministratorMenu with the managers
        AdministratorMenu adminMenu = new AdministratorMenu(appointmentManager, inventoryManager, replenishmentRequestManager, staffManager);

        // Display the menu
        adminMenu.displayMenu();
    }
}
