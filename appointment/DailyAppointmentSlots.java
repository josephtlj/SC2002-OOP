package appointment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DailyAppointmentSlots {
    private UUID dailySlotId;                   // Unique ID for this day's schedule
    private int date;                           // Date format: 01 June 2024 = 01062024        
    private UUID doctorId;                      // Doctor assigned to these slots 
    private List<HourlySlot> hourlySlots;       // List of 1-hour time slots for the day

    // Constructor
    public DailyAppointmentSlots(int date, UUID doctorId) {
        this.dailySlotId = UUID.randomUUID(); // Generate unique ID for the day
        this.date = date;
        this.doctorId = doctorId;
        this.hourlySlots = new ArrayList<>(); // Initialize the list of hourly slots
        generateHourlySlots();                // Generate slots for 9:00 AM to 5:00 PM
    }

    public static class HourlySlot {
        private UUID slotId;                 // Unique ID for each hourly slot
        private int time;                    // Time in 24-hour format (e.g., 0900, 1000)
        private boolean isAvailable;         // Availability of this slot
        private Appointment appointment;     // Appointment booked in this slot, if any

        public HourlySlot(int time) {
            this.slotId = UUID.randomUUID(); // Unique ID for each hourly slot
            this.time = time;
            this.isAvailable = true;         // Default: slot is available
            this.appointment = null;         // No appointment initially
        }

        // Getters and Setters
        public UUID getSlotId() {
            return slotId;
        }

        public int getTime() {
            return time;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        public Appointment getAppointment() {
            return appointment;
        }

        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
            this.isAvailable = (appointment == null); // Update availability
        }
    }

    // Generate 8 hourly slots for the day
    private void generateHourlySlots() {
        int startTime = 900;                    // Appointment Slots start from 9am each day
        for (int i = 0; i < 8; i++) {
            hourlySlots.add(new HourlySlot(startTime));
            startTime += 100; // Increment time by 1 hour (e.g., 0900 -> 1000)
        }   
    }

    // Getters
    public UUID getDailySlotId() {
        return dailySlotId;
    }

    public int getDate() {
        return date;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public List<HourlySlot> getHourlySlots() {
        return hourlySlots;
    }

    // Book an appointment in a specific hourly slot
    public boolean bookAppointment(Appointment appointment, int time) {
        for (HourlySlot slot : hourlySlots) {
            if (slot.getTime() == time && slot.isAvailable()) {
                slot.setAppointment(appointment); // Book the appointment
                return true;
            }
        }
        return false; // Slot not available
    }

    // Cancel an appointment in a specific hourly slot
    public boolean cancelAppointment(int time) {
        for (HourlySlot slot : hourlySlots) {
            if (slot.getTime() == time && !slot.isAvailable()) {
                slot.setAppointment(null); // Remove the appointment
                return true;
            }
        }
        return false; // No appointment found at this time
    }

    // Display details of all slots in the day
    public void displayDailySlots() {
        System.out.println("=============================================================");
        //System.out.println("| Daily Slot ID: " + dailySlotId);
        System.out.println("| Date: " + date);
        //System.out.println("| Doctor ID: " + doctorId);
        System.out.println("| Hourly Slots:");
        for (HourlySlot slot : hourlySlots) {
            System.out.print("  - Time: " + slot.getTime() + " | Available: " + slot.isAvailable());
            // if (!slot.isAvailable() && slot.getAppointment() != null) {
            //     System.out.print(" | Appointment Details: ");
            //     slot.getAppointment().displayAppointment();
            // }
            System.out.println();
        }
    }
}
