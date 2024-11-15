
public class PharmacistApp {

    public static void main(String[] args) {
        // Initialize service classes for each function
        AppointmentService appointmentService = new AppointmentService();
        PrescriptionService prescriptionService = new PrescriptionService();
        InventoryService inventoryService = new InventoryService();
        ReplenishmentService replenishmentService = new ReplenishmentService();

        // Initialize the View with the services
        PharmacistView view = new PharmacistView(appointmentService, prescriptionService, inventoryService, replenishmentService);

        // Display the menu to the pharmacist
        view.displayMenu();
    }
}
