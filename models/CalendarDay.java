package models;

import Enum.CalendarDayStatus;

public class CalendarDay 
{
    private CalendarDayStatus dayStatus;

    public CalendarDay() 
    {
        this.dayStatus = CalendarDayStatus.AVAILABLE; //DEFAULT
    }

    //GET AND SET METHODS
    public CalendarDayStatus getDayStatus() 
    {
        return dayStatus;
    }

    public void setDayStatus(CalendarDayStatus dayStatus) 
    {
        this.dayStatus = dayStatus;
    }

}
