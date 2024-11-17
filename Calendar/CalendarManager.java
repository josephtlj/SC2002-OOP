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
    public ApplyAnnualLeaveError applyAnnualLeave(LocalDate date)
    {
        int balanceAL= ANNUAL_LEAVE_DAYS-calendarDao.getAnnualLeaveDates().size();
        if(balanceAL==0)
        {
            ApplyAnnualLeaveError errorType = ApplyAnnualLeaveError.INSUFFICIENT_AL_DAYS;
            return errorType;
        }
        else
        {
            if(calendarDao.getNumberOfAnnualLeaveDays(date)>AL_THRESHOLD)
            {
                ApplyAnnualLeaveError errorType = ApplyAnnualLeaveError.STAFF_SHORTAGE;
                return errorType;
            }
            else
            {
                calendarDao.applyAnnualLeave(date);
                ApplyAnnualLeaveError errorType = ApplyAnnualLeaveError.NO_ERROR;
                return errorType;
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
    public void cancelAnnualLeave(LocalDate date)
    {
        calendarDao.cancelAnnualLeave(date);
    }

    //CANCEL MEDICAL LEAVE
    public void cancelMedicalLeave(LocalDate date)
    {
        calendarDao.cancelMedicalLeave(date);
    }

    //GET METHODS
    public CalendarDayStatus getStatusForDate(LocalDate date)
    {
        return calendarDao.getStatus(date);
    }
    
}
