package Doctor;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import DoctorView.DoctorChangePasswordView;
import daos.DoctorDao;

public class Doctor extends User 
{
    //ATTRIBUTES
    private DoctorDao doctorDao;
    private Calendar calendar;  
    private Department department;
    
    //CONSTRUCTOR
    public Doctor(String ID, String name, String department, String gender, int age, String password, boolean IsFirstLogin, byte[] salt, boolean passwordHashed) 
    {
        super(ID, password, Role.valueOf("Doctor"),salt, IsFirstLogin,passwordHashed, gender,age);
        this.calendar = new Calendar(LocalDate.now().getYear());  
        this.department= Department.valueOf(department); 
        this.doctorDao= new DoctorDao();
    }

    //GET METHODS
    public String getDepartment()
    {
        return this.department.name();
    }

    //RETRIEVE ALL DOCTORS FROM DATABASE
    public List<Doctor> getAllDoctors()
    {
        return doctorDao.getAllDoctors();
    }

    //UPDATE DOCTOR'S PASSWORD
    public void updateDoctorPassword()
    {
        DoctorPasswordManager passwordManager= new DoctorPasswordManager(this.doctorDao);
        DoctorChangePasswordView changePasswordView = new DoctorChangePasswordView(passwordManager);
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
    
    public void logout()
    {
        System.out.println("Doctor logged out.");
    }
}
