package src.models;

import java.util.List;

import javax.print.Doc;

import src.daos.DoctorDao;
import src.daos.MedicalRecordDao;
import src.interfaces.MedicalRecordDaoInterface;
import src.interfaces.MedicalRecordServiceInterface;
import src.services.MedicalRecordService;
import src.views.AppointmentOutcomeRecordView;
import src.views.DiagnosisTreatmentRecordView;
import src.views.DoctorAppointmentView;
import src.views.DoctorChangePasswordView;
import src.views.DoctorMedicalRecordView;
import src.utils.ENUM.DoctorAppointmentActionType;
import src.utils.ENUM.DoctorDepartment;
import src.utils.ENUM.DoctorMedicalRecordActionType;

import src.controllers.MedicalRecordController;

public class Doctor extends Staff
{
    //ATTRIBUTES
    private Calendar doctorCalendar;
    private DoctorDepartment department;
    private DoctorDao doctorDao;
    
    //CONSTRUCTOR
    // public Doctor(String ID, String name, String department,String password, boolean IsFirstLogin, byte[] salt) 
    public Doctor(String hospitalId, String password, Role role, byte[] salt,boolean isFirstLogin, String name, Gender gender, int age, DoctorDepartment department)
    {
        super(hospitalId, password, role, salt, isFirstLogin, name, gender, age);
        this.department = department;
        this.doctorCalendar= new Calendar(hospitalId);
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
        DoctorChangePasswordView changePasswordView = new DoctorChangePasswordView(super.getHospitalId());
    }

    //MANAGE APPOINTMENTS
    public void ManageAppointments(DoctorAppointmentActionType actionType)
    {
        DoctorAppointmentView doctorAppointmentView= new DoctorAppointmentView(super.getHospitalId(), actionType);
    }

    //MANAGE SCHEDULE
    public void ManangeSchedule()
    {
        doctorCalendar.manageCalendar(super.getHospitalId());
    }

    //MANAGE MEDICAL RECORD

    MedicalRecordDaoInterface medicalRecordDao = new MedicalRecordDao();
    MedicalRecordServiceInterface medicalRecordService = new MedicalRecordService(medicalRecordDao);
    MedicalRecordController medicalRecordController = new MedicalRecordController(medicalRecordService);

    public void ManageMedicalRecord(DoctorMedicalRecordActionType medicalRecordActionType)
    {
        if(medicalRecordActionType== DoctorMedicalRecordActionType.VIEW)
        {   
            DoctorMedicalRecordView medicalRecordView= new DoctorMedicalRecordView(medicalRecordController);
        }
        else
        {
            DiagnosisTreatmentRecordView treatmentRecordView= new DiagnosisTreatmentRecordView(medicalRecordController);
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