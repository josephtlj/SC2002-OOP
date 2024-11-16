package Doctor.Appointment;

import java.time.LocalTime;

public class AppointmentTimeSlot 
{
    private LocalTime startTime;
    private LocalTime endTime;

    // Constructor
    public AppointmentTimeSlot(String startTime, String endTime) 
    {
        this.startTime=LocalTime.parse(startTime);
        this.endTime=LocalTime.parse(endTime);
    }

    // Getters
    public LocalTime getStartTime() 
    {
        return startTime;
    }

    public LocalTime getEndTime() 
    {
        return endTime;
    }

    @Override
    public String toString() 
    {
        return "Start: " + startTime + ", End: " + endTime;
    }
}
