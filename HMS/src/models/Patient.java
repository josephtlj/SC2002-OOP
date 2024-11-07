package HMS.src.models;

import HMS.src.dao.MedicalRecordDAO;

// import HMS.src.MedicalRecord;

public class Patient extends User {
    // INSTANCE VARIABLES
    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    // INSTANCE METHODS
    public Patient(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin, boolean isHashed) {
        super(hospitalId, password, role, salt, isFirstLogin, isHashed);
    }

    public MedicalRecord viewMedicalRecord() {
        return medicalRecordDAO.getMedicalRecordByHospitalId(getHospitalId());
    }

    // public void updatePersonalInformation(){

    // }

    // public AppointmentSlot[] viewAppointmentSlot(){

    // }

    // public void scheduleAppointment(){

    // }

    // public void rescheduleAppointment(){

    // }

    // public void cancelAppointment(){

    // }

    // public Appointment[] viewScheduledAppointment(){
    // return null;
    // }

    // public AppointmentOutcomeRecord[] viewPastAppointmentOutcomeRecord(){
    // return null;
    // }
}
