package appointment;

public class ViewAppointmentSlots {
    private DailyAppointmentSlots dailySlots; // A reference to the daily slots container

    // Constructor
    public ViewAppointmentSlots(DailyAppointmentSlots dailySlots) {
        this.dailySlots = dailySlots; // Link to the day's appointment slots
    }

    // Method to view all slots
    public void viewAllSlots() {
        System.out.println("=============================================================");
        System.out.println("| Viewing Appointment Slots for Date: " + dailySlots.getDate());
        System.out.println("| Doctor ID: " + dailySlots.getDoctorId());
        System.out.println("| Hourly Slots:");
        for (DailyAppointmentSlots.HourlySlot slot : dailySlots.getHourlySlots()) {
            System.out.print("  - Time: " + slot.getTime() + " | Available: " + slot.isAvailable());
            if (!slot.isAvailable() && slot.getAppointment() != null) {
                System.out.print(" | Appointment Details: ");
                slot.getAppointment().displayAppointment();
            }
            System.out.println();
        }
        System.out.println("=============================================================");
    }

    // Method to view available slots only
    public void viewAvailableSlots() {
        System.out.println("=============================================================");
        System.out.println("| Viewing Available Appointment Slots for Date: " + dailySlots.getDate());
        System.out.println("| Doctor ID: " + dailySlots.getDoctorId());
        System.out.println("| Hourly Slots:");
        for (DailyAppointmentSlots.HourlySlot slot : dailySlots.getHourlySlots()) {
            if (slot.isAvailable()) {
                System.out.println("  - Time: " + slot.getTime() + " | Available: true");
            }
        }
        System.out.println("=============================================================");
    }

    // Method to view booked slots only
    public void viewBookedSlots() {
        System.out.println("=============================================================");
        System.out.println("| Viewing Booked Appointment Slots for Date: " + dailySlots.getDate());
        System.out.println("| Doctor ID: " + dailySlots.getDoctorId());
        System.out.println("| Hourly Slots:");
        for (DailyAppointmentSlots.HourlySlot slot : dailySlots.getHourlySlots()) {
            if (!slot.isAvailable() && slot.getAppointment() != null) {
                System.out.print("  - Time: " + slot.getTime() + " | Appointment Details: ");
                slot.getAppointment().displayAppointment();
            }
        }
        System.out.println("=============================================================");
    }
}
