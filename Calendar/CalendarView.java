package Calendar;

import java.util.Scanner;
import java.time.*;

public class CalendarView 
{
    //ATTRIBUTES
    private Scanner calendarScanner = new Scanner(System.in);

    //CONSTRUCTOR
    public CalendarView(CalendarManager calendarManager)
    {
        printCalendarManagement();
    }

    //VIEW METHODS

    public void printCalendarManagement()
    {
        //print menu of tasks that can be done
    }

    //PRINT CALENDAR
    public void printCalendar()
    {
        //ASK USER IF THEY WANT TO VIEW A DATE OR AN ENTIRE MONTH
        //ASK USER TO ENTER DATE AND CHECK IF INPUT IS VALID. CONVERT STRING DATE TO LOCALDATE
        ////ASK USER TO ENTER MONTH AND CHECK IF INPUT IS VALID. 1 IS JAN AND 12 IS DEC
        //CALL PRINTCALENDARDATE() OR PRINTCALENDARMONTH() DEPENDING ON THE INPUT
    }

    public void printCalendarDate(LocalDate date)
    {
        //PRINT DAY | STATUS
    }

    public void printCalendarMonth(int month)
    {
        //PRINT DAY| STATUS 
    }


    
}
