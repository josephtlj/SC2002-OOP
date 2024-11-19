package src.controllers;

import src.models.Appointment;
import src.models.AppointmentTimeSlot;

import java.util.*;

import src.utils.ENUM.AppointmentStatus;
import src.utils.ENUM.DoctorAppointmentActionType;
import src.services.DoctorAppointmentServices;

import java.time.*;

public class DoctorAppointmentController {
    // ATTRIBUTES
    DoctorAppointmentActionType actionType;
    List<Appointment> appointments;
    DoctorAppointmentServices doctorAppointmentServices;
    String doctorID;

    // CONSTRUCTOR
    public DoctorAppointmentController(String doctorID, DoctorAppointmentActionType actionType) {
        this.doctorAppointmentServices = new DoctorAppointmentServices(doctorID);
        this.actionType = actionType;
        this.appointments = getAllAppointments(doctorID);
        this.doctorID = doctorID;

    }

    public DoctorAppointmentActionType getActionType() {
        return actionType;
    }

    // RETRIEVE ALL APPOINTMENTS FROM DATABASE
    public List<Appointment> getAllAppointments(String doctorID) {
        return doctorAppointmentServices.getAllAppointments(doctorID);
    }

    // CHECK IF APPOINTMENT IS IN LIST OF SIEVED (PENDING OR CONFIRMED ETC)
    // APPOINTMENTS
    public boolean checkIfAppointmentInAppointments(List<Appointment> appointments, String patientID, LocalDate date,
            AppointmentTimeSlot timeSlot) {
        return doctorAppointmentServices.checkIfAppointmentInAppointments(appointments, patientID, date, timeSlot);
    }

    // RETRIEVE CONFIRMED APPOINTMENTS
    public List<Appointment> getConfirmedAppointments() {
        List<Appointment> confirmedAppointments = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                confirmedAppointments.add(appointment);
            }
        }

        return confirmedAppointments;
    }

    public List<Appointment> getConfirmedAppointments(LocalDate date) {
        List<Appointment> pendingAppointments = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED
                    && appointment.getAppointmentDate().equals(date)) {

                pendingAppointments.add(appointment);
            }
        }
        return pendingAppointments;
    }

    public List<Appointment> getConfirmedAppointments(int month) {
        List<Appointment> pendingAppointments = new ArrayList<>();  
        
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED
                    && appointment.getAppointmentDate().getMonthValue() == month) {
                
                pendingAppointments.add(appointment);
            }
        }

        return pendingAppointments;
    }

    // RETRIEVE PENDING APPOINTMENTS
    public List<Appointment> getPendingAppointments() {
        List<Appointment> pendingAppointments = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                pendingAppointments.add(appointment);
            }
        }

        return pendingAppointments;
    }

    // RETRIEVE PENDING APPOINTMENTS ON A SPECIFIC DATE
    public List<Appointment> getPendingAppointments(LocalDate date) {
        List<Appointment> pendingAppointments = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING
                    && appointment.getAppointmentDate().equals(date)) {
                pendingAppointments.add(appointment);
            }
        }

        return pendingAppointments;
    }

    // RETRIEVE PENDING APPOINTMENTS FOR A PARTICULAR MONTH
    public List<Appointment> getPendingAppointments(int month) {
        List<Appointment> pendingAppointments = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING
                    && appointment.getAppointmentDate().getMonthValue() == month) {
                pendingAppointments.add(appointment);
            }
        }

        return pendingAppointments;
    }

    // METHODS TO MANAGE APPOINTMENTS

    // UPDATE AVAILABILITY FOR APPOINTMENT TIMESLOT
    public boolean updateAppointmentAvailability(LocalDate date, int availability) {
        return doctorAppointmentServices.updateAppointmentAvailability(date, availability);
    }

    // UPDATE APPOINTMENT STATUS
    public boolean updateAppointmentStatus(String status, LocalDate date, AppointmentTimeSlot timeSlot,
            String patientID) {
        return doctorAppointmentServices.updateAppointmentStatus(status, date, timeSlot, patientID, this.doctorID);
    }

    public boolean isInCurrentYear(LocalDate date) {
        boolean isInCurrentYear = date.getYear() == LocalDate.now().getYear();
        return isInCurrentYear;
    }

}