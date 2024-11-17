package src.views;

import java.util.Scanner;

import src.controllers.PatientController;

public class PatientView {
    private final PatientController patientController;
    private final Scanner scanner = new Scanner(System.in);

    public PatientView(PatientController patientController) {
        this.patientController = patientController;
    }

    public int showMainMenu() {
        while (true) {
            try {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                      Welcome Patient                      |
                        =============================================================
                        Please select an option:
                        (1) View Medical Record
                        (2) Update Personal Information
                        (3) View Appointment Slots
                        (4) Schedule Appointment
                        (5) Reschedule Appointment
                        (6) Cancel Appointment
                        (7) View Scheduled Appointments
                        (8) View Past AppointmentOutcome Records
                        (9) Logout
                        """);
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= 9) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a choice between 1 and 9.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

    }
}
