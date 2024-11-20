package src.daos;

import java.io.*;
import java.util.*;
import src.utils.ENUM.AppointmentStatus;
import src.interfaces.DoctorAppointmentDaoInterface;
import src.models.Appointment;
import src.models.AppointmentTimeSlot;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DoctorAppointmentDao implements DoctorAppointmentDaoInterface {
    // ATTRIBUTES
    private static String DOCTORAPPOINTMENTSLOTSDB_PATH;
    private File doctorAppointmentSlotsFile;

    // CONSTRUCTOR
    public DoctorAppointmentDao(String ID) {
        if (ID.substring(0, 2).equals("PA")) {
            return;
        } else {
            // LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
            try (InputStream input = new FileInputStream("src/resources/config.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                DOCTORAPPOINTMENTSLOTSDB_PATH = prop.getProperty("DOCTORAPPOINTMENTSLOTSDB_PATH",
                        "data/Doctor/AppointmentSlots");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // LOCATE THE CORRECT CSV FILE WITH MATCHING ID
            File appointmentSlotsDir = new File(DOCTORAPPOINTMENTSLOTSDB_PATH);
            if (appointmentSlotsDir.exists() && appointmentSlotsDir.isDirectory()) {
                File[] files = appointmentSlotsDir.listFiles(file -> file.getName().equals(ID + "_appSlot.csv"));
                this.doctorAppointmentSlotsFile = files[0]; // ASSIGN MATCHING FILE

            }
        }

    }

    
    /** 
     * @param doctorID
     * @return List<Appointment>
     */
    // READ ALL APPOINTMENTSLOTS
    public List<Appointment> getAllAppointments(String doctorID) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(doctorAppointmentSlotsFile))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV and create a Appointment object
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 9) // Ensure the correct number of columns
                {
                    // Parse CSV fields
                    String appointmentId = parts[0];
                    String doctorId = parts[1];
                    String patientID = parts[2];
                    String date = parts[3];
                    String startTime = parts[4];
                    String endTime = parts[5];
                    String availability = parts[6];
                    String appointmentYesNo = parts[7];
                    String status = parts[8];

                    if (appointmentYesNo.equals("Yes")) {
                        // Create a Appointment object and add to the list
                        Appointment newAppointment = new Appointment(status, date, startTime, endTime, patientID,
                                doctorID);
                        appointments.add(newAppointment);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    
    /** 
     * @param date
     * @param availability
     * @return boolean
     */
    // UPDATE APPOINTMENT AVAILABILITY
    public boolean updateAppointmentAvailability(LocalDate date, String availability) {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(doctorAppointmentSlotsFile))) {
            // Read the header and add it to updatedLines
            String header = reader.readLine();
            updatedLines.add(header);

            // Iterate through the file and update the availability for the given date
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by commas

                if (parts.length == 9) // Ensure the correct number of columns
                {
                    // Parse the Date field (first column)
                    LocalDate lineDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    // If the date matches, update the availability column
                    if (lineDate.equals(date)) {
                        parts[6] = availability; // Update the "Availability" column

                        // If availability is set to "No", set dependent columns to "NA"
                        if (availability.equalsIgnoreCase("No")) {
                            parts[7] = "NA"; // Appointment
                            parts[2] = "NA"; // PatientID
                            parts[8] = "NA"; // Status
                        }
                    }
                    // Rebuild the line and add it to updatedLines
                    updatedLines.add(String.join(",", parts));
                }
            }

            // Write updated data back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(doctorAppointmentSlotsFile))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE APPOINTMENT STATUS
    public boolean updateAppointmentStatus(Appointment appointment) {
        // Retrieve appointment details
        AppointmentStatus status = appointment.getAppointmentStatus();
        LocalDate date = appointment.getAppointmentDate();
        AppointmentTimeSlot timeSlot = appointment.getAppointmentTimeSlot();
        String patientID = appointment.getPatientID();

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(doctorAppointmentSlotsFile))) {
            // Read the header and add it to updatedLines
            String header = reader.readLine();
            updatedLines.add(header);

            // Iterate through the file to find the matching appointment
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by commas

                if (parts.length == 9) // Ensure the correct number of columns
                {
                    // Parse the Date, Start Time, and End Time fields

                    LocalDate lineDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalTime startTime = LocalTime.parse(parts[4], DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime endTime = LocalTime.parse(parts[5], DateTimeFormatter.ofPattern("HH:mm"));

                    // If the appointment matches the date and time slot, update fields
                    if (lineDate.equals(date) &&
                            startTime.equals(timeSlot.getStartTime()) &&
                            endTime.equals(timeSlot.getEndTime())) {
                        switch (status) {
                            case PENDING:
                                parts[6] = "No"; // Availability
                                parts[7] = "Yes"; // Appointment
                                parts[2] = patientID; // PatientID
                                parts[8] = "PENDING"; // Status
                                break;

                            case CONFIRMED:
                                parts[8] = "CONFIRMED"; // Change status to CONFIRMED
                                break;

                            case DECLINED:
                                parts[6] = "Yes"; // Availability
                                parts[7] = "No"; // Appointment
                                parts[2] = "NA"; // PatientID
                                parts[8] = "NA"; // Status
                                break;

                            default:
                                break; // No changes for other statuses
                        }
                    }
                    // Rebuild the line and add it to updatedLines
                    updatedLines.add(String.join(",", parts));
                }
            }

            // Write the updated data back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(doctorAppointmentSlotsFile))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAvailableAppointmentSlots() {
        List<Appointment> availableSlots = new ArrayList<>();

        // Load the configuration from config.properties
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            DOCTORAPPOINTMENTSLOTSDB_PATH = prop.getProperty("DOCTORAPPOINTMENTSLOTSDB_PATH",
                    "data/AppointmentSlots");
        } catch (IOException ex) {
            ex.printStackTrace();
            return availableSlots; // Return empty list if configuration fails
        }

        // Locate the directory containing all _appSlot.csv files
        File appointmentSlotsDir = new File(DOCTORAPPOINTMENTSLOTSDB_PATH);
        if (!appointmentSlotsDir.exists() || !appointmentSlotsDir.isDirectory()) {
            System.err.println("Directory does not exist: " + DOCTORAPPOINTMENTSLOTSDB_PATH);
            return availableSlots; // Return empty list if directory is not found
        }

        // Get all files ending with _appSlot.csv
        File[] files = appointmentSlotsDir.listFiles(file -> file.getName().endsWith("_appSlot.csv"));
        if (files == null || files.length == 0) {
            System.err.println("No appointment slot files found in the directory.");
            return availableSlots; // Return empty list if no files are found
        }

        // Parse each file
        for (File file : files) {
            String doctorId = file.getName().split("_")[0];
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;

                // Skip the header row
                br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(","); // Assuming fields are comma-separated
                    if (parts.length == 9) { // Ensure correct format
                        // Extract relevant fields
                        String availability = parts[6].trim();
                        String appointment = parts[7].trim();

                        // Check conditions
                        if ("Yes".equalsIgnoreCase(availability) && "No".equalsIgnoreCase(appointment)) {
                            // Extract other fields for creating AppointmentTimeSlot
                            String date = parts[3].trim();
                            String startTime = parts[4].trim();
                            String endTime = parts[5].trim();

                            // Create an AppointmentTimeSlot object
                            // AppointmentTimeSlot slot = new AppointmentTimeSlot(
                            // LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            // startTime,
                            // endTime);

                            Appointment slot = new Appointment("CONFIRMED", date, startTime, endTime, "NA", doctorId);
                            availableSlots.add(slot);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + file.getName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        }

        return availableSlots;
    }

}