package src.views;

import java.util.List;
import java.util.Scanner;

import src.models.Session;

import src.models.Administrator;
import src.models.Pharmacist;
import src.models.ReplenishmentRequest;
import src.models.Medicine;

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
                            showViewAndManageMedicationInventory();
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
                (1) View Hospital Staff
                (2) Manage Hospital Staff
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

    private void showViewAndManageMedicationInventory() {
        int administratorChoice = 99999;
        while (administratorChoice != 5) {
            try {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |           View and Manage Medication Inventory            |
                        =============================================================
                        (1) View Medicine Inventory
                        (2) Add New Medication
                        (3) Update Medication
                        (4) Delete Medication
                        (5) Go back to previous page
                        """);

                System.out.print("Your choice: ");
                String input = scanner.nextLine();
                try {
                    administratorChoice = Integer.parseInt(input);

                    if (administratorChoice >= 1 && administratorChoice <= 5) {
                        switch (administratorChoice) {
                            case 1:
                                showViewMedicineInventory();
                                break;

                            case 2:
                                boolean addNewMedicine = false;
                                while (!addNewMedicine) {
                                    try {
                                        System.out.println("Enter Medicine Name:");
                                        String medicineName = scanner.nextLine();

                                        System.out.println("Enter Medicine Quantity:");
                                        int medicineQuantity;
                                        try {
                                            medicineQuantity = Integer.parseInt(scanner.nextLine());
                                            if (medicineQuantity <= 0) {
                                                System.out.println("Medicine quantity must be a positive number.");
                                                continue;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println(
                                                    "Invalid input. Please enter a valid number for Medicine Quantity.");
                                            continue;
                                        }

                                        System.out.println("Enter Medicine Alert Quantity:");
                                        int medicineAlert;
                                        try {
                                            medicineAlert = Integer.parseInt(scanner.nextLine());
                                            if (medicineAlert <= 0) {
                                                System.out
                                                        .println("Medicine alert quantity must be a positive number.");
                                                continue;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println(
                                                    "Invalid input. Please enter a valid number for Medicine Alert Quantity.");
                                            continue;
                                        }

                                        addNewMedicine = administratorController.handleAddNewMedication(medicineName,
                                                medicineQuantity, medicineAlert);
                                    } catch (Exception e) {
                                        System.out.println("An unexpected error occurred. Please try again.");
                                        e.printStackTrace();
                                    }

                                }

                                System.out.println("""
                                        =============================================================
                                        |             Hospital Management System (HMS)!             |
                                        |             Successfully added new medicine!              |
                                        =============================================================
                                        """);

                                break;

                            case 3:
                                administratorChoice = 99999;
                                while (administratorChoice != 0) {
                                    List<Medicine> medicineList = showViewMedicineInventory();
                                    System.out.println("""
                                            Please enter the Medicine Number (#) you wish to update, OR
                                            Please enter 0 to go back to the previous page.
                                            """);
                                    try {
                                        System.out.print("Enter your choice: ");
                                        input = scanner.nextLine();

                                        administratorChoice = Integer.parseInt(input);

                                        if (administratorChoice < 0 || administratorChoice > medicineList.size()) {
                                            System.out.printf(
                                                    "Invalid choice. Please enter a choice between 0 and %d.\n",
                                                    medicineList.size());
                                            continue;
                                        }

                                        if (administratorChoice == 0) {
                                            break;
                                        }

                                        Medicine selectedMedicine = medicineList.get(administratorChoice - 1);

                                        boolean updateMedicine = false;

                                        while (!updateMedicine) {
                                            try {
                                                System.out.println("Enter Medicine Quantity:");
                                                int medicineQuantity = Integer.parseInt(scanner.nextLine());

                                                if (medicineQuantity <= 0) {
                                                    System.out.println(
                                                            "Invalid input. Medicine quantity must be a positive number.");
                                                    continue;
                                                }

                                                System.out.println("Enter Medicine Alert Quantity:");
                                                int medicineAlert = Integer.parseInt(scanner.nextLine());

                                                if (medicineAlert <= 0) {
                                                    System.out.println(
                                                            "Invalid input. Medicine alert quantity must be a positive number.");
                                                    continue;
                                                }

                                                updateMedicine = administratorController.handleUpdateMedication(
                                                        medicineQuantity, medicineAlert,
                                                        selectedMedicine.getMedicineId());
                                            } catch (NumberFormatException e) {
                                                System.out.println("Invalid input. Please enter a valid number.");
                                            }
                                        }

                                        System.out.println("""
                                                =============================================================
                                                |             Hospital Management System (HMS)!             |
                                                |              Successfully updated medicine!               |
                                                =============================================================
                                                """);

                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a valid number.");
                                    }
                                }

                                break;
                            case 4:

                                administratorChoice = 99999;
                                while (administratorChoice != 0) {
                                    List<Medicine> medicineList = showViewMedicineInventory();
                                    System.out.println("""
                                            Please enter the Medicine Number (#) you wish to delete, OR
                                            Please enter 0 to go back to the previous page.
                                            """);
                                    try {
                                        System.out.print("Enter your choice: ");
                                        input = scanner.nextLine();

                                        administratorChoice = Integer.parseInt(input);

                                        if (administratorChoice < 0 || administratorChoice > medicineList.size()) {
                                            System.out.printf(
                                                    "Invalid choice. Please enter a choice between 0 and %d.\n",
                                                    medicineList.size());
                                            continue;
                                        }

                                        if (administratorChoice == 0) {
                                            break;
                                        }

                                        Medicine selectedMedicine = medicineList.get(administratorChoice - 1);

                                        administratorController
                                                .handleDeleteMedication(selectedMedicine.getMedicineId());

                                        System.out.println("""
                                                =============================================================
                                                |             Hospital Management System (HMS)!             |
                                                |              Successfully deleted medicine!               |
                                                =============================================================
                                                """);

                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a valid number.");
                                    } catch (Exception e) {
                                        System.out.println("An unexpected error occurred. Please try again.");
                                        e.printStackTrace();
                                    }
                                }

                                break;
                            case 5:

                                break;
                        }
                    } else {
                        System.out.println("Invalid choice. Please enter a choice between 1 and 5.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                e.printStackTrace();
            }
        }
    }

    private List<Medicine> showViewMedicineInventory() {
        List<Medicine> medicineList = administratorController.handleViewMedicationInventory();

        for (int medicineCount = 0; medicineCount < medicineList.size(); medicineCount++) {
            Medicine medicine = medicineList.get(medicineCount);

            System.out.println("-".repeat(60));
            System.out.printf("Medicine #%d\n", medicineCount + 1);
            System.out.printf("%-25s %-25s\n", "Medicine Name:", medicine.getMedicineName());
            System.out.printf("%-25s %-25s\n", "Medicine Quantity:",
                    medicine.getMedicineQuantity());
            System.out.printf("%-25s %-25s\n", "Medicine Alert:", medicine.getMedicineAlert());
            System.out.println("-".repeat(60));
        }

        return medicineList;
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

                if (replenishmentRequestList.isEmpty()) {
                    System.out.println("No pending replenishment requests at this time.");
                    return;
                }

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

                try {
                    System.out.print("Enter your choice: ");
                    administratorChoice = Integer.parseInt(scanner.nextLine());

                    if (administratorChoice < 0 || administratorChoice > replenishmentRequestList.size()) {
                        System.out.printf("Invalid choice. Please enter a choice between 0 and %d.\n",
                                replenishmentRequestList.size());
                        continue;
                    }

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

                    try {
                        System.out.print("Your choice: ");
                        int actionChoice = Integer.parseInt(scanner.nextLine());

                        if (actionChoice == 1) {
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
                        } else if (actionChoice == 2) {
                            administratorController.handleReplenishmentRequest(
                                    selectedReplenishmentRequest.getRequestId(),
                                    ReplenishmentRequest.Status.REJECTED);

                            System.out.println("""
                                    =============================================================
                                    |             Hospital Management System (HMS)!             |
                                    |       Successfully rejected replenishment request!        |
                                    =============================================================
                                    """);
                        } else {
                            System.out.println("Invalid choice. Please enter 1 to approve or 2 to reject.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number (1 or 2).");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }

            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                e.printStackTrace();
            }
        }
    }
}
