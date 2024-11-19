package src.interfaces;

import java.util.List;

import src.models.Appointment;

public interface AppointmentViewInterface 
{
    void printAppointmentOnADateView(List<Appointment> appointments);

    void printAppointmentInAMonthView(List<Appointment> appointments);

}