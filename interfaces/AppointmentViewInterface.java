package interfaces;

import java.util.List;

import models.Appointment;

public interface AppointmentViewInterface 
{
    void printAppointmentOnADateView(List<Appointment> appointments);

    void printAppointmentInAMonthView(List<Appointment> appointments);

}
