package controllers;
import services.AppointmentOutcomeRecordServices;

import java.time.*;
import java.util.*;

import Doctor.Appointment.Appointment;
import Doctor.Appointment.AppointmentTimeSlot;
import models.AppointmentOutcomeRecord;

public class AppointmentOutcomeRecordController 
{
    //ATTRIBUTES
    AppointmentOutcomeRecordServices appointmentOutcomeRecordServices;

    //CONSTRUCTOR
    public AppointmentOutcomeRecordController(String patientID)
    {
        this.appointmentOutcomeRecordServices= new AppointmentOutcomeRecordServices(patientID);
    }

    //METHODS

    public boolean updateOutcomeRecord(AppointmentOutcomeRecord outcomeRecord, String serviceType ,String notes )
    {
        outcomeRecord.setConsultationNotes(notes);
        outcomeRecord.setServiceType(serviceType);
        return appointmentOutcomeRecordServices.updateAppointmentOutcomeRecord(outcomeRecord);
    }

    public AppointmentOutcomeRecord findOutcomeRecord(String patientID, LocalDate date, AppointmentTimeSlot timeSlot)
    {
        return appointmentOutcomeRecordServices.findAppointmentOutcomeRecord(patientID,date,timeSlot);
    }

    public List<Appointment> getCompletedAppointments(String doctorID)
    {
        return appointmentOutcomeRecordServices.getCompletedAppointments(doctorID);
    }

    public List<Appointment> getCompletedAppointmentsInMonth(int month, List<Appointment> appointments)
    {
        return appointmentOutcomeRecordServices.getCompletedAppointmentsInMonth(month, appointments);
    }

    public List<Appointment> getCompletedAppointmentsInDay(LocalDate date, List<Appointment> appointments)
    {
        return appointmentOutcomeRecordServices.getCompletedAppointmentsInDay(date, appointments);
    }

    public boolean checkDate(LocalDate date)
    {
        return appointmentOutcomeRecordServices.checkDate(date);
    }
    
}
