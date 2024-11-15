package Doctor;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

public class Calendar 
{
    //ATTRIBUTES
    private int year;
    private Map<Integer, Map<Integer, CalendarDay>> calendarData;

    //CONSTRUCTOR
    public Calendar(int year) 
    {
        this.year = year;
        this.calendarData = new HashMap<>();
        initialiseYearCalendar();
    }

    //INITIALISE YEAR CALENDAR
    private void initialiseYearCalendar() 
    {
        GregorianCalendar calendar = new GregorianCalendar();
        for (int month = 1; month <= 12; month++) 
        {
            Map<Integer, CalendarDay> days = new HashMap<>();
            int daysInMonth = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

            for (int day = 1; day <= daysInMonth; day++) 
            {
                calendar.set(year, month - 1, day);
                int dayOfWeek = calendar.get(GregorianCalendar.DAY_OF_WEEK);
                if (dayOfWeek >= GregorianCalendar.MONDAY && dayOfWeek <= GregorianCalendar.FRIDAY) 
                {
                    days.put(day, new CalendarDay());
                } 
                else //WEEKENDS ARE NOT WORKING DAYS
                {
                    days.put(day, null); 
                }
            }
            calendarData.put(month, days);
        }
    }

    //GET AND SET METHODS
    public Optional<CalendarDay> getDay(int month, int day) 
    {
        Map<Integer, CalendarDay> days = calendarData.get(month);
        return (days != null) ? Optional.ofNullable(days.get(day)) : Optional.empty();
    }

    public void setDayStatus(int month, int day, CalendarDayStatus status) 
    {
        getDay(month, day).ifPresent(dayData -> dayData.setDayStatus(status));
    }

    //MOVE TO ANOTHER CLASS
    public void cancelAppointmentsForDay(int month, int day) 
    {
        getDay(month, day).ifPresent(dayData ->
        {
            dayData.setDayStatus(CalendarDayStatus.ANNUAL_LEAVE); // Assume leave cancels all appointments.
        });
    }
}
