package interfaces;

import java.time.LocalDate;

import models.AppointmentTimeSlot;

public interface InputForDateMonthTimeSlotViewInterface 
{
    int viewByDateOrMonth();

    LocalDate viewWhichDate();

    int viewWhichMonth();

    AppointmentTimeSlot viewWhichTimeSlot();

    
}
