package controllers;

import java.util.Scanner;
import java.util.UUID;
import managers.AdministratorAppointmentManager;
import managers.StaffManager;
import managers.InventoryManager;
import managers.ReplenishmentRequestManager;

public class AdministratorMenu {
    private AdministratorAppointmentManager appointmentManager;
    private InventoryManager inventoryManager;
    private ReplenishmentRequestManager replenishmentRequestManager;
    private StaffManager staffManager;

    public AdministratorMenu(AdministratorAppointmentManager appointmentManager, 
                             InventoryManager inventoryManager, 
                             ReplenishmentRequestManager replenishmentRequestManager, 
                             StaffManager staffManager) {
        this.appointmentManager = appointmentManager;
        this.inventoryManager = inventoryManager;
        this.replenishmentRequestManager = replenishmentRequestManager;
        this.staffManager = staffManager;
    }

    // Display main menu and handle user selection
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View all appointments");
            System.out.println("2. View appointments by status");
            System.out.println("4. View inventory");
            System.out.println("5. Add inventory item");
            System.out.println("6. Update inventory item");
            System.out.println("7. Remove inventory item");
            System.out.println("8. View low stock alert level items");
            System.out.println("9. Approve replenishment request");
            System.out.println("10. Reject replenishment request");
            System.out.println("11. View staff members");
            System.out.println("12. Filter staff members");
            System.out.println("13. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    appointmentManager.displayAllAppointments();
                    break;
                case 2:
                    System.out.print("Enter status (e.g., 'Pending', 'Confirmed'): ");
                    String status = scanner.nextLine();
                    appointmentManager.displayAppointmentsByStatus(status);
                    break;
                case 4:
                    inventoryManager.viewInventory();
                    break;
                case 5:
                    System.out.print("Enter medicine name: ");
                    String medicineName = scanner.nextLine();
                    System.out.print("Enter medicine quantity: ");
                    int medicineQuantity = scanner.nextInt();
                    System.out.print("Enter alert level quantity: ");
                    int medicineAlert = scanner.nextInt();
                    scanner.nextLine();  // Consume newline character
                    inventoryManager.addInventoryItem(medicineName, medicineQuantity, medicineAlert);
                    break;
                case 6:
                    System.out.print("Enter medicine ID to update: ");
                    String updateMedicineId = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline character
                    inventoryManager.updateInventoryItem(updateMedicineId, newQuantity);
                    break;
                case 7:
                    System.out.print("Enter medicine ID to remove: ");
                    String removeMedicineId = scanner.nextLine();
                    inventoryManager.removeInventoryItem(removeMedicineId);
                    break;
                case 8:
                    inventoryManager.viewAlertLevelInventory();
                    break;
                case 9:
                    System.out.print("Enter replenishment request ID to approve: ");
                    UUID approveRequestId = UUID.fromString(scanner.nextLine());
                    if (replenishmentRequestManager.approveRequest(approveRequestId)) {
                        System.out.println("Replenishment request approved.");
                    } else {
                        System.out.println("Failed to approve request.");
                    }
                    break;
                case 10:
                    System.out.print("Enter replenishment request ID to reject: ");
                    UUID rejectRequestId = UUID.fromString(scanner.nextLine());
                    if (replenishmentRequestManager.rejectRequest(rejectRequestId)) {
                        System.out.println("Replenishment request rejected.");
                    } else {
                        System.out.println("Failed to reject request.");
                    }
                    break;
                case 11:
                    staffManager.getAllStaff().forEach(staff -> System.out.println(staff));
                    break;
                case 12:
                    System.out.print("Enter role to filter by (or leave blank): ");
                    String role = scanner.nextLine();
                    System.out.print("Enter gender to filter by (or leave blank): ");
                    String gender = scanner.nextLine();
                    System.out.print("Enter age to filter by (or leave blank): ");
                    String ageInput = scanner.nextLine();
                    Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);
                    System.out.print("Enter department to filter by (or leave blank): ");
                    String department = scanner.nextLine();

                    staffManager.filterStaff(role, gender, age, department).forEach(staff -> System.out.println(staff));
                    break;
                case 13:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
