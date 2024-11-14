package views;

import java.util.InputMismatchException;
import java.util.Scanner;

import models.Session;
import models.MedicalRecord;
import interfaces.DoctorInteraction;
import interfaces.PatientInteraction;
import controllers.DoctorController;
import controllers.PatientController;

public class DoctorView implements DoctorInteraction 
{
    private Scanner doctorScanner = new Scanner(System.in);

    @Override
    public void displayDoctorView() 
    {
        // INITIALISE DOCTORCONTROLLER
        DoctorController doctorController = new DoctorController();

        // FUNCTION EXECUTES TILL USER WANTS TO GO BACK TO HOMEPAGE
        int doctorChoice = 0;

        do {
            try {
                // PROMPT DOCTOR TO RESET PASSWORD SHOULD IT BE THEIR FIRST TIME LOGGING IN
                if (Session.getCurrentUser().getIsFirstLogin()) {
                    boolean updatePassword = false;
                    String newPassword = null;
                    String confirmPassword = null;

                    while (updatePassword == false) {
                        System.out.println("""
                                =====================================================================================
                                |                         Hospital Management System (HMS)!                         |
                                |                                  Welcome Doctor                                   |
                                =====================================================================================
                                As this is your first time logging into your account, kindly reset your password.
                                """);
                        System.out.print("Enter your new password: ");
                        newPassword = doctorScanner.nextLine();
                        System.out.print("Re-enter your new password: ");
                        confirmPassword = doctorScanner.nextLine();

                        updatePassword = doctorController.updateDoctorPassword(newPassword, confirmPassword,
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
                        |                      Welcome Doctor                       |
                        =============================================================
                        Please select an option:
                        (1) View Patient Medical Records
                        (2) Update Patient Medical Records
                        (3) View Personal Schedule
                        (4) Set Availability for Appointments
                        (5) Accept or Decline Appointment Requests
                        (6) View Upcoming Appointments
                        (7) Record Appointment Outcome
                        (8) Logout
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.println("Your Choice: ");
                doctorChoice = doctorScanner.nextInt();
                doctorScanner.nextLine();

                switch (doctorChoice) {
                    
                    case 1:
                        System.out.println("Enter full name of patient:");
                        String patientName= doctorScanner.nextLine();


                    
                    
                    case 8:
                        Session.logout();
                        break;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please enter a valid integer only!\n");
                doctorScanner.nextLine();
            }

        } while (doctorChoice != 9);
    }
}