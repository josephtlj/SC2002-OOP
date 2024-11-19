package src.views;

import java.util.*;

import src.controllers.AppointmentOutcomeRecordController;
import src.models.Appointment;
import src.models.AppointmentOutcomeRecord;
import src.models.AppointmentTimeSlot;
import src.models.Session;

import java.time.*;

public class AppointmentOutcomeRecordView {
    // ATTRIBUTES
    Scanner scanner = new Scanner(System.in);
    InputForDateMonthTimeSlotView viewInputForDateMonthTimeSlot = new InputForDateMonthTimeSlotView();
    AppointmentView appointmentView = new AppointmentView();
    AppointmentOutcomeRecordController manager;

    // CONSTRUCTOR
    public AppointmentOutcomeRecordView(String doctorID) {
        this.manager = new AppointmentOutcomeRecordController(doctorID);
        printAppointmentOutcomeRecordView(doctorID);
    }

    // METHODS
    public void printAppointmentOutcomeRecordView(String doctorID) {
        int choice = -1;
        boolean validInput = false;

        do {
            try {
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
                validInput = true;

                switch (choice) {

                    case 1:
                        viewUpdateOutcomeRecord(doctorID);
                        break;

                    case 2:
                        return;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        validInput = false;
                        break;
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please enter a valid integer only!");
                validInput = false;
            }

        } while (!validInput);
    }

    public void viewUpdateOutcomeRecord(String doctorID) {

        AppointmentOutcomeRecord appointmentOutcomeRecord = viewFindOutcomeRecord(doctorID);

        if (appointmentOutcomeRecord == null) {
            System.out.println("Unable to find requested Appointment Outcome Record.");
            return;
        }

        System.out.println("To update the appointment outcome record, please provide the following details:");

        System.out.println("Enter service type:");
        String serviceType = scanner.nextLine();

        System.out.println("Enter consulation notes:");
        String notes = scanner.nextLine();

        boolean outcome = manager.updateOutcomeRecord(appointmentOutcomeRecord, serviceType, notes);
        if (outcome) {
            System.out.println("Appointment Outcome Record succcessfully updated.");
        } else {
            System.out.println("Unable to update Appointment Outcome Record.");
        }
    }

    public AppointmentOutcomeRecord viewFindOutcomeRecord(String ID) {

        viewCompletedAppointments(ID);

        System.out.println("To find the Appointment Outcome Record requested, please provide the following details:");

        System.out.print("Enter the patient ID: ");
        String patientID = scanner.nextLine().trim();

        this.manager = new AppointmentOutcomeRecordController(patientID);

        LocalDate date = viewInputForDateMonthTimeSlot.viewWhichDate();

        AppointmentTimeSlot timeSlot = viewInputForDateMonthTimeSlot.viewWhichTimeSlot();

        AppointmentOutcomeRecord outcomeRecord = manager.findOutcomeRecord(patientID, date, timeSlot);
        if (outcomeRecord == null) {
            System.out.println("Unable to find requested Appointment Outcome Record.");
        } else {
            System.out.println("Requested Appointment Outcome Record found.");
        }
        return outcomeRecord;

    }

    public void viewCompletedAppointments(String doctorID) {
        System.out
                .println("Before updating an Appointment Outcome Record(AOR), please view the completed appointments.");
        System.out.println("Note: You are ONLY allowed to update an AOR AFTER the scheduled appointment");

        int choice = viewInputForDateMonthTimeSlot.viewByDateOrMonth();

        List<Appointment> appointments = manager.getCompletedAppointments(doctorID);

        if (choice == 1) {
            appointmentView.printAppointmentOnADateView(appointments);

        } else {
            appointmentView.printAppointmentInAMonthView(appointments);
        }
    }
}