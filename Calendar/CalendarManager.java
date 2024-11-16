package Calendar;

import daos.CalendarDao;
import java.time.*;

public class CalendarManager 
{
    static final int ANNUAL_LEAVE_DAYS= 14;
    static final int MEDICAL_LEAVE_DAYS= 14;
    static final int AL_THRESHOLD=3;
    //ATTRIBUTES
    CalendarDao calendarDao;

    //CONSTRUCTOR
    public CalendarManager(CalendarDao calendarDao)
    {
        this.calendarDao= calendarDao;
    }

    //METHODS

    //APPLY ANNUAL LEAVE
    public boolean applyAnnualLeave(LocalDate date)
    {
        int balanceAL= ANNUAL_LEAVE_DAYS-calendarDao.getAnnualLeaveDates().size();
        if(balanceAL==0)
        {
            return false;
        }
        else
        {
            if(calendarDao.getNumberOfAnnualLeaveDays(date)>AL_THRESHOLD)
            {
                return false;
            }
            else
            {
                return calendarDao.applyAnnualLeave(date);
            }
        }
    }

    //APPLY MEDICAL LEAVE
    public boolean applyMedicalLeave(LocalDate date)
    {
        int balanceML= MEDICAL_LEAVE_DAYS-calendarDao.getMedicalLeaveDates().size();
        if(balanceML==0)
        {
            return false;
        }
        else
        {
            calendarDao.applyMedicalLeave(date);
            return true;
        }
    }

    //CANCEL ANNUAL LEAVE
    public boolean cancelAnnualLeave(LocalDate date)
    {
        return calendarDao.cancelAnnualLeave(date);
    }

    //CANCEL MEDICAL LEAVE
    public boolean cancelMedicalLeave(LocalDate date)
    {
        return calendarDao.cancelMedicalLeave(date);
    }
    
}
