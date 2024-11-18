package Administrator.backup;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class AdministratorMain {
    private static AdministratorMain admin;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Simulate admin login
        System.out.println("Welcome to the Hospital Management System");
        System.out.print("Enter Admin ID: ");
        String adminIdInput = scanner.nextLine();
        System.out.print("Enter Staff ID: ");
        String staffID = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            // Creating an Administrator object
            admin = new AdministratorMain(UUID.fromString(adminIdInput), staffID, name, password);
        } catch (IOException e) {
            System.out.println("Error loading data files: " + e.getMessage());
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Exiting...");
            return;
        }

        // Display menu options in a loop until the user decides to exit
        boolean running = true;
        while (running) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. Approve Replenishment Request");
            System.out.println("2. View and Manage Hospital Staff");
            System.out.println("3. View and Manage Medicine Inventory");
            System.out.println("4. View Appointments");
            System.out.println("5. Log Out");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    handleApproveReplenishmentRequest(scanner);
                    break;
                case 2:
                    handleManageStaff(scanner);
                    break;
                case 3:
                    handleManageMedicine(scanner);
                    break;
                case 4:
                    admin.viewAppointment();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void handleApproveReplenishmentRequest(Scanner scanner) {
        System.out.print("Enter Replenishment Request ID: ");
        String requestId = scanner.nextLine();
        try {
            admin.approveReplenishmentRequest(UUID.fromString(requestId));
        } catch (Exception e) {
            System.out.println("Error approving request: " + e.getMessage());
        }
    }

    private static void handleManageStaff(Scanner scanner) {
        System.out.println("\nManage Hospital Staff:");
        System.out.println("1. View All Staff");
        System.out.println("2. Add Staff");
        System.out.println("3. Update Staff");
        System.out.println("4. Remove Staff");
        System.out.println("5. Display Filtered Staff");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        switch (choice) {
            case 1:
                admin.viewManageHospitalStaff();
                break;
            case 2:
                System.out.print("Enter Staff ID: ");
                String staffID = scanner.nextLine();
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Role: ");
                String role = scanner.nextLine();
                System.out.print("Enter Gender: ");
                String gender = scanner.nextLine();
                System.out.print("Is Active (true/false): ");
                boolean isActive = scanner.nextBoolean();
                admin.addStaff(staffID, name, role, gender, isActive);
                break;
            case 3:
                System.out.print("Enter Staff ID to Update: ");
                String updateStaffID = scanner.nextLine();
                System.out.print("Enter New Name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter New Role: ");
                String newRole = scanner.nextLine();
                System.out.print("Enter New Gender: ");
                String newGender = scanner.nextLine();
                admin.updateStaff(updateStaffID, newName, newRole, newGender);
                break;
            case 4:
                System.out.print("Enter Staff ID to Remove: ");
                String removeStaffID = scanner.nextLine();
                admin.removeStaff(removeStaffID);
                break;
            case 5:
                System.out.print("Enter Role (or leave empty): ");
                String filterRole = scanner.nextLine();
                System.out.print("Enter Gender (or leave empty): ");
                String filterGender = scanner.nextLine();
                System.out.print("Enter Age (or leave empty): ");
                String ageInput = scanner.nextLine();
                Integer filterAge = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);
                System.out.print("Enter Department (or leave empty): ");
                String departmentInput = scanner.nextLine();
                Department department = departmentInput.isEmpty() ? null : Department.valueOf(departmentInput.toUpperCase());
                admin.displayFilteredStaff(filterRole, filterGender, filterAge, department);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void handleManageMedicine(Scanner scanner) {
        System.out.println("\nManage Medicine Inventory:");
        System.out.println("1. View All Medicines");
        System.out.println("2. Add Medicine");
        System.out.println("3. Update Medicine");
        System.out.println("4. Remove Medicine");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        switch (choice) {
            case 1:
                admin.viewManageMedicine();
                break;
            case 2:
                System.out.print("Enter Medicine Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter Alert Line: ");
                int alertLine = scanner.nextInt();
                admin.addMedicine(name, quantity, alertLine);
                break;
            case 3:
                System.out.print("Enter Medicine ID to Update: ");
                UUID medicineId = UUID.fromString(scanner.nextLine());
                System.out.print("Enter New Quantity: ");
                int newQuantity = scanner.nextInt();
                System.out.print("Enter New Alert Line: ");
                int newAlertLine = scanner.nextInt();
                admin.updateMedicine(medicineId, newQuantity, newAlertLine);
                break;
            case 4:
                System.out.print("Enter Medicine ID to Remove: ");
                UUID removeMedicineId = UUID.fromString(scanner.nextLine());
                admin.removeMedicine(removeMedicineId);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
