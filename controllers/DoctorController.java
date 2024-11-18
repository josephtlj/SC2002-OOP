package controllers;

import java.util.List;

import Enum.DoctorAppointmentActionType;
import Enum.DoctorMedicalRecordActionType;
import models.Doctor;
import views.AppointmentOutcomeRecordView;
import views.DiagnosisTreatmentRecordView;
import views.DoctorAppointmentView;
import views.DoctorChangePasswordView;
import views.DoctorMedicalRecordView;
import services.DoctorService;

public class DoctorController 
{
    //ATTRIBUTES
    DoctorService doctorService;

    //CONSTRUCTOR
    public DoctorController(String ID)
    {
        this.doctorService= new DoctorService(ID);
    }
    //GET METHODS
    public String getDepartment()
    {
        return this.department.name();
    }

    //RETRIEVE ALL DOCTORS FROM DATABASE
    public List<Doctor> getAllDoctors()
    {
        return doctorService.getAllDoctors();
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
    public void ManageMedicalRecord(DoctorMedicalRecordActionType medicalRecordActionType)
    {
        if(medicalRecordActionType== DoctorMedicalRecordActionType.VIEW)
        {
            DoctorMedicalRecordView medicalRecordView= new DoctorMedicalRecordView();
        }
        else
        {
            DiagnosisTreatmentRecordView treatmentRecordView= new DiagnosisTreatmentRecordView();
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
