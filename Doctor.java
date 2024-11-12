import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class Doctor extends User 
{
    // Attributes
    private Calendar calendar;  // Updated to use Calendar type
    private Department department;
    
    // Constructor
    public Doctor(String role, String department) 
    {
        super(role);
        this.calendar = new Calendar(LocalDate.now().getYear());  // Initialize Calendar for the current year
        this.department= Department.valueOf(department); 
    }

    // set availability for a specific date
    public void setAvailability(int month, int day, CalendarDayStatus status) 
    {
        calendar.updateDayStatus(month, day, status);
    }

    // get all free dates in a specific month
    public Map<Integer, CalendarDay> getFreeDates(int month) 
    {
        return calendar.getFreeDays(month);
    }

    // patient to request an appointment on a specific date
    public boolean requestAppointment(int month, int day) 
    {
        Optional<CalendarDay> dayOptional = calendar.getDay(month, day);
        if (dayOptional.isPresent() && dayOptional.get().getStatus() == CalendarDayStatus.FREE) 
        {
            calendar.updateDayStatus(month, day, CalendarDayStatus.PENDING);  // Mark as pending confirmation
            return true;
        }
        return false;
    }

    // accept an appointment request
    public void confirmAppointment(int month, int day) 
    {
        Optional<CalendarDay> dayOptional = calendar.getDay(month, day);
        if (dayOptional.isPresent() && dayOptional.get().getStatus() == CalendarDayStatus.PENDING) 
        {
            calendar.updateDayStatus(month, day, CalendarDayStatus.APPOINTMENT);  // Confirm appointment
        }
        else 
        {
            System.out.println("No pending appointment found on this date.");
        }
    }

    // decline an appointment request
    public void declineAppointment(int month, int day) 
    {
        Optional<CalendarDay> dayOptional = calendar.getDay(month, day);
        if (dayOptional.isPresent() && dayOptional.get().getStatus() == CalendarDayStatus.PENDING) 
        {
            calendar.updateDayStatus(month, day, CalendarDayStatus.FREE);  // Mark date as available again
        }
        else 
        {
            System.out.println("No pending appointment found on this date.");
        }
    }

    // view doctor's upcoming appointments
    public Map<Integer, CalendarDay> getUpcomingAppointments(int month) 
    {
        Map<Integer, CalendarDay> appointments = calendar.getFreeDays(month);
        appointments.entrySet().removeIf(entry -> entry.getValue().getStatus() != CalendarDayStatus.APPOINTMENT);
        return appointments;
    }

    // check if a specific date is free
    public boolean isDateFree(int month, int day) 
    {
        Optional<CalendarDay> dayOptional = calendar.getDay(month, day);
        return dayOptional.isPresent() && dayOptional.get().getStatus() == CalendarDayStatus.FREE;
    }
    
    @Override
    public void logout()
    {
        System.out.println("Doctor logged out.");
    }
}
