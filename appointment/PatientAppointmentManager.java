package appointment;
import java.time.LocalDate;
import java.util.*;

public class PatientAppointmentManager{

    PatientAppointmentDAO patientAppointmentSlotsDAO;
    PatientAppointmentActionType actionType;
    List <Appointment> appointments;

    public PatientAppointmentManager(String ID, PatientAppointmentActionType actionType){
        this.patientAppointmentSlotsDAO = new PatientAppointmentDAO(ID);
        this.actionType = actionType;
        this.appointments = getALLAppointments(ID);
    }

    public List<Appointment> getAllAppointments(String ID)
    {
        return patientAppointmentSlotsDao.getAllAppointments(ID);
    }

    public List<Appointment> getAvailableAppointments()
    {
        List<Appointment> availableAppointments = new ArrayList<>();
    
        for (Appointment appointment : appointments) 
        {
            if (appointment.getAppointmentAvailability() == AppointmentAvailability.Yes) 
            {
                availableAppointments.add(appointment);
            }
        }
        return availableAppointments;
    }

    


    public boolean updateAppointmentAvailability(LocalDate date, String availability)
    {
        return patientAppointmentSlotsDAO.updateAppointmentAvailability(date, availability);
    }

    public boolean updateAppointmentStatus(Appointment appointment)
    {
        return patientAppointmentSlotsDAO.updateAppointmentStatus(appointment);
    }

}