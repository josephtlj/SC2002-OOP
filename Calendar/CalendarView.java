package Calendar;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CalendarView 
{
    //ATTRIBUTES
    private Scanner calendarScanner = new Scanner(System.in);
    private CalendarManager calendarManager;

    //CONSTRUCTOR
    public CalendarView(CalendarManager calendarManager)
    {
        this.calendarManager= calendarManager;
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
        System.out.println("Would you like to view a specific date or an entire month?");
        System.out.println("1: View a specific date");
        System.out.println("2: View an entire month");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = -1;
        while (choice != 1 && choice != 2) {
            try {
                choice = Integer.parseInt(calendarScanner.nextLine());
                if (choice != 1 && choice != 2) {
                    System.out.print("Invalid choice. Please enter 1 or 2: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter 1 or 2: ");
            }
        }

        if (choice == 1) {
            System.out.print("Enter the date (yyyy-MM-dd): ");
            try {
                String inputDate = calendarScanner.nextLine();
                LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                printCalendarDate(date);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format. Please try again.");
            }
        } else if (choice == 2) {
            System.out.print("Enter the month (1 for January, 12 for December): ");
            try {
                int month = Integer.parseInt(calendarScanner.nextLine());
                if (month < 1 || month > 12) {
                    System.out.println("❌ Invalid month. Please enter a value between 1 and 12.");
                } else {
                    printCalendarMonth(month);
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number between 1 and 12.");
            }
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
        LocalDate date = null;
        boolean validInput = false;

        do {
            try {
                System.out.println("Enter the date you want to apply for annual leave (yyyy-MM-dd):");
                String input = calendarScanner.nextLine();
    
                // Validate and parse input date
                date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                validInput = true;
    
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
                        validInput = false; // Exit loop after successful application
                        break;
    
                    default:
                        break;
                }
            } 
            catch (DateTimeParseException e) 
            {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            }
            catch (Exception e) 
            {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } while (validInput);

    }

    //APPLY FOR MEDICAL LEAVE
    public void printApplyMedicalLeave()
    {
        LocalDate date = null;
        boolean validInput = false;

        do {
            try {
                System.out.println("Enter the date you want to apply for medical leave (yyyy-MM-dd):");
                String input = calendarScanner.nextLine();
    
                // Validate and parse input date
                date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                validInput = true;
    
                // Apply for annual leave
                calendarManager.applyMedicalLeave(date);
    
                System.out.println("Your medical leave has been successfully applied.");
                validInput= false;
            } 
            catch (DateTimeParseException e) 
            {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            }
            catch (Exception e) 
            {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } while (validInput);
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
        LocalDate date = null;
        boolean validInput = false;

        do {
            try 
            {
                System.out.println("Enter the date you want to cancel annual leave (yyyy-MM-dd):");
                String input = calendarScanner.nextLine();
    
                // Validate and parse input date
                date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                validInput = true;

                calendarManager.cancelAnnualLeave(date);
                System.out.println("Annual Leave has been successfully canceled.:");
                validInput= false;
    
            }    
            catch (DateTimeParseException e) 
            {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            }
            catch (Exception e) 
            {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } while (validInput);
    }

    //PRINT CANCEL MEDICAL LEAVE
    public void printCancelMedicalLeave()
    {
        LocalDate date = null;
        boolean validInput = false;

        do {
            try 
            {
                System.out.println("Enter the date you want to cancel medical leave (yyyy-MM-dd):");
                String input = calendarScanner.nextLine();
    
                // Validate and parse input date
                date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                validInput = true;

                calendarManager.cancelAnnualLeave(date);
                System.out.println("Medical Leave has been successfully canceled.:");
                validInput= false;
    
            }    
            catch (DateTimeParseException e) 
            {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            }
            catch (Exception e) 
            {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } while (validInput);
    }

    
}
