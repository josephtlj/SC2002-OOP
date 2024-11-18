package src.views;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import src.models.MedicalRecord;
import src.models.Session;
import src.controllers.PatientController;

public class PatientView {
    private final PatientController patientController;
    private final Scanner scanner = new Scanner(System.in);

    public PatientView(PatientController patientController) {
        this.patientController = patientController;
    }

    public void showMainMenu() {
        int patientChoice = 99999;
        while (patientChoice != 9) {
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
                        (8) View Past Appointment Outcome Records
                        (9) Logout
                        """);

                System.out.print("Enter your choice: ");
                patientChoice = Integer.parseInt(scanner.nextLine());

                if (patientChoice >= 1 && patientChoice <= 9) {
                    switch (patientChoice) {
                        case 1:
                            showMedicalRecord();
                            break;
                        case 2:
                            showUpdatePersonalInformation();
                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                        case 5:

                            break;
                        case 6:

                            break;
                        case 7:

                            break;
                        case 8:

                            break;
                        case 9:
                            Session.getCurrentSession().logout();
                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a choice between 1 and 9.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

    }

    private void showMedicalRecord() {
        try {
            String patientHospitalId = Session.getCurrentSession().getCurrentUser().getHospitalId();

            System.out.println("""
                    =============================================================
                    |             Hospital Management System (HMS)!             |
                    |                   View Medical Record                     |
                    =============================================================
                    """);
            MedicalRecord medicalRecord = patientController.handleViewMedicalRecord(patientHospitalId);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String formattedDob = sdf.format(medicalRecord.getDob());

            System.out.printf("%-30s %-30s\n", "Name:", medicalRecord.getName());
            System.out.printf("%-30s %-30s\n", "Date of Birth:", formattedDob);
            System.out.printf("%-30s %-30s\n", "Gender:", medicalRecord.getGender());
            System.out.printf("%-30s %-30s\n", "Phone Number:", medicalRecord.getPhoneNumber());
            System.out.printf("%-30s %-30s\n", "Email Address:", medicalRecord.getEmailAddress());
            System.out.printf("%-30s %-30s\n", "Blood Type:", medicalRecord.getBloodType());
            System.out.printf("%-30s %-30s\n", "Past Diagnosis Treatments:", medicalRecord.getPastDiagnosisTreatment());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }

    }

    private void showUpdatePersonalInformation() {
        try {
            String patientHospitalId = Session.getCurrentSession().getCurrentUser().getHospitalId();

            int patientChoice = 99999;
            while (patientChoice != 4) {

                showMedicalRecord();

                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                Update Personal Information                |
                        =============================================================
                        Please select an option:
                        (1) Update Password
                        (2) Update Email Address
                        (3) Update Phone Number
                        (4) Back to previous page
                        """);

                System.out.print("Enter your choice: ");
                patientChoice = Integer.parseInt(scanner.nextLine());

                if (patientChoice >= 1 && patientChoice <= 4) {
                    switch (patientChoice) {
                        case 1:
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

                                updatePassword = patientController.handleUpdatePassword(patientHospitalId, newPassword,
                                        confirmPassword);
                            }

                            System.out.println("""
                                    =============================================================
                                    |             Hospital Management System (HMS)!             |
                                    |              Successfully updated password!               |
                                    =============================================================
                                    """);
                            break;
                        case 2:
                            boolean updateEmailAddress = false;
                            while (!updateEmailAddress) {
                                System.out.println("""
                                        =============================================================
                                        |             Hospital Management System (HMS)!             |
                                        |                   Update Email Address                    |
                                        =============================================================
                                        """);
                                System.out.println("Enter your new email address:");
                                String newEmailAddress = scanner.nextLine();

                                patientController.handleUpdateEmailAddress(patientHospitalId, newEmailAddress);
                            }

                            System.out.println("""
                                    =============================================================
                                    |             Hospital Management System (HMS)!             |
                                    |            Successfully updated email address!            |
                                    =============================================================
                                    """);
                            break;
                        case 3:
                            boolean updatePhoneNumber = false;
                            while (!updatePhoneNumber) {
                                System.out.println("""
                                        =============================================================
                                        |             Hospital Management System (HMS)!             |
                                        |                       Update Phone Number                        |
                                        =============================================================
                                        """);
                                System.out.println("Enter your new phone number:");
                                String newPhoneNumber = scanner.nextLine();

                                patientController.handleUpdatePhoneNumber(patientHospitalId, newPhoneNumber);
                            }

                            System.out.println("""
                                    =============================================================
                                    |             Hospital Management System (HMS)!             |
                                    |            Successfully updated phone number!             |
                                    =============================================================
                                    """);
                            break;
                        case 4:

                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a choice between 1 and 4.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }

    }
}
