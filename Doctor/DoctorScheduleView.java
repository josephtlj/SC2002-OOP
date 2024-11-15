package Doctor;

import java.util.InputMismatchException;
import java.util.Scanner;

import models.Session;

public class DoctorScheduleView 
{
    //ATTRIBUTES
    Scanner doctorScanner= new Scanner(System.in);

    //CONSTRUCTOR
    public DoctorScheduleView(DoctorScheduleManager scheduleManager)
    {
        int doctorChoice = 0;

        do
        {
            try
            {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                     Doctor Schedule                       |
                        =============================================================
                        Please select an option:
                        (1) Apply for Annual Leave (AL)
                        (2) Apply for Medical Leave (MC)
                        (3) Go to previous page
                        """);

                // RETRIEVE DOCTOR'S CHOICE
                System.out.println("Your Choice: ");
                doctorChoice = doctorScanner.nextInt();
                doctorScanner.nextLine();

                switch (doctorChoice) 
                {
                    
                    case 1:
                        scheduleManager.applyAnnualLeave();
                        break;
                    case 2:
                        scheduleManager.applyMedicalLeave();
                        break;
                    case 3:
                        return; //GO BACK TO CALLING FUNCTION
                    default:
                        System.out.println("Please enter a valid choice!");
                        break;
                }

            } 
            catch (InputMismatchException inputMismatchException) 
            {
                System.out.println("Please enter a valid integer only!");
                doctorScanner.nextLine();
            }

        } while (doctorChoice != 4);
    }

    //METHODS
    public void printSchedule()
    {
        System.out.println("Enter month:");
        int month= doctorScanner.nextInt();
        doctorScanner.nextLine();
    }
}
