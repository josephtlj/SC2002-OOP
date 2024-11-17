package appointment;

import java.time.LocalDate;
import java.util.*;

public class PatientAppointmentManager {

    PatientAppointmentDAO patientAppointmentSlotsDAO;
    PatientAppointmentActionType actionType;
    List<Appointment> appointments;

    public PatientAppointmentManager(String ID, PatientAppointmentActionType actionType) {
        this.patientAppointmentSlotsDAO = new PatientAppointmentDAO(ID);
        this.actionType = actionType;
        this.appointments = getAllAppointments(ID);
    }

    public List<Appointment> getAllAppointments(String ID) {
        return patientAppointmentSlotsDAO.getAllAppointments(ID);
    }

    // BY DAY
    public List<Appointment> getAvailableAppointments(LocalDate date) {
        List<Appointment> availableAppointments = new ArrayList<>();

        availableAppointments = patientAppointmentSlotsDAO.getAllAvailableAppointmentsByDay(date);

        return availableAppointments;
    }

    // BY MONTH
    public List<Appointment> getAvailableAppointments(int month) {
        List<Appointment> availableAppointments = new ArrayList<>();

        availableAppointments = patientAppointmentSlotsDAO.getAllAvailableAppointmentsByMonth(month);

        return availableAppointments;
    }

    public boolean updateAppointmentAvailability(LocalDate date, String availability) {
        return patientAppointmentSlotsDAO.updateAppointmentAvailability(date, availability);
    }

    public boolean updateAppointmentStatus(Appointment appointment) {
        return patientAppointmentSlotsDAO.updateAppointmentStatus(appointment);
    }

}
