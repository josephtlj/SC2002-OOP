package appointment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.net.ssl.SSLSession;

public class PatientAppointmentView {

    private Scanner patientScanner = new Scanner(System.in);
    PatientAppointmentManager appointmentManager;

    public PatientAppointmentView(PatientAppointmentManager appointmentManager) {
        PatientAppointmentActionType actionType = appointmentManager.actionType;
        this.appointmentManager = appointmentManager;

        switch (actionType) {
            case VIEW:
                viewAvailableAppointments(); // with available doctors
                break;
            case SCHEDULE:
                scheduleAppointments();
                break;
            case RESCHEDULE:
                rescheduleAppointments();
                break;
            case CANCEL:
                cancelAppointments();
                break;
            case VIEW_SCHEDULED:
                viewScheduledAppointments();
                break;
            default:
                break;
        }
        return;
    }

    public int ViewChooseMonth() {
        int month = 0;
        while (true) {
            System.out.println("Please enter the number corresponding to the month (1 for January, 12 for December):");

            if (patientScanner.hasNextInt()) {
                month = patientScanner.nextInt();
                patientScanner.nextLine(); // Clear the newline character

                // Validate input
                if (month >= 1 && month <= 12) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 12.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                patientScanner.nextLine(); // Clear invalid input
            }
        }
        return month;
    }

    public int viewByDayOrMonth() {
        int choice = -1;
        do {
            try {
                System.out.println("Do you want to view by (1) Date or (2) Month? ");
                choice = patientScanner.nextInt();
                patientScanner.nextLine();

                switch (choice) {
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    default:
                        System.out.println("Please enter a valid choice!");
                        break;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please enter a valid integer only!\n");
                patientScanner.nextLine();
            }
        } while (choice != 1 || choice != 2);
        return 0; //never reach here
    }

    public List<Appointment> viewAvailableAppointmentsByDay() {
        boolean validInput = false;
        LocalDate date = null;
        do {
            try {
                System.out.print("Enter the date (dd/MM/yyyy): ");
                String dateInput = patientScanner.nextLine().trim();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateInput, dateFormatter);
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                validInput = false;
            }
        } while (!validInput);

        List<Appointment> appointments = appointmentManager.getAvailableAppointments(date);
        return appointments;
    }

    public List<Appointment> viewAvailableAppointmentsByMonth() {
        boolean validInput = false;
        int month = -1;
        do {
            try {
                System.out.print("Enter the month(1 for Jan and 12 for Dec): ");
                month = patientScanner.nextInt();
                patientScanner.nextLine();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input for month.");
                validInput = false;
            }
        } while (!validInput);

        List<Appointment> appointments = appointmentManager.getAvailableAppointments(month);
        return appointments;
    }

    public void viewAvailableAppointments() {
        // view by month or day
        int userChoice = viewByDayOrMonth();

        List<Appointment> availableAppointments = null;

        switch (userChoice) {
            case 1:
                availableAppointments = viewAvailableAppointmentsByDay();
                break;
            case 2:
                availableAppointments = viewAvailableAppointmentsByMonth();
                break;
        }

        // for loop through available appointments print each available time slot available
        for (Appointment appointment : availableAppointments) {
            System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s", "Appointment Date:", appointment.getAppointmentDate(), "Start Time:", appointment.getAppointmentTimeSlot().getStartTime(), "End Time:", appointment.getAppointmentTimeSlot().getEndTime());
        }

    }

    public void scheduleAppointment() {

    }

    public void rescheduleAppointment() {

    }

    public void cancelAppointment() {
        viewScheduledAppointment();
        System.out.print("Please select an appointment to cancel: ");

        
    }

    public void viewScheduledAppointment() {
        List<Appointment> scheduledAppointments = appointmentManager.getAppointments(Session.getCurrentUser().getHospitalId());

        for (Appointment appointment : scheduledAppointments) {
            System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s", "Appointment Date:", appointment.getAppointmentDate(), "Start Time:", appointment.getAppointmentTimeSlot().getStartTime(), "End Time:", appointment.getAppointmentTimeSlot().getEndTime());
        }
    }
}
