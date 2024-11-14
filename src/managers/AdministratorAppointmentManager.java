package managers;

import Models.Appointment;
import java.util.List;
//assuming there is an appointmentManager from whoever is doing appointment and it is being stored
public class AdministratorAppointmentManager {
    private AppointmentManager appointmentManager; // AppointmentManager will hold and manage appointments

    // Constructor
    public AdministratorAppointmentManager(AppointmentManager appointmentManager) {
        this.appointmentManager = appointmentManager;
    }

    // Method to display all appointments (for the administrator)
    public void displayAllAppointments() {
        List<Appointment> appointments = appointmentManager.getAllAppointments();  // Call to get all appointments from AppointmentManager
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        for (Appointment appointment : appointments) {
            appointment.displayAppointment(); // Displaying appointment details using the displayAppointment method in Appointment class
        }
    }

    // Method to display appointments by status (e.g., "pending", "confirmed", etc.)
    public void displayAppointmentsByStatus(String status) {
        List<Appointment> filteredAppointments = appointmentManager.getAppointmentsByStatus(status); // Retrieve filtered appointments
        if (filteredAppointments.isEmpty()) {
            System.out.println("No appointments found with status: " + status);
            return;
        }

        for (Appointment appointment : filteredAppointments) {
            appointment.displayAppointment(); // Displaying appointment details
        }
    }

}
