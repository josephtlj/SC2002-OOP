package Doctor;

import java.util.InputMismatchException;
import java.util.Scanner;

import Doctor.Appointment.DoctorAppointmentActionType;
import models.Session;

public class DoctorLoginView 
{
    private Scanner doctorScanner = new Scanner(System.in);

    public void displayDoctorLoginView(Doctor doctor) 
    {
        // PROMPT DOCTOR TO RESET PASSWORD SHOULD IT BE THEIR FIRST TIME LOGGING IN
        if (Session.getCurrentUser().getIsFirstLogin()) 
            {
                doctor.updateDoctorPassword();
            }
            else
            {
                printMenu(doctor);
            }
    }

    public void printMenu(Doctor doctor)
    {
        // FUNCTION EXECUTES TILL USER WANTS TO GO BACK TO HOMEPAGE
        int doctorChoice = 0;

        do
        {
            try
            {
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

                DoctorAppointmentActionType actionType;

                switch (doctorChoice) 
                {
                    
                    case 1:
                        System.out.println("Enter full name of patient:");
                        String patientName= doctorScanner.nextLine();

                    case 4:
                        actionType= DoctorAppointmentActionType.SET_AVAILABILITY;
                        doctor.ManageAppointments(actionType);
                        break;

                    case 5:
                        actionType= DoctorAppointmentActionType.ACCEPT_OR_DECLINE;
                        doctor.ManageAppointments(actionType);
                        break;

                    case 6:
                        actionType= DoctorAppointmentActionType.VIEW;
                        doctor.ManageAppointments(actionType);
                        break;
                    
                    
                    case 8:
                        Session.logout();
                        break;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }

            } 
            catch (InputMismatchException inputMismatchException) 
            {
                System.out.println("Please enter a valid integer only!\n");
                doctorScanner.nextLine();
            }

        } while (doctorChoice != 9);
            
        }
}