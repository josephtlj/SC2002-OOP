package src.views;

import java.util.InputMismatchException;
import java.util.Scanner;
import src.utils.ENUM.ApplyAnnualLeaveError;
import src.utils.ENUM.CalendarDayStatus;
import src.controllers.CalendarController;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class CalendarView 
{
    //ATTRIBUTES
    private Scanner calendarScanner = new Scanner(System.in);
    private CalendarController calendarManager;
    InputForDateMonthTimeSlotView inputForDateMonthTimeSlotView= new InputForDateMonthTimeSlotView();

    //CONSTRUCTOR
    public CalendarView(String ID)
    {
        this.calendarManager= new CalendarController(ID);
        printCalendarManagement();
    }

    //VIEW METHODS

    public void printCalendarManagement()
    {

        // FUNCTION EXECUTES TILL USER WANTS TO GO BACK TO HOMEPAGE
        int leaveChoice = 0;

        do
        {
            try
            {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                      Manage Schedule                      |
                        =============================================================
                        Please select an option:
                        (1) View Schedule
                        (2) Apply for Leave
                        (3) Cancel Leave
                        (4) Go to previous page
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.println("Your Choice: ");
                leaveChoice = calendarScanner.nextInt();
                calendarScanner.nextLine();

                switch (leaveChoice) 
                {
                    case 1:
                        printCalendar();
                        break;

                    case 2:
                        printApplyLeave();
                        break;

                    case 3:
                        printCancelLeave();
                        break;

                    case 4:
                        return;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }

            } 
            catch (InputMismatchException inputMismatchException) 
            {
                System.out.println("Please enter a valid integer only!\n");
                calendarScanner.nextLine();
            }

        } while (leaveChoice != 5);
            
    }

    //PRINT CALENDAR
    public void printCalendar()
    {
        int choice= inputForDateMonthTimeSlotView.viewByDateOrMonth();

        if(choice==1)
        {
            LocalDate date=inputForDateMonthTimeSlotView.viewWhichDate();
            printCalendarDate(date);
        }
        else
        {
            int month= inputForDateMonthTimeSlotView.viewWhichMonth();
            printCalendarMonth(month);
        }

    }

    public void printCalendarDate(LocalDate date)
    {
        CalendarDayStatus status = calendarManager.getStatusForDate(date); 
        String statusString = status.toString().replace("_", " ").toLowerCase();
        statusString = Character.toUpperCase(statusString.charAt(0)) + statusString.substring(1);
        System.out.println("\n📅 Calendar for " + date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        System.out.println("----------------------------------");
        System.out.printf("| %-10s | %-15s |%n", "Day", "Status");
        System.out.println("----------------------------------");
        System.out.printf("| %-10s | %-15s |%n", date.getDayOfWeek(), status);
        System.out.println("----------------------------------");
    }

    public void printCalendarMonth(int month)
    {
        System.out.println("\n📅 Calendar for " + LocalDate.of(LocalDate.now().getYear(), month, 1).format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        System.out.println("-------------------------------------------------");
        System.out.printf("| %-5s | %-10s | %-15s |%n", "Date", "Day", "Status");
        System.out.println("-------------------------------------------------");

        for (int day = 1; day <= LocalDate.of(LocalDate.now().getYear(), month, 1).lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, day);
            CalendarDayStatus status = calendarManager.getStatusForDate(date); 
            String statusString = status.toString().replace("_", " ").toLowerCase();
            statusString = Character.toUpperCase(statusString.charAt(0)) + statusString.substring(1);
            System.out.printf("| %-5d | %-10s | %-15s |%n", day, date.getDayOfWeek(), status);
        }

        System.out.println("-------------------------------------------------");
    }

    //APPLY FOR LEAVE
    public void printApplyLeave()
    {
        int leaveChoice = 0;

        do
        {
            try
            {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                      Apply Leave                          |
                        =============================================================
                        Please select an option:
                        (1) Apply for Annual Leave
                        (2) Apply for Medical Leave
                        (3) Go to previous page
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.println("Your Choice: ");
                leaveChoice = calendarScanner.nextInt();
                calendarScanner.nextLine();

                switch (leaveChoice) 
                {
                    case 1:
                        printApplyAnnualLeave();
                        break;

                    case 2:
                        printApplyMedicalLeave();
                        break;

                    case 3:
                        return;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }

            } 
            catch (InputMismatchException inputMismatchException) 
            {
                System.out.println("Please enter a valid integer only!\n");
                calendarScanner.nextLine();
            }

        } while (leaveChoice != 9);
    }

    //APPLY FOR ANNUAL LEAVE
    public void printApplyAnnualLeave()
    {

        LocalDate date= inputForDateMonthTimeSlotView.viewWhichDate();
        // Apply for annual leave
        ApplyAnnualLeaveError errorType = calendarManager.applyAnnualLeave(date);
    
        // Handle different outcomes
        switch (errorType) 
        {
            case INSUFFICIENT_AL_DAYS:
                System.out.println("Sorry, you do not have enough annual leave days left.");
                break;
    
            case STAFF_SHORTAGE:
                System.out.println("Your leave request could not be approved due to staff shortages on the selected date.");
                break;
    
            case NO_ERROR:
                System.out.println("Your annual leave has been successfully applied.");
                break;
    
            default:
                break;
        }

            
    } 


    //APPLY FOR MEDICAL LEAVE
    public void printApplyMedicalLeave()
    {
        LocalDate date= inputForDateMonthTimeSlotView.viewWhichDate();
        calendarManager.applyMedicalLeave(date);
        System.out.println("Your medical leave has been successfully applied.");

    }

    //PRINT CANCEL LEAVE
    public void printCancelLeave()
    {
        int leaveChoice = 0;

        do
        {
            try
            {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                      Cancel Leave                          |
                        =============================================================
                        Please select an option:
                        (1) Cencel Annual Leave
                        (2) Cancel Medical Leave
                        (3) Go to previous page
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.println("Your Choice: ");
                leaveChoice = calendarScanner.nextInt();
                calendarScanner.nextLine();

                switch (leaveChoice) 
                {
                    case 1:
                        printCancelAnnualLeave();
                        break;

                    case 2:
                        printCancelMedicalLeave();
                        break;

                    case 3:
                        return;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }

            } 
            catch (InputMismatchException inputMismatchException) 
            {
                System.out.println("Please enter a valid integer only!\n");
                calendarScanner.nextLine();
            }

        } while (leaveChoice != 9);
    }

    //PRINT CANCEL ANNUAL LEAVE
    public void printCancelAnnualLeave()
    {
        LocalDate date= inputForDateMonthTimeSlotView.viewWhichDate();
        calendarManager.cancelAnnualLeave(date);
        System.out.println("Annual Leave has been successfully canceled.:");
                
    }

    //PRINT CANCEL MEDICAL LEAVE
    public void printCancelMedicalLeave()
    {
        LocalDate date= inputForDateMonthTimeSlotView.viewWhichDate();
        calendarManager.cancelAnnualLeave(date);
        System.out.println("Medical Leave has been successfully canceled.:");
    
    }

    
}