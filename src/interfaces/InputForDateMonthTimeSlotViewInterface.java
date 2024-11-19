package src.interfaces;

import java.time.LocalDate;

import src.models.AppointmentTimeSlot;

public interface InputForDateMonthTimeSlotViewInterface 
{
    int viewByDateOrMonth();

    LocalDate viewWhichDate();

    int viewWhichMonth();

    AppointmentTimeSlot viewWhichTimeSlot();

    
}