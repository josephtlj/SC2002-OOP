import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Patient {
    private UUID patientId;                       // Unique ID for each patient
    private String name;
    private String dateOfBirth;
    private String gender;
    private String contactInfo;
    private String bloodType;
    private List<Appointment> appointments;       // List of appointments for the patient
    private List<AppointmentSlot> availableSlots; // List of available appointment slots

    // Constructor
    public Patient(String name, String dateOfBirth, String gender, String contactInfo, String bloodType) {
        this.patientId = UUID.randomUUID();       // Generate a unique ID for each patient
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;
        this.appointments = new ArrayList<>();    // Initialize empty list of appointments
        this.availableSlots = new ArrayList<>();  // Initialize empty list of available slots
    }

    // Getters and Setters
    public UUID getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<AppointmentSlot> getAvailableSlots() {
        return availableSlots;
    }

    // Methods for managing appointments
    public void viewAppointments() {
        System.out.println("Appointments for " + name + ":");
        for (Appointment appointment : appointments) {
            appointment.displayAppointment();
        }
    }

    public void viewAvailableSlots() {
        System.out.println("Available Slots:");
        for (AppointmentSlot slot : availableSlots) {
            slot.displaySlotDetails();
        }
    }

    public void scheduleAppointment(AppointmentSlot slot, UUID doctorId, String type) {
        if (slot.isAvailable()) {
            Appointment appointment = new Appointment(this.patientId, doctorId, type, slot.getDateTime());
            appointments.add(appointment);
            slot.addAppointment(appointment); // Link the appointment to the slot
            slot.setAvailable(false);         // Mark the slot as unavailable
            System.out.println("Appointment scheduled successfully.");
        } else {
            System.out.println("Selected slot is not available.");
        }
    }

    public void rescheduleAppointment(Appointment appointment, AppointmentSlot newSlot) {
        if (newSlot.isAvailable()) {
            AppointmentSlot oldSlot = findAppointmentSlot(appointment);
            if (oldSlot != null) {
                oldSlot.removeAppointment(appointment); // Remove from old slot
            }
            newSlot.addAppointment(appointment); // Add to new slot
            newSlot.setAvailable(false);         // Mark new slot as unavailable
            appointment.setDateTime(newSlot.getDateTime()); // Update appointment time
            System.out.println("Appointment rescheduled successfully.");
        } else {
            System.out.println("Selected slot is not available.");
        }
    }

    public void cancelAppointment(Appointment appointment) {
        AppointmentSlot slot = findAppointmentSlot(appointment);
        if (slot != null) {
            slot.removeAppointment(appointment); // Remove from slot
            appointments.remove(appointment);     // Remove from patient’s list
            System.out.println("Appointment canceled successfully.");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    // Helper method to find the slot associated with a specific appointment
    private AppointmentSlot findAppointmentSlot(Appointment appointment) {
        for (AppointmentSlot slot : availableSlots) {
            if (slot.getAppointments().contains(appointment)) {
                return slot;
            }
        }
        return null;
    }
}
