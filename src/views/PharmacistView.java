package src.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import src.models.Session;
import src.models.AppointmentOutcomeRecord;
import src.models.Medicine;
import src.controllers.PharmacistController;

public class PharmacistView {
    private final PharmacistController pharmacistController;
    private final Scanner scanner = new Scanner(System.in);

    public PharmacistView(PharmacistController pharmacistController) {
        this.pharmacistController = pharmacistController;
    }

    public void showMainMenu() {
        int pharmacistChoice = 99999;
        while (pharmacistChoice != 6) {
            try {

                if(Session.getCurrentSession().getCurrentUser().getIsFirstLogin()){
                    System.out.println("As this is your first login, kindly reset your password.");
                    showUpdatePassword();
                }

                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                    Welcome Pharmacist                     |
                        =============================================================
                        Please select an option:
                        (1) Update Password
                        (2) View Appointment Outcome Record
                        (3) Update Prescription Status
                        (4) View Medication Inventory
                        (5) Submit Replenishment Request
                        (6) Logout
                        """);

                System.out.print("Enter your choice: ");
                pharmacistChoice = Integer.parseInt(scanner.nextLine());

                if (pharmacistChoice >= 1 && pharmacistChoice <= 6) {
                    switch (pharmacistChoice) {
                        case 1:
                            showUpdatePassword();
                            break;
                        case 2:
                            // showAppointmentOutcomeRecords();
                            break;
                        case 3:
                            // showUpdatePrescriptionStatus();
                            break;
                        case 4:
                            showMedicationInventory();
                            break;
                        case 5:
                            showSubmitReplenishmentRequest();
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

                updatePassword = pharmacistController.handleUpdatePassword(pharmacistHospitalId, newPassword,
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

    private void showAppointmentOutcomeRecords() {
        // System.out.println("""
        //         =============================================================
        //         |             Hospital Management System (HMS)!             |
        //         |            View Appointment Outcome Record(s)             |
        //         =============================================================
        //         """);

        // boolean viewApptOutRec = false;
        // LocalDate date;

        // while (!viewApptOutRec) {
        //     System.out.println("Enter the day which you wish to view Appointment Outcome Records (dd/MM/yyyy)");
        //     String viewDate = scanner.nextLine();

        //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
        //     try {
        //         // Try to parse the input string
        //         date = LocalDate.parse(viewDate, formatter);
        //         viewApptOutRec = true; // Set flag to true if parsing succeeds
        //         System.out.println("Valid date: " + date);
        //     } catch (Exception e) {
        //         // Handle invalid date format
        //         System.out.println("Invalid date format. Please try again using the format dd/MM/yyyy.");
        //     }
        // }

        // List<AppointmentOutcomeRecord> appointmentOutcomeRecordList = pharmacistController
        //         .handleViewAppointmentOutcomeRecordsByDay(date);
    }

    private void showMedicationInventory() {
        System.out.println("""
                =============================================================
                |             Hospital Management System (HMS)!             |
                |                View Medication Inventory                  |
                =============================================================
                """);
        List<Medicine> medicineList = pharmacistController.handleViewMedicationInventory();

        for (Medicine medicine : medicineList) {
            System.out.printf("%-25s %-25s\n", "Medicine Name:", medicine.getMedicineName());
            System.out.printf("%-25s %-25s\n", "Medicine Quantity:", medicine.getMedicineQuantity());
            System.out.printf("%-25s %-25s\n", "Medicine Alert:", medicine.getMedicineAlert());
            System.out.println("-".repeat(60));
        }
    }

    private void showSubmitReplenishmentRequest() {
        try {
            boolean submitReplenishmentRequest = false;

            while (!submitReplenishmentRequest) {
                showMedicationInventory();

                System.out.println("Enter the name of the medicine to be replenished:");
                String medicineName = scanner.nextLine();

                System.out.println("Enter the quantity to be replenished:");
                int replenishmentQuantity;

                try {
                    replenishmentQuantity = Integer.parseInt(scanner.nextLine());
                    if (replenishmentQuantity <= 0) {
                        System.out.println("Quantity must be a positive number. Please try again.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity. Please enter a valid number.");
                    continue;
                }

                submitReplenishmentRequest = pharmacistController.handleSubmitReplenishmentRequest(medicineName,
                        replenishmentQuantity);
            }
            System.out.println("""
                    =============================================================
                    |             Hospital Management System (HMS)!             |
                    |       Successfully submitted replenishment request!       |
                    =============================================================
                    """);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
            e.printStackTrace();
        }
    }
}
