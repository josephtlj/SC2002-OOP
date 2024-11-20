package src.controllers;

import src.daos.CalendarDao;
import java.time.*;
import src.services.CalendarService;
import src.utils.ENUM.ApplyAnnualLeaveError;
import src.utils.ENUM.CalendarDayStatus;

public class CalendarController 
{
    static final int ANNUAL_LEAVE_DAYS= 14;
    static final int MEDICAL_LEAVE_DAYS= 14;
    static final int AL_THRESHOLD=3;
    //ATTRIBUTES
    CalendarService calendarService;

    //CONSTRUCTOR
    public CalendarController(String ID)
    {
        this.calendarService= new CalendarService(ID);
    }

    
    /** 
     * @param date
     * @return ApplyAnnualLeaveError
     */
    //METHODS

    //APPLY ANNUAL LEAVE
    public ApplyAnnualLeaveError applyAnnualLeave(LocalDate date)
    {
        int balanceAL= ANNUAL_LEAVE_DAYS-calendarService.getAnnualLeaveDates().size();
        if(balanceAL==0)
        {
            ApplyAnnualLeaveError errorType = ApplyAnnualLeaveError.INSUFFICIENT_AL_DAYS;
            return errorType;
        }
        else
        {
            if(calendarService.getNumberOfAnnualLeaveDays(date)>AL_THRESHOLD)
            {
                ApplyAnnualLeaveError errorType = ApplyAnnualLeaveError.STAFF_SHORTAGE;
                return errorType;
            }
            else
            {
                calendarService.applyAnnualLeave(date);
                ApplyAnnualLeaveError errorType = ApplyAnnualLeaveError.NO_ERROR;
                return errorType;
            }
        }
    }

    
    /** 
     * @param date
     * @return boolean
     */
    //APPLY MEDICAL LEAVE
    public boolean applyMedicalLeave(LocalDate date)
    {
        int balanceML= MEDICAL_LEAVE_DAYS-calendarService.getMedicalLeaveDates().size();
        if(balanceML==0)
        {
            return false;
        }
        else
        {
            calendarService.applyMedicalLeave(date);
            return true;
        }
    }

    //CANCEL ANNUAL LEAVE
    public void cancelAnnualLeave(LocalDate date)
    {
        calendarService.cancelAnnualLeave(date);
    }

    //CANCEL MEDICAL LEAVE
    public void cancelMedicalLeave(LocalDate date)
    {
        calendarService.cancelMedicalLeave(date);
    }

    //GET METHODS
    public CalendarDayStatus getStatusForDate(LocalDate date)
    {
        return calendarService.getStatus(date);
    }
    
}