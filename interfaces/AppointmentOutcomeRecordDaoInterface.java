package interfaces;

import java.time.LocalDate;
import java.util.List;

import models.Appointment;
import models.AppointmentOutcomeRecord;
import models.AppointmentTimeSlot;

public interface AppointmentOutcomeRecordDaoInterface 
{
    boolean updateAppointmentOutcomeRecord(AppointmentOutcomeRecord record);

    AppointmentOutcomeRecord findAppointmentOutcomeRecord(String patientID, LocalDate date, AppointmentTimeSlot timeSlot);

    List<Appointment> getCompletedAppointments(String doctorID);


}
