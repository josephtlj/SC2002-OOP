package src.models;

import java.time.LocalDate;
import java.util.UUID;

public class AppointmentOutcomeRecord {
    private UUID appointmentRecordId;
    private String appointmentId;
    private LocalDate date;
    private String serviceType;
    private String consultationNotes;
    private String medicationStatus;

    // Constructor
    public AppointmentOutcomeRecord(UUID appointmentRecordId, String appointmentId, LocalDate date, String serviceType,
            String notes) {
        this.appointmentRecordId = appointmentRecordId;
        this.appointmentId = appointmentId;
        this.date = date;
        this.serviceType = serviceType;
        this.medicationStatus = "Pending"; // Default status
        this.consultationNotes = notes;
    }

    
    /** 
     * @return UUID
     */
    // Getters and Setters for attributes
    public UUID getAppointmentRecordId() {
        return this.appointmentRecordId;
    }

    public void setAppointmentRecordId(UUID appointmentRecordId) {
        this.appointmentRecordId = appointmentRecordId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setConsultationNotes(String notes) {
        this.consultationNotes = notes;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setMedicationStatus(String status) {
        this.medicationStatus = status;
    }

    public String getMedicationStatus() {
        return medicationStatus;
    }
}