package src.controllers;
import src.services.AppointmentOutcomeRecordServices;

import java.time.*;
import java.util.*;

import src.models.Appointment;
import src.models.AppointmentOutcomeRecord;
import src.models.AppointmentTimeSlot;

public class AppointmentOutcomeRecordController 
{
    //ATTRIBUTES
    AppointmentOutcomeRecordServices appointmentOutcomeRecordServices;

    //CONSTRUCTOR

    public AppointmentOutcomeRecordController(int lorem){
        // insert in normal instantiation
    }

    public AppointmentOutcomeRecordController(String patientID)
    {
        this.appointmentOutcomeRecordServices= new AppointmentOutcomeRecordServices(patientID);
    }

    
    /** 
     * @param outcomeRecord
     * @param serviceType
     * @param notes
     * @return boolean
     */
    //METHODS

    public boolean updateOutcomeRecord(AppointmentOutcomeRecord outcomeRecord, String serviceType ,String notes )
    {
        outcomeRecord.setConsultationNotes(notes);
        outcomeRecord.setServiceType(serviceType);
        return appointmentOutcomeRecordServices.updateAppointmentOutcomeRecord(outcomeRecord);
    }

    
    /** 
     * @param patientID
     * @param date
     * @param timeSlot
     * @return AppointmentOutcomeRecord
     */
    public AppointmentOutcomeRecord findOutcomeRecord(String patientID, LocalDate date, AppointmentTimeSlot timeSlot)
    {
        return appointmentOutcomeRecordServices.findAppointmentOutcomeRecord(patientID,date,timeSlot);
    }

    
    /** 
     * @param doctorID
     * @return List<Appointment>
     */
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

    // public List<AppointmentOutcomeRecord> handleViewAppointmentOutcomeRecordsByDay(LocalDate date){
    //     return appointmentOutcomeRecordServices.readAllAppointmentOutcomeRecordsByDay(date);
    // }
    
}