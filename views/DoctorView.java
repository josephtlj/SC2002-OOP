package views;

import java.util.InputMismatchException;
import java.util.Scanner;

import Enum.DoctorAppointmentActionType;
import Enum.DoctorMedicalRecordActionType;
import interfaces.DoctorViewInterface;
import models.Doctor;
import models.Session;

public class DoctorView implements DoctorViewInterface
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
                        (3) Manage Personal Schedule
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

                DoctorAppointmentActionType appointmentActionType;
                DoctorMedicalRecordActionType medicalRecordActionType;

                switch (doctorChoice) 
                {
                    
                    case 1:
                        medicalRecordActionType= DoctorMedicalRecordActionType.VIEW;
                        doctor.ManageMedicalRecord(medicalRecordActionType);
                        break;

                    case 2:
                        medicalRecordActionType= DoctorMedicalRecordActionType.UPDATE;
                        doctor.ManageMedicalRecord(medicalRecordActionType);
                        break;

                    case 3:
                        doctor.ManangeSchedule();
                        break;

                    case 4:
                        appointmentActionType= DoctorAppointmentActionType.SET_AVAILABILITY;
                        doctor.ManageAppointments(appointmentActionType);
                        break;

                    case 5:
                        appointmentActionType= DoctorAppointmentActionType.ACCEPT_OR_DECLINE;
                        doctor.ManageAppointments(appointmentActionType);
                        break;

                    case 6:
                        appointmentActionType= DoctorAppointmentActionType.VIEW;
                        doctor.ManageAppointments(appointmentActionType);
                        break;
                    
                    case 7:
                        doctor.ManageAppointmentOutcomeRecord();
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