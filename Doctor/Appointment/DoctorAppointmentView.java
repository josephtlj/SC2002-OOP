package Doctor.Appointment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DoctorAppointmentView 
{
    //ATTRIBUTES
    private Scanner doctorScanner = new Scanner(System.in);
    DoctorAppointmentManager appointmentManager;

    //CONSTRUCTOR
    public DoctorAppointmentView(DoctorAppointmentManager appointmentManager)
    {
        DoctorAppointmentActionType actionType= appointmentManager.actionType;
        this.appointmentManager= appointmentManager;

        switch(actionType)
        {
            case VIEW:
                viewUpcomingAppointments();
                break;
            case SET_AVAILABILITY:
                viewSetAvailabilityForAppointments();
                break;
            case ACCEPT_OR_DECLINE:
                viewAcceptOrDeclineAppointments();
                break;
            default:
                break;
        }
        return;
    }

    //VIEW METHODS FOR EACH TYPE OF APPOINTMENT ACTION

    //VIEW FOR ACCEPTING OR DECLINING APPOINTMENT
    public void viewAcceptOrDeclineAppointments()
    {
        System.out.println("Please view pending appointments before accepting or declining them.");
        viewPendingAppointments();

        //INPUT VALIDATION
        //REDIRECT TO CORRECT METHOD AFTER THAT

        System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        =============================================================
                        Please select an option:
                        (1) Accept Appointment
                        (2) Decline Appointment
                        (3) Go back to previous page
                        """);

    }

    //VIEW PENDING ASSIGNMENTS
    public void viewPendingAppointments()
    {
        System.out.println("Do you want to view upcoming appointments in a day(1) or month(2)?");
        //CODE IMPLEMENTATION FOR READING USER INPUT AND VALIDATING IT AND CALLING EITHER ViewChooseMonth() or ViewChooseDay()

        
        int month = ViewChooseMonth();

        // Filter confirmed appointments by the selected month
        List<Appointment> pendingAppointments = this.appointmentManager.getPendingAppointments();
        List<Appointment> filteredAppointments = new ArrayList<>();

        for (Appointment appointment : pendingAppointments) 
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
            System.out.println("No pending appointments found for the selected month.");
        }
    }

    public void viewAcceptAppointment() {
        System.out.println("To accept an appointment, please provide the following details:");
    
        // Get and validate the appointment date
        LocalDate date = null;
        while (date == null) {
            System.out.print("Enter the appointment date (dd/MM/yyyy): ");
            String dateInput = doctorScanner.nextLine().trim();
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateInput, dateFormatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }
    
        // Get and validate the start time
        String startTime = null;
        while (startTime == null) {
            System.out.print("Enter the time slot start time (HH:mm): ");
            String startTimeInput = doctorScanner.nextLine().trim();
            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                startTime = timeFormatter.format(timeFormatter.parse(startTimeInput));
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:mm.");
            }
        }
    
        // Get and validate the end time
        String endTime = null;
        while (endTime == null) {
            System.out.print("Enter the time slot end time (HH:mm): ");
            String endTimeInput = doctorScanner.nextLine().trim();
            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                endTime = timeFormatter.format(timeFormatter.parse(endTimeInput));
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:mm.");
            }
        }
    
        // Get the patient ID
        System.out.print("Enter the patient ID: ");
        String patientID = doctorScanner.nextLine().trim();
    
        // Validate that the appointment exists in pending appointments
        List<Appointment> pendingAppointments = this.appointmentManager.getPendingAppointments();
    
        Optional<Appointment> appointmentOptional = pendingAppointments.stream()
                .filter(a -> a.getAppointmentDate().equals(date) // Ensure this matches
                        && a.getAppointmentTimeSlot().getStartTime().equals(startTime)
                        && a.getAppointmentTimeSlot().getEndTime().equals(endTime)
                        && a.getPatientID().equals(patientID))
                .findFirst();
    
        if (appointmentOptional.isEmpty()) {
            System.out.println("No matching appointment found in pending appointments. Please check your input.");
            return;
        }
    
        // Update the appointment status
        Appointment appointment = appointmentOptional.get();
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointmentManager.updateAppointmentStatus(appointment);
    
        System.out.println("Appointment successfully accepted.");
    }
    
    

    //VIEW FOR DECLINING APPOINTMENT
    public void viewDeclineAppointment()
    {
        System.out.println("To decline an appointment, please provide the following details:");

        // Get and validate the appointment date
        System.out.print("Enter the appointment date (dd/MM/yyyy): ");
        String dateInput = doctorScanner.nextLine().trim();
        LocalDate date;
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date = LocalDate.parse(dateInput, dateFormatter);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

    // Get and validate the start time
    System.out.print("Enter the time slot start time (HH:mm): ");
    String startTimeInput = doctorScanner.nextLine().trim();
    String startTime;
    try {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        startTime = timeFormatter.format(timeFormatter.parse(startTimeInput));
    } catch (Exception e) {
        System.out.println("Invalid time format for start time. Please use HH:mm format.");
        return;
    }

    // Get and validate the end time
    System.out.print("Enter the time slot end time (HH:mm): ");
    String endTimeInput = doctorScanner.nextLine().trim();
    String endTime;
    try {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        endTime = timeFormatter.format(timeFormatter.parse(endTimeInput));
    } catch (Exception e) {
        System.out.println("Invalid time format for end time. Please use HH:mm format.");
        return;
    }

    // Get the patient ID
    System.out.print("Enter the patient ID: ");
    String patientID = doctorScanner.nextLine().trim();

    // Validate that the appointment exists in pending appointments
    List<Appointment> pendingAppointments = this.appointmentManager.getPendingAppointments();
    Optional<Appointment> appointmentOptional = pendingAppointments.stream()
            .filter(a -> a.getAppointmentDate().equals(date)
                    && a.getAppointmentTimeSlot().getStartTime().equals(startTime)
                    && a.getAppointmentTimeSlot().getEndTime().equals(endTime)
                    && a.getPatientID().equals(patientID))
            .findFirst();

    if (appointmentOptional.isEmpty()) {
        System.out.println("No matching appointment found in pending appointments. Please check your input.");
        return;
    }

    // Update the appointment status
    Appointment appointment = appointmentOptional.get();
    appointment.setAppointmentStatus(AppointmentStatus.DECLINED);
    appointmentManager.updateAppointmentStatus(appointment);

    System.out.println("Appointment successfully declined.");
    }

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
    public void viewUpcomingAppointments() 
    {
        System.out.println("Do you want to view upcoming appointments in a day(1) or month(2)?");
        //CODE IMPLEMENTATION FOR READING USER INPUT AND VALIDATING IT AND CALLING EITHER ViewChooseMonth() or ViewChooseDay()

        //CHANGE BELOW CODE TO FACTOR IN CHANGES
        int month = ViewChooseMonth();

        // Filter confirmed appointments by the selected month
        List<Appointment> confirmedAppointments = this.appointmentManager.getConfirmedAppointments();
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
    public void viewSetAvailabilityForAppointments() 
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

        boolean success = this.appointmentManager.updateAppointmentAvailability(date, availability);
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
