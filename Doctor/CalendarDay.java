package Doctor;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class CalendarDay 
{
    private CalendarDayStatus dayStatus;
    private Map<LocalTime, CalendarDayStatus> timeSlots;

    public CalendarDay() 
    {
        this.dayStatus = CalendarDayStatus.FREE;
        this.timeSlots = new LinkedHashMap<>(); 
        initialiseAppointmentTimeSlots();
    }

    //INITIALISE APPOINTMENT TIME SLOTS
    private void initialiseAppointmentTimeSlots() 
    {
        LocalTime[] appointmentSlots = 
        {
            LocalTime.of(10, 0), LocalTime.of(10, 30),
            LocalTime.of(11, 0), LocalTime.of(11, 30),
            LocalTime.of(15, 0), LocalTime.of(15, 30),
            LocalTime.of(16, 0), LocalTime.of(16, 30)
        };
        for (LocalTime slot : appointmentSlots) 
        {
            timeSlots.put(slot, CalendarDayStatus.FREE);
        }
    }

    //GET AND SET METHODS
    public CalendarDayStatus getDayStatus() 
    {
        return dayStatus;
    }

    public void setDayStatus(CalendarDayStatus dayStatus) 
    {
        this.dayStatus = dayStatus;
        if (dayStatus == CalendarDayStatus.ANNUAL_LEAVE || dayStatus == CalendarDayStatus.MEDICAL_LEAVE) 
        {
            timeSlots.replaceAll((time, status) -> CalendarDayStatus.OTHERS); // Mark all slots as unavailable
        }
    }

    public Map<LocalTime, CalendarDayStatus> getTimeSlots() 
    {
        return timeSlots;
    }

    public boolean setSlotStatus(LocalTime timeSlot, CalendarDayStatus status) 
    {
        if (timeSlots.containsKey(timeSlot)) 
        {
            timeSlots.put(timeSlot, status);
            return true;
        }
        return false;
    }

    public boolean isSlotFree(LocalTime timeSlot) 
    {
        return timeSlots.getOrDefault(timeSlot, CalendarDayStatus.OTHERS) == CalendarDayStatus.FREE;
    }

    public void cancelAllAppointments() 
    {
        timeSlots.replaceAll((time, status) -> status == CalendarDayStatus.PENDING || status == CalendarDayStatus.APPOINTMENT ? CalendarDayStatus.FREE : status);
    }
}
