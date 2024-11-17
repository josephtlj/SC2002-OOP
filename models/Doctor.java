package models;

import java.util.List;
import Calendar.Calendar;
import Doctor.Appointment.AppointmentOutcomeRecord;
import Doctor.Appointment.AppointmentOutcomeRecordManager;
import Doctor.Appointment.DoctorAppointmentActionType;
import Doctor.Appointment.DoctorAppointmentManager;
import Doctor.Appointment.DoctorAppointmentView;
import Doctor.DiagnosisTreatmentRecord.DiagnosisTreatmentRecordManager;
import Doctor.DiagnosisTreatmentRecord.DiagnosisTreatmentRecordView;
import Doctor.DoctorPassword.*;
import daos.AppointmentOutcomeRecordDao;
import daos.DiagnosisTreatmentRecordDao;
import daos.DoctorDao;
import daos.MedicalRecordDao;
import Doctor.MedicalRecord.*;
import daos.AppointmentOutcomeRecordDao;
import Doctor.Appointment.AppointmentOutcomeRecordView;

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
    private MedicalRecordDao medicalRecordDao;
    private AppointmentOutcomeRecordDao outcomeRecordDao;
    private Calendar doctorCalendar;
    private Department department;
    
    //CONSTRUCTOR
    public Doctor(String ID, String name, String department,String password, boolean IsFirstLogin, byte[] salt) 
    {
        super(ID, password, Role.valueOf("Doctor"),salt, IsFirstLogin); 
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
        DiagnosisTreatmentRecordManager treatmentRecordManager= new DiagnosisTreatmentRecordManager(treatmentRecordDao);
        DoctorAppointmentManager appointmentManager= new DoctorAppointmentManager(getHospitalId(), actionType,treatmentRecordManager);
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
            DoctorMedicalRecordManager manager= new DoctorMedicalRecordManager(medicalRecordDao);
            DoctorMedicalRecordView medicalRecordView= new DoctorMedicalRecordView(manager);
        }
        else
        {
            DiagnosisTreatmentRecordManager treatmentRecordManager= new DiagnosisTreatmentRecordManager(treatmentRecordDao);
            DiagnosisTreatmentRecordView treatmentRecordView= new DiagnosisTreatmentRecordView(treatmentRecordManager);
        }
    }

    //MANAGE APPOINTMENT OUTCOME RECORD
    public void ManageAppointmentOutcomeRecord()
    {
        AppointmentOutcomeRecordManager manager= new AppointmentOutcomeRecordManager(outcomeRecordDao);
        AppointmentOutcomeRecordView outcomeRecordView= new AppointmentOutcomeRecordView(manager,super.getHospitalId());
    }
    
    public void logout()
    {
        System.out.println("Doctor logged out.");
    }
}
