package models;

import java.util.List;
import Calendar.Calendar;
import daos.AppointmentOutcomeRecordDao;
import daos.DiagnosisTreatmentRecordDao;
import daos.DoctorDao;
import daos.MedicalRecordDao;
import views.AppointmentOutcomeRecordView;
import views.DiagnosisTreatmentRecordView;
import views.DoctorAppointmentView;
import views.DoctorChangePasswordView;
import Doctor.MedicalRecord.*;
import controllers.AppointmentOutcomeRecordController;
import controllers.DiagnosisTreatmentRecordController;
import controllers.DoctorAppointmentController;
import controllers.DoctorPasswordController;
import services.DoctorAppointmentServices;
import Enum.DoctorAppointmentActionType;
import Enum.DoctorDepartment;

public class Doctor extends User
{
    //ATTRIBUTES
    private DoctorDao doctorDao;
    private MedicalRecordDao medicalRecordDao;
    private Calendar doctorCalendar;
    private DoctorDepartment department;
    
    //CONSTRUCTOR
    public Doctor(String ID, String name, String department,String password, boolean IsFirstLogin, byte[] salt) 
    {
        super(ID, password, Role.valueOf("Doctor"),salt, IsFirstLogin); 
        this.department= DoctorDepartment.valueOf(department); 
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
        DoctorPasswordController passwordManager= new DoctorPasswordController(this.doctorDao);
        DoctorChangePasswordView changePasswordView = new DoctorChangePasswordView(passwordManager);
    }

    //MANAGE APPOINTMENTS
    public void ManageAppointments(DoctorAppointmentActionType actionType)
    {
        DoctorAppointmentView doctorAppointmentView= new DoctorAppointmentView(super.getHospitalId(), actionType);
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
            DoctorMedicalRecordManager manager= new DoctorMedicalRecordManager(medicalRecordDao);
            DoctorMedicalRecordView medicalRecordView= new DoctorMedicalRecordView(manager);
        }
        else
        {
            DiagnosisTreatmentRecordController treatmentRecordManager= new DiagnosisTreatmentRecordController(treatmentRecordDao);
            DiagnosisTreatmentRecordView treatmentRecordView= new DiagnosisTreatmentRecordView(treatmentRecordManager);
        }
    }

    //MANAGE APPOINTMENT OUTCOME RECORD
    public void ManageAppointmentOutcomeRecord()
    {
        AppointmentOutcomeRecordView outcomeRecordView= new AppointmentOutcomeRecordView(super.getHospitalId());
    }
    
    public void logout()
    {
        System.out.println("Doctor logged out.");
    }
}
