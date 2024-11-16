package outdated;

public class ScheduleAppointment {
    private DailyAppointmentSlots dailySlots;

    public ScheduleAppointment(DailyAppointmentSlots dailySlots) {
        this.dailySlots = dailySlots;
    }

    public boolean bookAppointment(Appointment appointment, int time) {
        for (DailyAppointmentSlots.HourlySlot slot : dailySlots.getHourlySlots()) {
            if (slot.getTime() == time && slot.isAvailable()) {
                slot.setAppointment(appointment); // Book the appointment
                return true;
            }
        }
        return false; // Slot not available
    }
}