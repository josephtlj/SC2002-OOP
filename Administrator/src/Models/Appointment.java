package Administrator.src.Modelsdels;
import java.util.UUID;

public class Appointment {
    private UUID appointmentId;       // Unique ID for each appointment
    private String status;            // e.g., "pending", "confirmed", "canceled"
    private String type;              // e.g., "consultation", "X-ray", "blood test"
    private UUID patientId;           // Unique ID of the patient
    private UUID doctorId;            // Unique ID of the doctor
    private String dateTime;          // Date and time of the appointment

    // Constructor
    public Appointment(UUID patientId, UUID doctorId, String type, String dateTime) {
        this.appointmentId = UUID.randomUUID(); // Generate a unique ID for each appointment
        this.status = "pending";                // Default 
        this.type = type;                       //
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
    }

    // Getters and Setters
    public UUID getAppointmentId() {
        return appointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    // Method to display appointment details
    public void displayAppointment() {
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Status: " + status);
        System.out.println("Type: " + type);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Date and Time: " + dateTime);
    }
}