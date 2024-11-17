package src.views;

import java.util.Scanner;

import src.controllers.PharmacistController;
import src.models.Session;

public class PharmacistView {
    private final PharmacistController pharmacistController;
    private final Scanner scanner = new Scanner(System.in);

    public PharmacistView(PharmacistController pharmacistController) {
        this.pharmacistController = pharmacistController;
    }

    public void showMainMenu() {
        int pharmacistChoice = 99999;
        while (pharmacistChoice != 9) {
            try {
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
                            showAppointmentOutcomeRecord();
                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                        case 5:
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

                pharmacistController.handleUpdatePassword(pharmacistHospitalId, newPassword, confirmPassword);
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

    private void showAppointmentOutcomeRecord(){
        
    }
}
