package Doctor.Appointment;

import daos.DoctorAppointmentDao;

import java.util.*;
import java.time.*;

public class DoctorAppointmentManager 
{
    //ATTRIBUTES
    DoctorAppointmentDao doctorAppointmentSlotsDao;
    DoctorAppointmentActionType actionType;
    List <Appointment> appointments;

    //CONSTRUCTOR
    public DoctorAppointmentManager(String ID, DoctorAppointmentActionType actionType)
    {
        this.doctorAppointmentSlotsDao= new DoctorAppointmentDao(ID);
        this.actionType= actionType;
        this.appointments=getAllAppointments(ID);
    }

    //RETRIEVE ALL APPOINTMENTS FROM DATABASE
    public List<Appointment> getAllAppointments(String ID)
    {
        return doctorAppointmentSlotsDao.getAllAppointments(ID);
    }

    //RETRIEVE CONFIRMED APPOINTMENTS
    public List<Appointment> getConfirmedAppointments()
    {
        List<Appointment> confirmedAppointments = new ArrayList<>();
    
        for (Appointment appointment : appointments) 
        {
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) 
            {
                confirmedAppointments.add(appointment);
            }
        }
    
        return confirmedAppointments;
    }

    //RETRIEVE PENDING APPOINTMENTS
    public List<Appointment> getPendingAppointments()
    {
        List<Appointment> pendingAppointments = new ArrayList<>();
    
        for (Appointment appointment : appointments) 
        {
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING) 
            {
                pendingAppointments.add(appointment);
            }
        }
    
        return pendingAppointments;
    }

    //METHODS TO MANAGE APPOINTMENTS

    //UPDATE AVAILABILITY FOR APPOINTMENT TIMESLOT
    public boolean updateAppointmentAvailability(LocalDate date, String availability)
    {
        return doctorAppointmentSlotsDao.updateAppointmentAvailability(date, availability);
    }

    //UPDATE APPOINTMENT STATUS
    public boolean updateAppointmentStatus(Appointment appointment)
    {
        return doctorAppointmentSlotsDao.updateAppointmentStatus(appointment);
    }

    
}
