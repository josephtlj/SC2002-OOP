package src.models;

import src.utils.ENUM.CalendarDayStatus;

public class CalendarDay 
{
    private CalendarDayStatus dayStatus;

    public CalendarDay() 
    {
        this.dayStatus = CalendarDayStatus.AVAILABLE; //DEFAULT
    }

    
    /** 
     * @return CalendarDayStatus
     */
    //GET AND SET METHODS
    public CalendarDayStatus getDayStatus() 
    {
        return dayStatus;
    }

    
    /** 
     * @param dayStatus
     */
    public void setDayStatus(CalendarDayStatus dayStatus) 
    {
        this.dayStatus = dayStatus;
    }

}