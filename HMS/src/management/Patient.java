package HMS.src.management;

// import HMS.src.MedicalRecord;

public class Patient extends User {
    // INSTANCE VARIABLES

    // INSTANCE METHODS
    public Patient(String hospitalId, String password, Role role, byte[] salt, boolean isFirstLogin, boolean isHashed) {
        super(hospitalId, password, role, salt, isFirstLogin, isHashed);
    }

    // public MedicalRecord viewMedicalRecord(){
    //     return null;
    // }

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
    //     return null;
    // }

    // public AppointmentOutcomeRecord[] viewPastAppointmentOutcomeRecord(){
    //     return null;
    // }
}
