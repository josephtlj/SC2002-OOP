package src.interfaces;

import java.time.LocalDate;
import java.util.List;

import src.models.Appointment;

public interface DoctorAppointmentDaoInterface 
{
    List<Appointment> getAllAppointments(String doctorID);

    boolean updateAppointmentAvailability(LocalDate date, String availability);

    boolean updateAppointmentStatus(Appointment appointment);

    
}