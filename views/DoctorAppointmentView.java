package views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Enum.DoctorAppointmentActionType;
import controllers.DoctorAppointmentController;
import interfaces.DoctorAppointmentViewInterface;
import models.Appointment;
import models.AppointmentTimeSlot;

public class DoctorAppointmentView implements DoctorAppointmentViewInterface
{
    //ATTRIBUTES
    private Scanner doctorScanner = new Scanner(System.in);
    DoctorAppointmentController appointmentManager;
    InputForDateMonthTimeSlotView inputForDateMonthTimeSlotView= new InputForDateMonthTimeSlotView();
    AppointmentView appointmentView = new AppointmentView();
    DoctorAppointmentActionType actionType;

    //CONSTRUCTOR
    public DoctorAppointmentView(String doctorID, DoctorAppointmentActionType actionType)
    {
        this.actionType=actionType;
        this.appointmentManager= new DoctorAppointmentController(doctorID, actionType);

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
        int viewBy= inputForDateMonthTimeSlotView.viewByDateOrMonth();
        if(viewBy==1)
        {
            viewPendingAppointmentsByDay();
        }
        else
        {
            viewPendingAppointmentsByMonth();
        }
    }


    //VIEW PENDING APPOINTMENTS BY DAY
    public void viewPendingAppointmentsByDay()
    {
        LocalDate date=inputForDateMonthTimeSlotView.viewWhichDate();

        List<Appointment> appointments= appointmentManager.getPendingAppointments(date);

        appointmentView.printAppointmentOnADateView(appointments);

    }

    //VIEW PENDING APPOINTMENTS BY MONTH
    public void viewPendingAppointmentsByMonth()
    {
        int month=inputForDateMonthTimeSlotView.viewWhichMonth();

        List<Appointment> appointments= appointmentManager.getPendingAppointments(month);

        appointmentView.printAppointmentInAMonthView(appointments);
    }



    public void viewAcceptAppointment() {
        System.out.println("To accept an appointment, please provide the following details:");
    
        LocalDate date= inputForDateMonthTimeSlotView.viewWhichDate();

    
        AppointmentTimeSlot timeSlot=inputForDateMonthTimeSlotView.viewWhichTimeSlot();
    
        // Get the patient ID
        System.out.print("Enter the patient ID: ");
        String patientID = doctorScanner.nextLine().trim();
    
        List<Appointment> pendingAppointments = this.appointmentManager.getPendingAppointments();
        
        boolean appointmentFound=appointmentManager.checkIfAppointmentInAppointments(pendingAppointments, patientID, date, timeSlot);

        if(appointmentFound)
        {
            System.out.println("Appointment found.");
        }
        else
        {
            System.out.println("Appointment not found.");
            System.out.println("Unable to accept appointment.");
            return;
        }

        boolean outcome= appointmentManager.updateAppointmentStatus("Confirmed",date,timeSlot,patientID);

        if(outcome)
        {
            System.out.println("Appointment has been successfully accepted.");
        }
        else
        {
            System.out.println("Unable to accept appointment.");
        }

        
    }
    
    

    //VIEW FOR DECLINING APPOINTMENT
    public void viewDeclineAppointment()
    {
        System.out.println("To decline an appointment, please provide the following details:");

        LocalDate date= inputForDateMonthTimeSlotView.viewWhichDate();

    
        AppointmentTimeSlot timeSlot=inputForDateMonthTimeSlotView.viewWhichTimeSlot();
    
        // Get the patient ID
        System.out.print("Enter the patient ID: ");
        String patientID = doctorScanner.nextLine().trim();
    
        List<Appointment> pendingAppointments = this.appointmentManager.getPendingAppointments();
        
        boolean appointmentFound=appointmentManager.checkIfAppointmentInAppointments(pendingAppointments, patientID, date, timeSlot);

        if(appointmentFound)
        {
            System.out.println("Appointment found.");
        }
        else
        {
            System.out.println("Appointment not found.");
            System.out.println("Unable to decline appointment.");
            return;
        }

        boolean outcome= appointmentManager.updateAppointmentStatus("Declined",date,timeSlot,patientID);

        if(outcome)
        {
            System.out.println("Appointment has been successfully declined.");
            System.out.println(String.format("Your appointment availability on %s has been updated to Available.", date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            System.out.println("If you wish to change your availability, please go back to the Homepage and Set Availability.");
        }
        else
        {
            System.out.println("Unable to decline appointment.");
        }

    }
    


    // VIEW UPCOMING CONFIRMED APPOINTMENTS
    public void viewUpcomingAppointments() 
    {
        int choice=inputForDateMonthTimeSlotView.viewByDateOrMonth();

        if(choice==1)
        {
            LocalDate date=inputForDateMonthTimeSlotView.viewWhichDate();
            List<Appointment> appointments=appointmentManager.getConfirmedAppointments(date);
            if(appointments.isEmpty())
            {
                System.out.println("There are no confirmed appointments on the chosen date.");
            }
            appointmentView.printAppointmentOnADateView(appointments);
        }
        else
        {
            int month=inputForDateMonthTimeSlotView.viewWhichMonth();
            List<Appointment> appointments=appointmentManager.getConfirmedAppointments(month);
            if(appointments.isEmpty())
            {
                System.out.println("There are no confirmed appointments in the chosen month.");
            }
            appointmentView.printAppointmentInAMonthView(appointments);
        }

    }


    // VIEW FOR SETTING AVAILABILITY FOR APPOINTMENTS
    public void viewSetAvailabilityForAppointments() 
    {
        LocalDate date=inputForDateMonthTimeSlotView.viewWhichDate();
        boolean isInCurrentYear=appointmentManager.isInCurrentYear(date);

        if(!isInCurrentYear)
        {
            System.out.println("Invalid date entered. Date must be in current year.");
            return;
        }

        boolean validInput=false;
        int availability=-1;
        do
        {
            try
            {
                System.out.println("Enter availability (1) Yes or (2) No:");
                availability= doctorScanner.nextInt();

                if(availability!=1 && availability!=2)
                {
                    System.out.println("Invalid choice.");
                    validInput=false;
                }
                else
                {
                    validInput=true;
                }
            }
            catch (Exception e) 
            {
                System.out.println("Invalid input for month.");
                validInput=false;
            }
        }while(!validInput);


        boolean success = this.appointmentManager.updateAppointmentAvailability(date, availability);
        if (success) 
        {
            System.out.println("Availability successfully updated.");
        } 
        else 
        {
            System.out.println("Unable to update availability.");
        }
    }

}
