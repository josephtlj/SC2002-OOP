package models;
import java.util.UUID;

public class Appointment {
    private UUID appointmentId;       
    private String status;            // e.g. "pending", "confirmed", "canceled"
    private String type;              // e.g. "consultation", "X-ray", "blood test"
    private UUID patientId;           
    private UUID doctorId;            
    private int date;                 // convention: 01 June 2024 = 01062024
    private int time;                 // convention: 0000 to 2359

    // Constructor
    public Appointment(UUID patientId, UUID doctorId, String type, int date, int time) {
        this.appointmentId = UUID.randomUUID(); // Generate a unique ID for each appointment
        this.status = "pending";                // Default status
        this.type = type;                       
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    // Method to display appointment details
    public void displayAppointment() {
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Status: " + status);
        System.out.println("Type: " + type);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Date and Time: " + date + " " + time);
    }
}
