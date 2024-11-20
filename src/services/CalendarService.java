package src.services;

import java.time.LocalDate;
import java.util.List;

import src.utils.ENUM.CalendarDayStatus;
import src.daos.CalendarDao;

public class CalendarService 
{
    //ATTRIBUTES
    CalendarDao calendarDao;

    //CONSTRUCTOR
    public CalendarService(String ID)
    {
        this.calendarDao= new CalendarDao(ID);
    }

    
    /** 
     * @return List<LocalDate>
     */
    //METHODS

    public List<LocalDate> getMedicalLeaveDates()
    {
        return calendarDao.getMedicalLeaveDates();
    }

    public List<LocalDate> getAnnualLeaveDates()
    {
        return calendarDao.getMedicalLeaveDates();
    }

    public List<LocalDate> getMeetingDates()
    {
        return calendarDao.getMeetingDates();
    }

    public List<LocalDate> getTrainingDates()
    {
        return calendarDao.getTrainingDates();
    }

    public List<LocalDate> getAvailableDates()
    {
        return calendarDao.getAvailableDates();
    }

    public List<LocalDate> getOthersDates()
    {
        return calendarDao.getOthersDates();
    }

    public List<LocalDate> getNaDates()
    {
        return calendarDao.getNaDates();
    }

    public int getNumberOfAnnualLeaveDays(LocalDate date)
    {
        return calendarDao.getNumberOfAnnualLeaveDays(date);
    }

    public void applyAnnualLeave(LocalDate date)
    {
        calendarDao.applyAnnualLeave(date);
    }

    public void applyMedicalLeave(LocalDate date)
    {
        calendarDao.applyMedicalLeave(date);
    }

    public void cancelAnnualLeave(LocalDate date)
    {
        calendarDao.cancelAnnualLeave(date);
    }

    public void cancelMedicalLeave(LocalDate date)
    {
        calendarDao.cancelMedicalLeave(date);
    }

    public CalendarDayStatus getStatus(LocalDate date)
    {
        return calendarDao.getStatus(date);
    }
}