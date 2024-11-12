import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Calendar
{
    private int year;
    private Map<YearMonth, Map<Integer, CalendarDay>> calendarData;

    // constructor to initialize the calendar for a given year
    public Calendar(int year)
    {
        this.year = year;
        this.calendarData = new HashMap<>();
        initializeYearCalendar();
    }

    // initialize each month with the correct number of days
    private void initializeYearCalendar()
    {
        for (int month = 1; month <= 12; month++)
        {
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();
            Map<Integer, CalendarDay> days = new HashMap<>();

            for (int day = 1; day <= daysInMonth; day++)
            {
                days.put(day, new CalendarDay());
            }
            calendarData.put(yearMonth, days);
        }
    }

    // get a specific day
    public Optional<CalendarDay> getDay(int month, int day)
    {
        YearMonth yearMonth = YearMonth.of(year, month);
        Map<Integer, CalendarDay> days = calendarData.get(yearMonth);
        if (days != null && days.containsKey(day))
        {
            return Optional.ofNullable(days.get(day));
        }
        return Optional.empty();
    }

    // mark a day with a specific status
    public void updateDayStatus(int month, int day, CalendarDayStatus status)
    {
        YearMonth yearMonth = YearMonth.of(year, month);
        Map<Integer, CalendarDay> days = calendarData.get(yearMonth);
        if (days != null && days.containsKey(day))
        {
            days.get(day).setStatus(status);
        }
    }

    // retrieve all free days in a given month
    public Map<Integer, CalendarDay> getFreeDays(int month)
    {
        YearMonth yearMonth = YearMonth.of(year, month);
        Map<Integer, CalendarDay> freeDays = new HashMap<>();
        Map<Integer, CalendarDay> days = calendarData.get(yearMonth);

        if (days != null)
        {
            for (Map.Entry<Integer, CalendarDay> entry : days.entrySet())
            {
                if (entry.getValue().getStatus() == CalendarDayStatus.FREE)
                {
                    freeDays.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return freeDays;
    }

    // get the number of days in a specific month
    public int getDaysInMonth(int month)
    {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }
}
