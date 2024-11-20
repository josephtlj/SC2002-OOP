package src.models;

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

    
    /** 
     * @return LocalTime
     */
    // Getters
    public LocalTime getStartTime() 
    {
        return startTime;
    }

    
    /** 
     * @return LocalTime
     */
    public LocalTime getEndTime() 
    {
        return endTime;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() 
    {
        return "Start: " + startTime + ", End: " + endTime;
    }
}