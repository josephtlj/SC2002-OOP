package Doctor.Appointment;

import daos.AppointmentOutcomeRecordDao;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentOutcomeRecordManager 
{
    //ATTRIBUTES
    AppointmentOutcomeRecordDao outcomeRecordDao;

    //CONSTRUCTOR
    public AppointmentOutcomeRecordManager(AppointmentOutcomeRecordDao outcomeRecordDao)
    {
        this.outcomeRecordDao=outcomeRecordDao;
    }

    //METHODS

    public void updateOutcomeRecord(AppointmentOutcomeRecord outcomeRecord, String serviceType ,String notes )
    {
        outcomeRecord.setConsultationNotes(notes);
        outcomeRecord.setServiceType(serviceType);
        outcomeRecordDao.updateAppointmentOutcomeRecord(outcomeRecord);
    }

    public AppointmentOutcomeRecord findOutcomeRecord(String patientID, LocalDate date, AppointmentTimeSlot timeSlot)
    {
        return outcomeRecordDao.findAppointmentOutcomeRecord(patientID,date,timeSlot);
    }

    public List<Appointment> getCompletedAppointments(String ID)
    {
        return outcomeRecordDao.completedAppointments(ID);
    }

    public List<Appointment> getCompletedAppointmentsInMonth(int month, List<Appointment> appointments)
    {
        // Filter the appointments list to find appointments in the specified month with status CONFIRMED
        return appointments.stream()
                .filter(appointment -> appointment.getAppointmentDate().getMonthValue() == month )
                .collect(Collectors.toList());
    }

    public List<Appointment> getCompletedAppointmentsInDay(LocalDate date, List<Appointment> appointments)
    {
        // Filter the appointments list to find appointments in the specified date 
        return appointments.stream()
                .filter(appointment -> appointment.getAppointmentDate().isEqual(date))
                .collect(Collectors.toList());
    }
    
}
