package src.interfaces;

import java.time.LocalDate;
import java.util.List;

import src.models.Appointment;
import src.models.AppointmentOutcomeRecord;
import src.models.AppointmentTimeSlot;

public interface AppointmentOutcomeRecordDaoInterface 
{
    boolean updateAppointmentOutcomeRecord(AppointmentOutcomeRecord record);

    AppointmentOutcomeRecord findAppointmentOutcomeRecord(String patientID, LocalDate date, AppointmentTimeSlot timeSlot);

    List<Appointment> getCompletedAppointments(String doctorID);


}