package src.interfaces;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import src.utils.ENUM.CalendarDayStatus;

public interface CalendarDaoInterface 
{
    CalendarDayStatus getStatus(LocalDate date);

    List<LocalDate> getDatesByStatus(File calendarFile, CalendarDayStatus status);

    int getNumberOfAnnualLeaveDays(LocalDate date);

    int getNumberOfMedicalLeaveDays(LocalDate date);

    void applyAnnualLeave(LocalDate date);

    void applyMedicalLeave(LocalDate date);

    void cancelAnnualLeave(LocalDate date);

    void cancelMedicalLeave(LocalDate date);

    boolean changeStatus(LocalDate date, CalendarDayStatus status);

    List<LocalDate> getMedicalLeaveDates();

    List<LocalDate> getAnnualLeaveDates();

    List<LocalDate> getMeetingDates();

    List<LocalDate> getTrainingDates();

    List<LocalDate> getAvailableDates();

    List<LocalDate> getOthersDates();

    List<LocalDate> getNaDates();

}