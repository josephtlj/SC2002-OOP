package appointment;

import java.time.LocalDate;
import java.util.*;

public class PatientAppointmentView {
    private Scanner patientScanner = new Scanner(System.in);

    public PatientAppointmentView(PatientAppointmentManager appointmentManager)
    {
        PatientAppointmentActionType actionType= appointmentManager.actionType;

        switch(actionType)
        {
            case VIEW:
                viewAvailableAppointments(appointmentManager);
                break;
            case SCHEDULE:
                scheduleAppointments(appointmentManager);
                break;
            case RESCHEDULE:
                rescheduleAppointments(appointmentManager);
                break;
            case CANCEL:
                cancelAppointments(appointmentManager);
                break;
            case VIEW_SCHEDULED:
                viewScheduledAppointments(appointmentManager);
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

    public void viewAvailableAppointments(PatientAppointmentManager appointmentManager) 
    {
        System.out.println("Do you want to view available appointments in a day(1) or month(2)?");
        //CODE IMPLEMENTATION FOR READING USER INPUT AND VALIDATING IT AND CALLING EITHER ViewChooseMonth() or ViewChooseDay()

        //CHANGE BELOW CODE TO FACTOR IN CHANGES
        int month = ViewChooseMonth();

        // Filter available appointments by the selected month
        List<Appointment> availableAppointments = appointmentManager.getAvailableAppointments();
        List<Appointment> filteredAppointments = new ArrayList<>();

        for (Appointment appointment : availableAppointments) 
        {
            if (appointment.getAppointmentAvailability().getMonthValue() == month) 
            {
                filteredAppointments.add(appointment);
            }
        }

        // Sort appointments by date and time for better organization
        filteredAppointments.sort(Comparator.comparing(Appointment::getAppointmentDate)
                                            .thenComparing(a -> a.getAppointmentTimeSlot().getStartTime()));

        // Print header
        String headerFormat = "| %-12s | %-15s | %-12s |\n";
        String rowFormat = "| %-12s | %-15s | %-12s |\n";

        System.out.println("+--------------+-----------------+--------------+");
        System.out.printf(headerFormat, "Date", "Time Slot", "Patient ID");
        System.out.println("+--------------+-----------------+--------------+");

        // Print each appointment
        for (Appointment appointment : filteredAppointments) 
        {
            LocalDate date = appointment.getAppointmentDate();
            String timeSlot = appointment.getAppointmentTimeSlot().getStartTime() + " - " 
                            + appointment.getAppointmentTimeSlot().getEndTime();
            String patientID = appointment.getPatientID();

            System.out.printf(rowFormat, date, timeSlot, patientID);
        }

        // Footer line
        System.out.println("+--------------+-----------------+--------------+");

        if (filteredAppointments.isEmpty()) 
        {
            System.out.println("No confirmed appointments found for the selected month.");
        }
    }

    public void scheduleAppointment(PatientAppointmentManager appointmentManager){

    }

    public void rescheduleAppointment(PatientAppointmentManager appointmentManager){

    }

    public void cancelAppointment(PatientAppointmentManager appointmentManager){

    }

    public void viewScheduledAppointment(PatientAppointmentManager appointmentManager){

    }
}
