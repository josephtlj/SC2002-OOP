package views;

import java.util.InputMismatchException;
import java.util.Scanner;

import models.Session;
import models.MedicalRecord;
import interfaces.PatientInteraction;
import controllers.PatientController;

public class PatientView implements PatientInteraction {
    private Scanner patientScanner = new Scanner(System.in);

    @Override
    public void displayPatientView() {
        // INITIALISE PATIENTCONTROLLER
        PatientController patientController = new PatientController();

        // FUNCTION EXECUTES TILL USER WANTS TO GO BACK TO HOMEPAGE
        int patientChoice = 0;

        do {
            try {
                // PROMPT PATIENT TO RESET PASSWORD SHOULD IT BE HIS FIRST TIME LOGGING IN
                if (Session.getCurrentUser().getIsFirstLogin()) {
                    boolean updatePassword = false;
                    String newPassword = null;
                    String confirmPassword = null;

                    while (updatePassword == false) {
                        System.out.println("""
                                =====================================================================================
                                |                         Hospital Management System (HMS)!                         |
                                |                                  Welcome Patient                                  |
                                =====================================================================================
                                As this is your first time logging into your account, kindly reset your password.
                                """);
                        System.out.print("Enter your new password: ");
                        newPassword = patientScanner.nextLine();
                        System.out.print("Re-enter your new password: ");
                        confirmPassword = patientScanner.nextLine();

                        updatePassword = patientController.updatePatientPassword(newPassword, confirmPassword,
                                Session.getCurrentUser().getHospitalId());

                    }

                    System.out.println("""
                            =====================================================================================
                            |                         Hospital Management System (HMS)!                         |
                            |              Password Successfully Updated! Kindly login once again.              |
                            =====================================================================================
                            """);
                    Session.logout();
                    return;

                }

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

                // RETRIEVE USER'S CHOICE
                System.out.print("Your Choice: ");
                patientChoice = patientScanner.nextInt();
                patientScanner.nextLine();

                switch (patientChoice) {
                    case 1:
                        MedicalRecord medicalRecord = patientController
                                .viewMedicalRecord(Session.getCurrentUser().getHospitalId());
                        System.out.println(String.format("""
                                ==============================================
                                |               Medical Record               |
                                ==============================================
                                Name: %s
                                Date Of Birth: %s
                                Gender: %s,
                                Phone Number: %s
                                Email Address: %s
                                Blood Type: %s
                                Past Diagnosis & Treatment: %s
                                """, medicalRecord.getName(), medicalRecord.getDob(), medicalRecord.getGender(),
                                medicalRecord.getPhoneNumber(), medicalRecord.getEmailAddress(),
                                medicalRecord.getBloodType(),
                                medicalRecord.getPastDiagnosisTreatment()));
                        break;
                    case 2:
                        System.out.println(String.format("""
                                ===============================================
                                |         Update Personal Information         |
                                ===============================================
                                Please select an option:
                                (1) Update Password
                                (2) Update Email Address
                                (3) Update Phone Number
                                (4) Go back to previous page
                                """));

                        // RETRIEVE USER'S CHOICE
                        System.out.print("Your Choice: ");
                        patientChoice = patientScanner.nextInt();
                        patientScanner.nextLine();

                        switch (patientChoice) {
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                            case 4:

                                break;

                            default:
                                System.out.println("Please enter a valid choice!\n");
                                break;
                        }

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
                        Session.logout();
                        break;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please enter a valid integer only!\n");
                patientScanner.nextLine();
            }

        } while (patientChoice != 9);
    }
}