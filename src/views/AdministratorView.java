package src.views;

import java.util.List;
import java.util.Scanner;

import src.models.Session;

import src.models.Administrator;
import src.models.Pharmacist;
import src.models.ReplenishmentRequest;
import src.controllers.AdministratorController;

public class AdministratorView {
    private final AdministratorController administratorController;
    private final Scanner scanner = new Scanner(System.in);

    public AdministratorView(AdministratorController administratorController) {
        this.administratorController = administratorController;

    }

    public void showMainMenu() {
        int administratorChoice = 99999;
        while (administratorChoice != 6) {
            try {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                   Welcome Administrator                   |
                        =============================================================
                        Please select an option:
                        (1) Update Password
                        (2) View and Manage Hospital Staff
                        (3) View Appointment Details
                        (4) View and Manage Medication Inventory
                        (5) Approve Replenishment Requests
                        (6) Logout
                        """);

                System.out.print("Enter your choice: ");
                administratorChoice = Integer.parseInt(scanner.nextLine());

                if (administratorChoice >= 1 && administratorChoice <= 6) {
                    switch (administratorChoice) {
                        case 1:
                            showUpdatePassword();
                            break;
                        case 2:
                            // showViewAndManageHospitalStaff();
                            break;
                        case 3:
                            // showViewAppointmentDetails();
                            break;
                        case 4:
                            // showViewAndManageMedicationInventory();
                            break;
                        case 5:
                            showApproveReplenishmentRequests();
                            break;
                        case 6:
                            Session.getCurrentSession().logout();
                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a choice between 1 and 6.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void showUpdatePassword() {
        try {
            String pharmacistHospitalId = Session.getCurrentSession().getCurrentUser().getHospitalId();

            boolean updatePassword = false;

            while (!updatePassword) {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                      Update Password                      |
                        =============================================================
                        """);

                System.out.println("Enter your new password:");
                String newPassword = scanner.nextLine();
                System.out.println("Confirm your new password:");
                String confirmPassword = scanner.nextLine();

                updatePassword = administratorController.handleUpdatePassword(pharmacistHospitalId, newPassword,
                        confirmPassword);
            }

            System.out.println("""
                    =============================================================
                    |             Hospital Management System (HMS)!             |
                    |              Successfully updated password!               |
                    =============================================================
                    """);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void showViewAndManageHospitalStaff() {
        System.out.println("""
                =============================================================
                |             Hospital Management System (HMS)!             |
                |              View and Manage Hospital Staff               |
                =============================================================
                """);
        // List<Administrator> administratorList;
        // List<Pharmacist> pharmacistList;

        // for (Medicine medicine : administratorList) {
        // System.out.printf("%-25s %-25s\n", "Medicine Name:",
        // medicine.getMedicineName());
        // System.out.printf("%-25s %-25s\n", "Medicine Quantity:",
        // medicine.getMedicineQuantity());
        // System.out.printf("%-25s %-25s\n", "Medicine Alert:",
        // medicine.getMedicineAlert());
        // System.out.println("-".repeat(51));
        // }
    }

    private void showApproveReplenishmentRequests() {
        int administratorChoice = 99999;
        while (administratorChoice != 0) {
            try {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |              Approve Replenishment Requests               |
                        =============================================================
                        """);

                List<ReplenishmentRequest> replenishmentRequestList = administratorController
                        .handleGetReplenishmentRequestsByStatus(ReplenishmentRequest.Status.PENDING);

                System.out.printf("There are %d Pending Replenishment Request(s):\n", replenishmentRequestList.size());
                System.out.println("-".repeat(60));

                for (int replenishmentRequestCount = 0; replenishmentRequestCount < replenishmentRequestList
                        .size(); replenishmentRequestCount++) {
                    System.out.printf("Replenishment Request #%d\n", replenishmentRequestCount + 1);

                    ReplenishmentRequest replenishmentRequest = replenishmentRequestList.get(replenishmentRequestCount);

                    System.out.printf("%-25s %-25s\n", "Request Id:", replenishmentRequest.getRequestId());
                    System.out.printf("%-25s %-25s\n", "Medicine Name:", replenishmentRequest.getMedicineName());
                    System.out.printf("%-25s %-25s\n", "Request Quantity:",
                            replenishmentRequest.getRequestedQuantity());
                    System.out.printf("%-25s %-25s\n", "Request Status:", replenishmentRequest.getStatus());
                    System.out.printf("%-25s %-25s\n", "Medicine Id:", replenishmentRequest.getMedicineId());
                    System.out.println("-".repeat(60));
                }

                System.out.println("""
                        Please enter the Replenishment Request Number (#) you wish to approve or reject, OR
                        Please enter 0 to go back to the previous page.
                        """);

                administratorChoice = scanner.nextInt();

                if (administratorChoice >= 0 && administratorChoice <= replenishmentRequestList.size()) {
                    if (administratorChoice == 0) {
                        break;
                    }

                    ReplenishmentRequest selectedReplenishmentRequest = replenishmentRequestList
                            .get(administratorChoice - 1);

                    System.out.println("""
                            Would you like to approve / reject this request?
                            Approve:  Please enter 1
                            Reject:   Please enter 2
                            """);

                    System.out.println("-".repeat(60));
                    System.out.printf("%-25s %-25s\n", "Request Id:", selectedReplenishmentRequest.getRequestId());
                    System.out.printf("%-25s %-25s\n", "Medicine Name:",
                            selectedReplenishmentRequest.getMedicineName());
                    System.out.printf("%-25s %-25s\n", "Request Quantity:",
                            selectedReplenishmentRequest.getRequestedQuantity());
                    System.out.printf("%-25s %-25s\n", "Request Status:", selectedReplenishmentRequest.getStatus());
                    System.out.printf("%-25s %-25s\n", "Medicine Id:", selectedReplenishmentRequest.getMedicineId());
                    System.out.println("-".repeat(60));

                    System.out.print("Your choice: ");
                    administratorChoice = scanner.nextInt();

                    if (administratorChoice >= 1 && administratorChoice <= 2) {
                        switch (administratorChoice) {
                            case 1:
                                administratorController.handleReplenishmentRequest(
                                        selectedReplenishmentRequest.getRequestId(),
                                        selectedReplenishmentRequest.getRequestedQuantity(),
                                        ReplenishmentRequest.Status.APPROVED,
                                        selectedReplenishmentRequest.getMedicineId());

                                System.out.println("""
                                        =============================================================
                                        |             Hospital Management System (HMS)!             |
                                        |       Successfully approved replenishment request!        |
                                        =============================================================
                                        """);
                                break;

                            case 2:
                                administratorController.handleReplenishmentRequest(
                                        selectedReplenishmentRequest.getRequestId(),
                                        ReplenishmentRequest.Status.REJECTED);
                                System.out.println("""
                                        =============================================================
                                        |             Hospital Management System (HMS)!             |
                                        |       Successfully rejected replenishment request!        |
                                        =============================================================
                                        """);
                                break;
                        }
                    } else {
                        System.out.println("Invalid choice. Please enter a choice between 1 and 2");
                    }

                } else {
                    System.out.printf("Invalid choice. Please enter a choice between 0 and %d",
                            replenishmentRequestList.size());
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }

        }
    }

}
