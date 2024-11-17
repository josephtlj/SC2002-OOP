package Doctor.Appointment;

import daos.DoctorAppointmentDao;
import models.DiagnosisTreatmentRecord;

import java.util.*;

import Doctor.DiagnosisTreatmentRecord.DiagnosisTreatmentRecordManager;

import java.time.*;

public class DoctorAppointmentManager 
{
    //ATTRIBUTES
    DoctorAppointmentDao doctorAppointmentSlotsDao;
    DiagnosisTreatmentRecordManager treatRecManager;
    DoctorAppointmentActionType actionType;
    List <Appointment> appointments;

    //CONSTRUCTOR
    public DoctorAppointmentManager(String ID, DoctorAppointmentActionType actionType, DiagnosisTreatmentRecordManager treatRecManager)
    {
        this.doctorAppointmentSlotsDao= new DoctorAppointmentDao(ID);
        this.actionType= actionType;
        this.appointments=getAllAppointments(ID);
        this.treatRecManager= treatRecManager;
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

    //RETRIEVE PENDING APPOINTMENTS ON A SPECIFIC DATE
    public List<Appointment> getPendingAppointments(LocalDate date) 
    {
        List<Appointment> pendingAppointments = new ArrayList<>();
        
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING 
                    && appointment.getAppointmentDate().equals(date)) {
                pendingAppointments.add(appointment);
            }
        }
        
        return pendingAppointments;
    }

    // RETRIEVE PENDING APPOINTMENTS FOR A PARTICULAR MONTH
    public List<Appointment> getPendingAppointments(int month)
    {
        List<Appointment> pendingAppointments = new ArrayList<>();
    
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING 
                    && appointment.getAppointmentDate().getMonthValue() == month) {
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
    public void updateAppointmentStatus(Appointment appointment)
    {
        doctorAppointmentSlotsDao.updateAppointmentStatus(appointment);
        if(appointment.getAppointmentStatus()==AppointmentStatus.CONFIRMED)
        {
            //CREATE APPOINTMENT OUTCOME RECORD
            DiagnosisTreatmentRecord treatRec= new DiagnosisTreatmentRecord(null, null, null, appointment);
            this.treatRecManager.addDiagnosisTreatmentRecord(treatRec);
        }
    }

    
}
