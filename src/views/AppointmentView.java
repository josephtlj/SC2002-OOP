package src.views;

import java.time.format.DateTimeFormatter;
import java.util.*;

import src.interfaces.AppointmentViewInterface;
import src.models.Appointment;
import java.time.*;

public class AppointmentView implements AppointmentViewInterface {

    
    /** 
     * @param appointments
     */
    public void printAppointmentOnADateView(List<Appointment> appointments) {
        // Print header
        String headerFormat = "| %-15s | %-21s | %-15s |\n";
        String rowFormat = "| %-15s | %-21s | %-15s |\n";

        System.out.println("+-----------------+-----------------------+-----------------+");
        System.out.printf(headerFormat, "Date", "Time Slot", "Patient ID");
        System.out.println("+-----------------+-----------------------+-----------------+");

        // Print only the appointments on the specified date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Appointment appointment : appointments) {
            String formattedDate = appointment.getAppointmentDate().format(formatter);
            String timeSlot = appointment.getAppointmentTimeSlot().getStartTime() + " - "
                    + appointment.getAppointmentTimeSlot().getEndTime();
            String patientID = appointment.getPatientID();
            System.out.printf(rowFormat, formattedDate, timeSlot, patientID);
        }
        // Footer line
        System.out.println("+-----------------+-----------------------+-----------------+");
    }

    public void printAppointmentInAMonthView(List<Appointment> appointments) {
        // Print header
        String headerFormat = "| %-15s | %-21s | %-15s |\n";
        String rowFormat = "| %-15s | %-21s | %-15s |\n";

        System.out.println("+-----------------+-----------------------+-----------------+");
        System.out.printf(headerFormat, "Date", "Time Slot", "Patient ID");
        System.out.println("+-----------------+-----------------------+-----------------+");

        // Print only the appointments in the specified month
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Appointment appointment : appointments) {
            String formattedDate = appointment.getAppointmentDate().format(formatter);
            String timeSlot = appointment.getAppointmentTimeSlot().getStartTime() + " - "
                    + appointment.getAppointmentTimeSlot().getEndTime();
            String patientID = appointment.getPatientID();
            System.out.printf(rowFormat, formattedDate, timeSlot, patientID);
        }

        // Footer line
        System.out.println("+-----------------+-----------------------+-----------------+");
    }
}