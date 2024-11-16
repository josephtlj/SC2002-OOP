package Doctor;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Doctor.Appointment.DoctorAppointmentActionType;
import Doctor.Appointment.DoctorAppointmentManager;
import Doctor.Appointment.DoctorAppointmentView;
import Doctor.Calendar.Calendar;
import Doctor.Calendar.CalendarDay;
import Doctor.Calendar.CalendarDayStatus;
import Doctor.DoctorPassword.*;
import daos.DoctorCalendarDao;
import daos.DoctorDao;

public class Doctor extends User 
{
    //ATTRIBUTES
    private DoctorDao doctorDao;
    private Calendar doctorCalendar;
    private Department department;
    
    //CONSTRUCTOR
    public Doctor(String ID, String name, String department, String gender, int age, String password, boolean IsFirstLogin, byte[] salt, boolean passwordHashed) 
    {
        super(ID, password, Role.valueOf("Doctor"),salt, IsFirstLogin,passwordHashed, gender,age); 
        this.department= Department.valueOf(department); 
        this.doctorDao= new DoctorDao();
        this.doctorCalendar= new Calendar(ID);
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

    //MANAGE APPOINTMENTS
    public void ManageAppointments(DoctorAppointmentActionType actionType)
    {
        DoctorAppointmentManager appointmentManager= new DoctorAppointmentManager(getHospitalId(), actionType);
        DoctorAppointmentView appointmentView= new DoctorAppointmentView(appointmentManager);
    }
    
    public void logout()
    {
        System.out.println("Doctor logged out.");
    }
}
