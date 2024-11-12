import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentSlot {
    private UUID slotId;                     // Unique ID for each appointment slot
    private String dateTime;                 // Date and time of the slot
    private boolean isAvailable;             // Availability status of the slot
    private UUID doctorId;                   // ID of the doctor assigned to the slot
    private List<Appointment> appointments;  // List of appointments in this slot

    // Constructor
    public AppointmentSlot(String dateTime, UUID doctorId) {
        this.slotId = UUID.randomUUID();     // Generate a unique ID for each slot
        this.dateTime = dateTime;
        this.isAvailable = true;             // Default availability status
        this.doctorId = doctorId;
        this.appointments = new ArrayList<>(); // Initialize empty list of appointments
    }

    // Getters and Setters
    public UUID getSlotId() {
        return slotId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    // Method to add an appointment to the slot
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        this.isAvailable = false; // Once an appointment is added, set availability to false
    }

    // Method to remove an appointment from the slot
    public void removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        if (this.appointments.isEmpty()) {
            this.isAvailable = true; // Set availability to true if no appointments remain
        }
    }

    // Method to display slot details
    public void displaySlotDetails() {
        System.out.println("Slot ID: " + slotId);
        System.out.println("Date and Time: " + dateTime);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Available: " + isAvailable);
        if (!appointments.isEmpty()) {
            System.out.println("Appointments in Slot:");
            for (Appointment appointment : appointments) {
                appointment.displayAppointment();
            }
        }
    }
}
