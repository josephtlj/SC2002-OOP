package outdated;

public class CancelAppointment {
    private DailyAppointmentSlots dailySlots;

    public CancelAppointment(DailyAppointmentSlots dailySlots) {
        this.dailySlots = dailySlots;
    }

    public boolean cancelAppointment(int time) {
        for (DailyAppointmentSlots.HourlySlot slot : dailySlots.getHourlySlots()) {
            if (slot.getTime() == time && !slot.isAvailable()) {
                slot.setAppointment(null); // Remove the appointment
                return true;
            }
        }
        return false; // No appointment found at this time
    }
}