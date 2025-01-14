package src.services;

import src.models.Appointment;
import src.models.AppointmentTimeSlot;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import src.daos.DoctorAppointmentDao;
import src.interfaces.DoctorAppointmentServicesInterface;

public class DoctorAppointmentServices implements DoctorAppointmentServicesInterface 
{
    DoctorAppointmentDao doctorAppointmentDao;

    //CONSTRUCTOR
    public DoctorAppointmentServices(String doctorID)
    {
        this.doctorAppointmentDao= new DoctorAppointmentDao(doctorID);
    }
    
    /** 
     * @param doctorID
     * @return List<Appointment>
     */
    //METHODS

    public List<Appointment> getAllAppointments(String doctorID)
    {
        return doctorAppointmentDao.getAllAppointments(doctorID);
    }

    
    /** 
     * @param appointments
     * @param patientID
     * @param date
     * @param timeSlot
     * @return boolean
     */
    public boolean checkIfAppointmentInAppointments(List<Appointment> appointments, String patientID, LocalDate date, AppointmentTimeSlot timeSlot)
    {
        Optional<Appointment> appointmentOptional = appointments.stream()
                .filter(a -> a.getAppointmentDate().equals(date) 
                        && a.getAppointmentTimeSlot().getStartTime().equals(timeSlot.getStartTime())
                        && a.getAppointmentTimeSlot().getEndTime().equals(timeSlot.getEndTime())
                        && a.getPatientID().equals(patientID))
                .findFirst();
    
        if (appointmentOptional.isEmpty()) {
            return false;
        }
        return true;
    }

    
    /** 
     * @param status
     * @param date
     * @param timeSlot
     * @param patientID
     * @param doctorID
     * @return boolean
     */
    public boolean updateAppointmentStatus(String status, LocalDate date, AppointmentTimeSlot timeSlot, String patientID, String doctorID)
    {

        String Date = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String startTime = timeSlot.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String endTime = timeSlot.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        Appointment appointment = new Appointment(status, Date, startTime, endTime, patientID, doctorID);

        return doctorAppointmentDao.updateAppointmentStatus(appointment);

    }

    public boolean updateAppointmentAvailability(LocalDate date, int availability)
    {
        String Availability=null;

        if(availability==1)
        {
            Availability="Yes";
        }
        else
        {
            Availability="No";
        }
        if(isInCurrentYear(date))
        {
            return doctorAppointmentDao.updateAppointmentAvailability(date, Availability);
        }
        else
        {
            return false;
        }
    }

    public boolean isInCurrentYear(LocalDate date)
    {
        boolean isInCurrentYear = date.getYear() == LocalDate.now().getYear();
        return isInCurrentYear;
    }
}