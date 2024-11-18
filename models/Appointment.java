package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Enum.AppointmentStatus;

public class Appointment 
{
    //ATTRIBUTES    
    private AppointmentStatus status;                      
    private LocalDate date;               
    private AppointmentTimeSlot timeSlot;
    private String patientID;
    private String doctorID;

    //CONSTRUCTOR
    public Appointment(String status, String date, String startTime, String endTime, String patientID, String doctorID)
    {
        this.status= AppointmentStatus.valueOf(status);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date = LocalDate.parse(date, formatter);

        this.timeSlot= new AppointmentTimeSlot(startTime, endTime);
        this.patientID= patientID;
        this.doctorID= doctorID;
    }

    //GET METHODS
    public AppointmentTimeSlot getAppointmentTimeSlot()
    {
        return timeSlot;
    }

    public AppointmentStatus getAppointmentStatus()
    {
        return status;
    }

    public LocalDate getAppointmentDate()
    {
        return date;
    }

    public String getPatientID()
    {
        return patientID;
    }

    public String getDoctorID()
    {
        return doctorID;
    }

    //SET METHODS
    public void setAppointmentStatus(AppointmentStatus status)
    {
        this.status= status;
    }

    public void setAppointmentTimeSlot(AppointmentTimeSlot timeSlot)
    {
        this.timeSlot= timeSlot;
    }

    public void setPatientID(String ID)
    {
        this.patientID= ID;
    }

    public void setDoctorTD(String ID)
    {
        this.doctorID= ID;
    }

    public void setAppointmentDate(LocalDate date)
    {
        this.date= date;
    }
}
