package Doctor.Appointment;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import Doctor.Appointment.AppointmentTimeSlot;

public class AppointmentOutcomeRecordView 
{
    //ATTRIBUTES
    Scanner scanner= new Scanner(System.in);
    AppointmentOutcomeRecordManager manager;

    //CONSTRUCTOR
    public AppointmentOutcomeRecordView(AppointmentOutcomeRecordManager manager, String ID)
    {
        this.manager=manager;
        printView(ID);
    }

    //METHODS
    public void printView(String ID)
    {
        int choice = -1;
        boolean validInput=false;

        do
        {
            try
            {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |             Update Appointment Outcome Record             |
                        =============================================================
                        Please note that the Appointment Outcome Record can ONLY be
                        updated after the scheduled Appointment.
                        Do you wish to proceed?

                        Please select an option:
                        (1) Yes
                        (2) Go back to previous page
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.println("Your Choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                validInput=true;

                switch (choice) 
                {
                    
                    case 1:
                        AppointmentOutcomeRecord outcomeRecord=viewFindOutcomeRecord(ID);
                        viewUpdateOutcomeRecord(outcomeRecord);
                        break;

                    case 2:
                       return;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }

            } 
            catch (InputMismatchException inputMismatchException) 
            {
                System.out.println("Please enter a valid integer only!");
                validInput=false;
            }

        } while (!validInput);
    }

    public void viewUpdateOutcomeRecord(AppointmentOutcomeRecord outcomeRecord)
    {
        System.out.println("To update the appointment outcome record, please provide the following details:");

        System.out.println("Enter service type:");
        String serviceType= scanner.nextLine();

        System.out.println("Enter consulation notes:");
        String notes= scanner.nextLine();

        manager.updateOutcomeRecord(outcomeRecord,serviceType,notes);
    }

    public AppointmentOutcomeRecord viewFindOutcomeRecord(String ID)
    {

        viewCompletedAppointments(ID);

        System.out.println("To update an appointment outcome record, please provide the following details:");
    
        // Get and validate the appointment date
        LocalDate date = null;
        boolean validInput = false;
        do {
            try {
                System.out.print("Enter the appointment date (dd/MM/yyyy): ");
                String dateInput = scanner.nextLine().trim();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateInput, dateFormatter);

                // Validate that the date is before 16/11/2024
                LocalDate cutoffDate = LocalDate.of(2024, 11, 16);
                if (date.isBefore(cutoffDate)) {
                    validInput = true; // Date is valid
                } else {
                    System.out.println("The date must be before 16/11/2024. Please try again.");
                    validInput = false;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                validInput = false;
            }
        } while (!validInput);

        final LocalDate validatedDate = date;

    
        // Get and validate the start time
        String startTime = null;
        while (startTime == null) {
            System.out.print("Enter the time slot start time (HH:mm): ");
            String startTimeInput = scanner.nextLine().trim();
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
            String endTimeInput = scanner.nextLine().trim();
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
        String patientID = scanner.nextLine().trim();
    
        // Validate that the appointment exists in pending appointments
        List<Appointment> completedAppointments = this.manager.getCompletedAppointments(ID);
    
        Optional<Appointment> appointmentOptional = completedAppointments.stream()
                .filter(a -> a.getAppointmentDate().equals(validatedDate) 
                        && a.getAppointmentTimeSlot().getStartTime().equals(StartTime)
                        && a.getAppointmentTimeSlot().getEndTime().equals(EndTime)
                        && a.getPatientID().equals(patientID))
                .findFirst();
    
        if (appointmentOptional.isEmpty()) {
            System.out.println("No matching appointment found in completed appointments. Please check your input.");
            return null;
        }

        AppointmentTimeSlot timeSlot= new AppointmentTimeSlot(StartTime, EndTime);

        AppointmentOutcomeRecord outcomeRecord= manager.findOutcomeRecord(patientID, validatedDate, null);
        return outcomeRecord;

    }

    public void viewCompletedAppointments(String ID)
    {
        List<Appointment> appointments= manager.getCompletedAppointments(ID);
        int choice=viewByDayOrMonth();
        if(choice==1)
        {
            viewByDay(appointments);
        }
        else
        {
            viewByMonth(appointments);
        }

    }

    public void viewByDay(List<Appointment> appointments)
    {
        boolean validInput= false;
        LocalDate date=null;
        do
        {
            try
            {
                System.out.print("Enter the date (dd/MM/yyyy): ");
                String dateInput = scanner.nextLine().trim();
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

        List<Appointment> sievedAppointments= manager.getCompletedAppointmentsInDay(date, appointments);

        if (sievedAppointments.isEmpty()) 
        {
            System.out.println("No completed appointments found for the selected date.");
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
            for (Appointment appointment : sievedAppointments) {
                String timeSlot = appointment.getAppointmentTimeSlot().getStartTime() + " - " 
                                + appointment.getAppointmentTimeSlot().getEndTime();
                String patientID = appointment.getPatientID();
                System.out.printf(rowFormat, timeSlot, patientID);
            }
    
            // Footer line
            System.out.println("+-----------------+--------------+");
        }


    }

    public void viewByMonth(List<Appointment> appointments)
    {
        boolean validInput= false;
        int month=-1;
        do
        {
            try
            {
                System.out.print("Enter the month(1 for Jan and 12 for Dec): ");
                month= scanner.nextInt();
                scanner.nextLine();
                validInput=true;
            }
            catch (Exception e) 
            {
                System.out.println("Invalid input for month.");
                validInput=false;
            }
        }while(!validInput);

        List<Appointment> sievedAppointments= manager.getCompletedAppointmentsInMonth(month, appointments);

        if (sievedAppointments.isEmpty()) 
        {
            System.out.println("No completed appointments found for the selected month.");
        } 
        else 
        {
            //Print header
            String headerFormat = "| %-15s | %-21s | %-15s |\n";
            String rowFormat = "| %-15s | %-21s | %-15s |\n";

            System.out.println("+-----------------+-----------------------+-----------------+");
            System.out.printf(headerFormat, "Date", "Time Slot", "Patient ID");
            System.out.println("+-----------------+-----------------------+-----------------+");

            // Print each appointment
            for (Appointment appointment : sievedAppointments) {
            String date = appointment.getAppointmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String timeSlot = appointment.getAppointmentTimeSlot().getStartTime() + " - " 
                        + appointment.getAppointmentTimeSlot().getEndTime();
            String patientID = appointment.getPatientID();
            System.out.printf(rowFormat, date, timeSlot, patientID);
            }

            // Footer line
            System.out.println("+-----------------+-----------------------+-----------------+");
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
                choice= scanner.nextInt();
                scanner.nextLine();

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
                scanner.nextLine();
            }
        }while(choice!=3);
        return 0; //never reach here
    }
}
