package Doctor.Calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;

import daos.DoctorCalendarDao;

import java.util.HashMap;
import java.util.List;

public class Calendar 
{
    //ATTRIBUTES
    DoctorCalendarDao doctorCalendarDao;
    private final Map<Integer, Map<Integer, CalendarDay>> calendarData = new HashMap<>();

    //CONSTRUCTOR
    public Calendar(String ID) 
    {
        this.doctorCalendarDao= new DoctorCalendarDao(ID);
        initialiseCalendar();
    }

    //INISIALISE CALENDAR
    public void initialiseCalendar() 
    {
        //FETCH DATES FOR EACH CALENDAR DAY STATUS
        List<LocalDate> medicalLeaveDates = doctorCalendarDao.getMedicalLeaveDates();
        List<LocalDate> annualLeaveDates = doctorCalendarDao.getAnnualLeaveDates();
        List<LocalDate> meetingDates = doctorCalendarDao.getMeetingDates();
        List<LocalDate> trainingDates = doctorCalendarDao.getTrainingDates();
        List<LocalDate> availableDates = doctorCalendarDao.getAvailableDates();
        List<LocalDate> othersDates = doctorCalendarDao.getOthersDates();
        List<LocalDate> naDates = doctorCalendarDao.getNaDates();

        //INITIALISE GREGORIAN CALENDAR
        for (int month = 1; month <= 12; month++) 
        {
            Map<Integer, CalendarDay> days = new HashMap<>();
            for (int day = 1; day <= 31; day++) 
            {
                LocalDate date;
                try 
                {
                    date = LocalDate.of(LocalDate.now().getYear(), month, day); //CURRENT YEAR IS 2024
                } 
                catch (Exception e) 
                {
                    continue; //SKIP INVALID DATES (EG. 30 FEB)
                }

                CalendarDay calendarDay = new CalendarDay();

                //SET STATUS BASED ON DATE
                if (medicalLeaveDates.contains(date)) 
                {
                    calendarDay.setDayStatus(CalendarDayStatus.MEDICAL_LEAVE);
                } 
                else if (annualLeaveDates.contains(date)) 
                {
                    calendarDay.setDayStatus(CalendarDayStatus.ANNUAL_LEAVE);
                } 
                else if (meetingDates.contains(date)) 
                {
                    calendarDay.setDayStatus(CalendarDayStatus.MEETING);
                } 
                else if (trainingDates.contains(date)) 
                {
                    calendarDay.setDayStatus(CalendarDayStatus.TRAINING);
                } 
                else if (availableDates.contains(date)) 
                {
                    calendarDay.setDayStatus(CalendarDayStatus.AVAILABLE);
                } 
                else if (othersDates.contains(date)) 
                {
                    calendarDay.setDayStatus(CalendarDayStatus.OTHERS);
                } 
                else if (naDates.contains(date)) 
                {
                    calendarDay.setDayStatus(CalendarDayStatus.NA);
                }

                days.put(day, calendarDay);
            }
            calendarData.put(month, days);
        }
    }

    //GET METHODS
    public Optional<CalendarDay> getDay(int month, int day) 
    {
        Map<Integer, CalendarDay> days = calendarData.get(month);
        return (days != null) ? Optional.ofNullable(days.get(day)) : Optional.empty();
    }

    public int getDaysInMonth(int month) 
    {
        int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return YearMonth.of(year, month).lengthOfMonth();
    }

    //SET METHODS
    public void setDayStatus(int month, int day, CalendarDayStatus status) 
    {
        getDay(month, day).ifPresent(dayData -> dayData.setDayStatus(status));
    }
    
}
