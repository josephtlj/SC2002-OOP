package Doctor.Appointment;

import java.time.LocalDate;
import java.util.*;

public class DoctorAppointmentView 
{
    //ATTRIBUTES
    private Scanner doctorScanner = new Scanner(System.in);

    //CONSTRUCTOR
    public DoctorAppointmentView(DoctorAppointmentManager appointmentManager)
    {
        DoctorAppointmentActionType actionType= appointmentManager.actionType;

        switch(actionType)
        {
            case VIEW:
                viewUpcomingAppointments(appointmentManager);
                break;
            case SET_AVAILABILITY:
                viewSetAvailabilityForAppointments(appointmentManager);
                break;
            case ACCEPT_OR_DECLINE:
                viewUpcomingAppointments(appointmentManager);
                break;
            default:
                break;
        }
        return;
    }

    //VIEW METHODS FOR EACH TYPE OF APPOINTMENT ACTION

    // VIEW FOR CHOOSING MONTH TO DISPLAY
    public int ViewChooseMonth() 
    {
        int month = 0;
        while (true) 
        {
            System.out.println("Please enter the number corresponding to the month (1 for January, 12 for December):");

            if (doctorScanner.hasNextInt()) 
            {
                month = doctorScanner.nextInt();
                doctorScanner.nextLine(); // Clear the newline character

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
                doctorScanner.nextLine(); // Clear invalid input
            }
        }
        return month;
    }

    //VIEW FOR CHOOSING DAY TO DISPLAY
    


    // VIEW UPCOMING CONFIRMED APPOINTMENTS
    public void viewUpcomingAppointments(DoctorAppointmentManager appointmentManager) 
    {
        System.out.println("Do you want to view upcoming appointments in a day(1) or month(2)?");
        //CODE IMPLEMENTATION FOR READING USER INPUT AND VALIDATING IT AND CALLING EITHER ViewChooseMonth() or ViewChooseDay()

        //CHANGE BELOW CODE TO FACTOR IN CHANGES
        int month = ViewChooseMonth();

        // Filter confirmed appointments by the selected month
        List<Appointment> confirmedAppointments = appointmentManager.getConfirmedAppointments();
        List<Appointment> filteredAppointments = new ArrayList<>();

        for (Appointment appointment : confirmedAppointments) 
        {
            if (appointment.getAppointmentDate().getMonthValue() == month) 
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


    // VIEW FOR SETTING AVAILABILITY FOR APPOINTMENTS
    public void viewSetAvailabilityForAppointments(DoctorAppointmentManager appointmentManager) 
    {
        System.out.println("Enter the date for which you want to set availability (yyyy-MM-dd):");
        String dateInput = doctorScanner.nextLine();
        LocalDate date;

        try 
        {
         date = LocalDate.parse(dateInput);
        } 
        catch (Exception e) 
        {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

        System.out.println("Enter availability (Yes or No):");
        String availability = doctorScanner.nextLine().trim();

        if (!availability.equalsIgnoreCase("Yes") && !availability.equalsIgnoreCase("No")) 
        {
            System.out.println("Invalid input for availability. Please enter 'Yes' or 'No'.");
            return;
        }

        boolean success = appointmentManager.updateAppointmentAvailability(date, availability);
        if (success) 
        {
            System.out.println("Availability successfully updated.");
        } 
        else 
        {
            System.out.println("Failed to update availability. Please try again.");
        }
    }

}
