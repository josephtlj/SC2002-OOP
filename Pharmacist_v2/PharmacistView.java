import java.util.Scanner;

public class PharmacistView {
    private AppointmentService appointmentService;
    private PrescriptionService prescriptionService;
    private InventoryService inventoryService;
    private ReplenishmentService replenishmentService;

    public PharmacistView(AppointmentService appointmentService, PrescriptionService prescriptionService, InventoryService inventoryService, ReplenishmentService replenishmentService) {
        this.appointmentService = appointmentService;
        this.prescriptionService = prescriptionService;
        this.inventoryService = inventoryService;
        this.replenishmentService = replenishmentService;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("""
                
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                   Welcome Pharmacist                      |
                        =============================================================
                        Please select an option:
                        (1) View Appointment Outcome Record
                        (2) Update Prescription Status
                        (3) View Medication Inventory
                        (4) Submit Replenishment Request
                        (5) Logout
                                
                         """);
        
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 :
                           {
                            appointmentService.viewAllAppointmentOutcomes();
                           break;
                           }
                case 2 : 
                           {
                            prescriptionService.updatePrescriptionStatus();
                            break;
                           }
                case 3 : 
                            {
                             inventoryService.viewInventory();
                             break;
                            }    
                case 4 : 
                            {
                            replenishmentService.submitReplenishmentRequest();
                            break;
                            }
                case 5 : 
                            {
                            System.out.println("Exiting...");
                            break;
                            }
                default : System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
