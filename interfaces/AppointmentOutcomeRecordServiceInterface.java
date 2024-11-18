package interfaces;

import java.time.LocalDate;
import java.util.List;

import models.Appointment;
import models.AppointmentOutcomeRecord;
import models.AppointmentTimeSlot;

public interface AppointmentOutcomeRecordServiceInterface 
{
    AppointmentOutcomeRecord findAppointmentOutcomeRecord(String patientID, LocalDate date, AppointmentTimeSlot timeSlot);

    boolean updateAppointmentOutcomeRecord(AppointmentOutcomeRecord appointmentOutcomeRecord);

    List<Appointment> getCompletedAppointments(String doctorID);

    List<Appointment> getCompletedAppointmentsInMonth(int month, List<Appointment> appointments);

    List<Appointment> getCompletedAppointmentsInDay(LocalDate date, List<Appointment> appointments);

    boolean checkDate(LocalDate date);
}
