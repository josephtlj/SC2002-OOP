package appointment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PatientAppointmentView {
    private Scanner patientScanner = new Scanner(System.in);
    PatientAppointmentManager appointmentManager;

    public PatientAppointmentView(PatientAppointmentManager appointmentManager)
    {
        PatientAppointmentActionType actionType= appointmentManager.actionType;
        this.appointmentManager= appointmentManager;

        switch(actionType)
        {
            case VIEW:
                viewAvailableAppointments(); // with available doctors
                break;
            case SCHEDULE:
                scheduleAppointments();
                break;
            case RESCHEDULE:
                rescheduleAppointments();
                break;
            case CANCEL:
                cancelAppointments();
                break;
            case VIEW_SCHEDULED:
                viewScheduledAppointments();
                break;
            default:
                break;
        }
        return;
    }

    public int ViewChooseMonth() 
    {
        int month = 0;
        while (true) 
        {
            System.out.println("Please enter the number corresponding to the month (1 for January, 12 for December):");

            if (patientScanner.hasNextInt()) 
            {
                month = patientScanner.nextInt();
                patientScanner.nextLine(); // Clear the newline character

                // Validate input
                if (month >= 1 && month <= 12) 
                {
                    break;
                } 
                else 
                {
                    System.out.println("Invalid input. Please enter a number between 1 and 12.");
                }
            } 
            else 
            {
                System.out.println("Invalid input. Please enter a valid number.");
                patientScanner.nextLine(); // Clear invalid input
            }
        }
        return month;
    }

    public int ViewChooseDay(int month, int year) {
        int day = 0;
        int maxDays = getDaysInMonth(month, year); // Get the number of days in the given month and year

        while (true) {
            System.out.println("Please enter the day of the month (1 to " + maxDays + "): ");

            if (patientScanner.hasNextInt()) {
                day = patientScanner.nextInt();
                patientScanner.nextLine(); // Clear the newline character

                if (day >= 1 && day <= maxDays) {
                    break; // Valid input; exit the loop
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + maxDays + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                patientScanner.nextLine(); // Clear invalid input
            }
        }
        return day;
    }

    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31; // Months with 31 days
            case 4: case 6: case 9: case 11:
                return 30; // Months with 30 days
            case 2:
                return isLeapYear(year) ? 29 : 28; // February: Check for leap year
            default:
                return 0; // Invalid month
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public int viewByDayOrMonth()
    {
        int choice=-1;
        do
        {
            try
            {
                System.out.println("Do you want to view by (1) Date or (2) Month? ");
                choice= patientScanner.nextInt();
                patientScanner.nextLine();

                switch(choice)
                {
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    default:
                        System.out.println("Please enter a valid choice!");
                        break;
                }
            }
            catch (InputMismatchException inputMismatchException) 
            {
                System.out.println("Please enter a valid integer only!\n");
                patientScanner.nextLine();
            }
        }while(choice!=3);
        return 0; //never reach here
    }

    public List<Appointment> viewAvailableAppointmentsByDay()
    {
        boolean validInput= false;
        LocalDate date=null;
        do
        {
            try
            {
                System.out.print("Enter the date (dd/MM/yyyy): ");
                String dateInput = patientScanner.nextLine().trim();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateInput, dateFormatter);
                validInput=true;
            }
            catch (Exception e) 
            {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                validInput=false;
            }
        }while(!validInput);

        List<Appointment> appointments= appointmentManager.getAvailableAppointments(date);
        return appointments;
    }

    public List<Appointment> viewAvailableAppointmentsByMonth()
    {
        boolean validInput= false;
        int month=-1;
        do
        {
            try
            {
                System.out.print("Enter the month(1 for Jan and 12 for Dec): ");
                month= patientScanner.nextInt();
                patientScanner.nextLine();
                validInput=true;
            }
            catch (Exception e) 
            {
                System.out.println("Invalid input for month.");
                validInput=false;
            }
        }while(!validInput);

        List<Appointment> appointments= appointmentManager.getAvailableAppointments(month);
        return appointments;
    }










    public void viewAvailableAppointments() 
    {
        
    }

    public void scheduleAppointment(){

    }

    public void rescheduleAppointment(){

    }

    public void cancelAppointment(){

    }

    public void viewScheduledAppointment(){

    }
}
