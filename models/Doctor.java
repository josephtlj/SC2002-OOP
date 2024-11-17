package models;

import java.util.List;
import Calendar.Calendar;
import Doctor.Appointment.DoctorAppointmentActionType;
import Doctor.Appointment.DoctorAppointmentManager;
import Doctor.Appointment.DoctorAppointmentView;
import Doctor.DiagnosisTreatmentRecord.DiagnosisTreatmentRecordManager;
import Doctor.DiagnosisTreatmentRecord.DiagnosisTreatmentRecordView;
import Doctor.DoctorPassword.*;
import daos.DiagnosisTreatmentRecordDao;
import daos.DoctorDao;
import daos.MedicalRecordDao;
import Doctor.MedicalRecord.*;

public class Doctor extends User
{
    private enum Department 
    {
	    RADIOLOGY,
	    CARDIOLOGY,
	    ORTHOPEDICS,
	    GENERAL_SURGERY,
	    PHYSIOTHERAPY
    }
    //ATTRIBUTES
    private DoctorDao doctorDao;
    private DiagnosisTreatmentRecordDao treatmentRecordDao;
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

    //MANAGE SCHEDULE
    public void ManangeSchedule()
    {
        doctorCalendar.manageCalendar();
    }

    //MANAGE MEDICAL RECORD
    public void ManageMedicalRecord(DoctorMedicalRecordActionType medicalRecordActionType)
    {
        if(medicalRecordActionType== DoctorMedicalRecordActionType.VIEW)
        {
            //TO BE IMPLEMENTED WHEN INTEGRATING WITH PATIENT
        }
        else
        {
            DiagnosisTreatmentRecordManager treatmentRecordManager= new DiagnosisTreatmentRecordManager(treatmentRecordDao);
            DiagnosisTreatmentRecordView treatmentRecordView= new DiagnosisTreatmentRecordView(treatmentRecordManager);
        }
    }
    
    public void logout()
    {
        System.out.println("Doctor logged out.");
    }
}
