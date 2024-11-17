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

        
        int doctorChoice = 0;

        do
        {
            try
            {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        =============================================================
                        Please select an option:
                        (1) Accept Appointment
                        (2) Decline Appointment
                        (3) Go back to previous page
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.println("Your Choice: ");
                doctorChoice = doctorScanner.nextInt();
                doctorScanner.nextLine();

                switch (doctorChoice) 
                {
                    
                    case 1:
                        viewAcceptAppointment();
                        System.out.println("Note: Remember to update patient's Medical Record after the appointment!");
                        break;

                    case 2:
                        viewDeclineAppointment();
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
                doctorScanner.nextLine();
            }

        } while (doctorChoice != 9);

    }

    //VIEW PENDING ASSIGNMENTS
    public void viewPendingAppointments()
    {
        int viewBy= viewByDayOrMonth();
        List<Appointment> appointments;
        if(viewBy==1)
        {
            appointments=viewPendingAppointmentsByDay();
        }
        else
        {
            appointments=viewPendingAppointmentsByMonth();
        }

        if (appointments.isEmpty()) 
        {
            System.out.println("No pending appointments found for the selected date.");
        } 
        else 
        {
            // Print header
            String headerFormat = "| %-15s | %-12s |\n";
            String rowFormat = "| %-15s | %-12s |\n";
            System.out.println("+-----------------+--------------+");
            System.out.printf(headerFormat, "Time Slot", "Patient ID");
            System.out.println("+-----------------+--------------+");
    
            // Print each appointment
            for (Appointment appointment : appointments) {
                String timeSlot = appointment.getAppointmentTimeSlot().getStartTime() + " - " 
                                + appointment.getAppointmentTimeSlot().getEndTime();
                String patientID = appointment.getPatientID();
                System.out.printf(rowFormat, timeSlot, patientID);
            }
    
            // Footer line
            System.out.println("+-----------------+--------------+");
        }
    }

    //CHOOSE TO VIEW DAY OR MONTH
    public int viewByDayOrMonth()
    {
        int choice=-1;
        do
        {
            try
            {
                System.out.println("Do you want to view by (1) Date or (2) Month? ");
                choice= doctorScanner.nextInt();
                doctorScanner.nextLine();

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
                doctorScanner.nextLine();
            }
        }while(choice!=3);
        return 0; //never reach here
    }

    //VIEW PENDING APPOINTMENTS BY DAY
    public List<Appointment> viewPendingAppointmentsByDay()
    {
        boolean validInput= false;
        LocalDate date=null;
        do
        {
            try
            {
                System.out.print("Enter the date (dd/MM/yyyy): ");
                String dateInput = doctorScanner.nextLine().trim();
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

        List<Appointment> appointments= appointmentManager.getPendingAppointments(date);
        return appointments;
    }

    //VIEW PENDING APPOINTMENTS BY MONTH
    public List<Appointment> viewPendingAppointmentsByMonth()
    {
        boolean validInput= false;
        int month=-1;
        do
        {
            try
            {
                System.out.print("Enter the month(1 for Jan and 12 for Dec): ");
                month= doctorScanner.nextInt();
                doctorScanner.nextLine();
                validInput=true;
            }
            catch (Exception e) 
            {
                System.out.println("Invalid input for month.");
                validInput=false;
            }
        }while(!validInput);

        List<Appointment> appointments= appointmentManager.getPendingAppointments(month);
        return appointments;
    }



    public void viewAcceptAppointment() {
        System.out.println("To accept an appointment, please provide the following details:");
    
        // Get and validate the appointment date
        LocalDate date = null;
        boolean validInput=false;
        do{
            try {
                System.out.print("Enter the appointment date (dd/MM/yyyy):");
                String dateInput = doctorScanner.nextLine().trim();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateInput, dateFormatter);
                validInput=true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                validInput=false;
            }
        }while(!validInput);
        final LocalDate Date=date;

    
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
        final String StartTime=startTime;
    
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
        final String EndTime=endTime;
    
        // Get the patient ID
        System.out.print("Enter the patient ID: ");
        String patientID = doctorScanner.nextLine().trim();
    
        // Validate that the appointment exists in pending appointments
        List<Appointment> pendingAppointments = this.appointmentManager.getPendingAppointments();
    
        Optional<Appointment> appointmentOptional = pendingAppointments.stream()
                .filter(a -> a.getAppointmentDate().equals(Date) 
                        && a.getAppointmentTimeSlot().getStartTime().equals(StartTime)
                        && a.getAppointmentTimeSlot().getEndTime().equals(EndTime)
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
        LocalDate date = null;
        boolean validInput=false;
        do{
            try {
                System.out.print("Enter the appointment date (dd/MM/yyyy):");
                String dateInput = doctorScanner.nextLine().trim();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateInput, dateFormatter);
                validInput=true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                validInput=false;
            }
        }while(!validInput);
        final LocalDate Date=date;

    
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
        final String StartTime=startTime;
    
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
        final String EndTime=endTime;
    
        // Get the patient ID
        System.out.print("Enter the patient ID: ");
        String patientID = doctorScanner.nextLine().trim();
    
        // Validate that the appointment exists in pending appointments
        List<Appointment> pendingAppointments = this.appointmentManager.getPendingAppointments();
    
        Optional<Appointment> appointmentOptional = pendingAppointments.stream()
                .filter(a -> a.getAppointmentDate().equals(Date) 
                        && a.getAppointmentTimeSlot().getStartTime().equals(StartTime)
                        && a.getAppointmentTimeSlot().getEndTime().equals(EndTime)
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
