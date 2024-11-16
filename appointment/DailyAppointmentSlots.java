package appointment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DailyAppointmentSlots {
    private UUID dailySlotId;                 // Unique ID for this day's schedule
    private int date;                         // Date format: 01 June 2024 = 01062024
    private UUID doctorId;                    // Doctor assigned to these slots
    private List<HourlySlot> hourlySlots;     // List of hourly slots for the day

    // Constructor
    public DailyAppointmentSlots(int date, UUID doctorId) {
        this.dailySlotId = UUID.randomUUID(); // Generate unique ID for the day
        this.date = date;
        this.doctorId = doctorId;
        this.hourlySlots = new ArrayList<>(); // Initialize the list of hourly slots
        generateHourlySlots();                // Generate slots for 9:00 AM to 5:00 PM
    }

    // Inner class representing a single hourly slot
    public static class HourlySlot {
        private UUID slotId;
        private int time;
        private boolean isAvailable;
        private Appointment appointment;

        public HourlySlot(int time) {
            this.slotId = UUID.randomUUID();
            this.time = time;
            this.isAvailable = true;
            this.appointment = null;
        }

        // Getters and Setters
        public UUID getSlotId() { return slotId; }
        public int getTime() { return time; }
        public boolean isAvailable() { return isAvailable; }
        public void setAvailable(boolean available) { isAvailable = available; }
        public Appointment getAppointment() { return appointment; }
        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
            this.isAvailable = (appointment == null);
        }
    }

    // Generate hourly slots from 9:00 AM to 5:00 PM
    private void generateHourlySlots() {
        int startTime = 900; // Start at 9:00 AM
        for (int i = 0; i < 8; i++) {
            hourlySlots.add(new HourlySlot(startTime));
            startTime += 100; // Increment time by 1 hour
        }
    }

    // Getters
    public UUID getDailySlotId() { return dailySlotId; }
    public int getDate() { return date; }
    public UUID getDoctorId() { return doctorId; }
    public List<HourlySlot> getHourlySlots() { return hourlySlots; }
}
