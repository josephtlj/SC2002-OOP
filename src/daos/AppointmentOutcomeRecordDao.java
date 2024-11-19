package src.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import src.interfaces.AppointmentOutcomeRecordDaoInterface;
import src.models.Appointment;
import src.models.AppointmentOutcomeRecord;
import src.models.AppointmentTimeSlot;

public class AppointmentOutcomeRecordDao implements AppointmentOutcomeRecordDaoInterface {
    // ATTRIBUTES
    private static String APPOINTMENTOUTCOMERECORDDB_PATH;
    private File appointmentOutcomeRecordFile;
    private static String DOCTORAPPOINTMENTSLOTSDB_PATH;
    private File doctorAppointmentSlotsFile;

    // CONSTRUCTOR
    public AppointmentOutcomeRecordDao(String ID) {
        if (ID.charAt(0) == 'D') {
            return;
        }

        // LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            APPOINTMENTOUTCOMERECORDDB_PATH = prop.getProperty("APPOINTMENTOUTCOMERECORDDB_PATH",
                    "src/data/AppointmentOutComeRecord");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // CONSTRUCTOR
    // public AppointmentOutcomeRecordDao(int ID) {
    //     if (ID.charAt(0) == 'D') {
    //         return;
    //     }

    //     // LOCATE THE CORRECT CSV FILE WITH MATCHING ID
    //     File treatmentRecDir = new File(APPOINTMENTOUTCOMERECORDDB_PATH);
    //     if (treatmentRecDir.exists() && treatmentRecDir.isDirectory()) {
    //         File[] files = treatmentRecDir.listFiles(file -> file.getName().equals(ID + "_AppOutRec.csv"));
    //         this.appointmentOutcomeRecordFile = files[0]; // ASSIGN MATCHING FILE
    //     }
    // }

    public boolean updateAppointmentOutcomeRecord(AppointmentOutcomeRecord record) {

        List<String> updatedLines = new ArrayList<>();
        boolean recordUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(appointmentOutcomeRecordFile))) {
            List<String> lines = new ArrayList<>();

            // SKIP HEADER ROW
            String header = reader.readLine();
            // KEEP HEADER ROW IN OUTPUT
            lines.add(header);

            String line;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(","); // Assuming CSV fields are comma-separated
                if (fields.length > 0 && fields[0].equals(record.getAppointmentId())) {
                    // Found the matching row, update service type and consultation notes
                    fields[2] = record.getServiceType(); // Update service type
                    fields[3] = record.getConsultationNotes(); // Update consultation notes
                    line = String.join(",", fields); // Reconstruct the line
                    recordUpdated = true;
                }
                updatedLines.add(line); // Add line to the updated content
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Overwrite the file with updated content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentOutcomeRecordFile))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // FIND APPOINTMENT OUTCOME RECORD
    public AppointmentOutcomeRecord findAppointmentOutcomeRecord(String patientID, LocalDate date,
            AppointmentTimeSlot timeSlot) {
        if (appointmentOutcomeRecordFile == null) {
            System.err.println("Appointment outcome record file not initialized.");
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);

        try (BufferedReader br = new BufferedReader(new FileReader(appointmentOutcomeRecordFile))) {
            String line;

            // Skip header row if present
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) { // Ensure there are enough columns
                    String appointmentId = parts[1].trim();
                    String recordDate = parts[2].trim();
                    String serviceType = parts[3].trim();
                    String consultationNotes = parts[5].trim();

                    // Match date and time slot

                    if (recordDate.equals(formattedDate)) {
                        // Create and return the record if matches are found
                        return new AppointmentOutcomeRecord(
                                appointmentId,
                                date,
                                serviceType,
                                consultationNotes);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // WILL NEVER REACH HERE AS SERVICES ENSURES VALIDITY
    }

    // RETURN LIST OF COMPLETED APPOINTMENTS
    public List<Appointment> getCompletedAppointments(String doctorID) {
        List<Appointment> completedAppointments = new ArrayList<>();

        // LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            DOCTORAPPOINTMENTSLOTSDB_PATH = prop.getProperty("DOCTORAPPOINTMENTSLOTSDB_PATH",
                    "src/data/AppointmentSlots");
        } catch (IOException ex) {
            ex.printStackTrace();
            return completedAppointments; // Return empty list if configuration fails
        }

        // LOCATE THE CORRECT CSV FILE WITH MATCHING ID
        File appointmentSlotsDir = new File(DOCTORAPPOINTMENTSLOTSDB_PATH);
        if (appointmentSlotsDir.exists() && appointmentSlotsDir.isDirectory()) {
            File[] files = appointmentSlotsDir.listFiles(file -> file.getName().equals(doctorID + "_appSlot.csv"));
            if (files != null && files.length > 0) {
                doctorAppointmentSlotsFile = files[0]; // ASSIGN MATCHING FILE
            } else {
                System.err.println("No matching file found for ID: " + doctorID);
                return completedAppointments; // Return empty list if file is not found
            }
        } else {
            System.err.println("Appointment Slots directory does not exist.");
            return completedAppointments; // Return empty list if directory is not found
        }

        // PARSE THE CSV FILE TO FIND COMPLETED APPOINTMENTS
        try (BufferedReader br = new BufferedReader(new FileReader(doctorAppointmentSlotsFile))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate cutoffDate = LocalDate.parse("16/11/2024", formatter);

            // Skip the header row
            br.readLine();

            // Read and process each line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV fields are comma-separated

                if (parts.length == 7) {

                    String date = parts[0].trim();
                    String startTime = parts[1].trim();
                    String endTime = parts[2].trim();
                    String patientID = parts[5].trim();
                    String status = parts[6].trim();

                    // Parse date and check if it's before cutoff date and CONFIRMED
                    LocalDate appointmentDate = LocalDate.parse(date, formatter);
                    if (appointmentDate.isBefore(cutoffDate) && status.equals("CONFIRMED")) {
                        Appointment appointment = new Appointment(
                                status, date, startTime, endTime, patientID, doctorID);
                        completedAppointments.add(appointment);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return completedAppointments;
    }

    // public List<AppointmentOutcomeRecord> getAllAppointmentOutcomeRecords() {
    // List<AppointmentOutcomeRecord> appointmentOutcomeRecordList = new
    // ArrayList<>();

    // // Locate all CSV files in the directory
    // File directory = new File(APPOINTMENTOUTCOMERECORDDB_PATH);
    // if (!directory.exists() || !directory.isDirectory()) {
    // System.err.println("Directory does not exist: " +
    // APPOINTMENTOUTCOMERECORDDB_PATH);
    // return appointmentOutcomeRecordList;
    // }

    // File[] csvFiles = directory.listFiles((dir, name) -> name.endsWith(".csv"));
    // // Filter CSV files

    // System.out.println(csvFiles.length);

    // if (csvFiles == null || csvFiles.length == 0) {
    // System.err.println("No CSV files found in the directory.");
    // return appointmentOutcomeRecordList;
    // }

    // // Read each CSV file
    // for (File csvFile : csvFiles) {
    // try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
    // System.out.println("Reading file: " + csvFile.getName());

    // // Skip header row
    // br.readLine();

    // String line;
    // while ((line = br.readLine()) != null) {
    // AppointmentOutcomeRecord appointmentOutcomeRecord =
    // parseAppointmentOutcomeRecord(line);
    // if (appointmentOutcomeRecord != null) {
    // appointmentOutcomeRecordList.add(appointmentOutcomeRecord);
    // }
    // }
    // } catch (IOException e) {
    // System.err.println("Error reading file: " + csvFile.getName() + " - " +
    // e.getMessage());
    // e.printStackTrace();
    // }
    // }

    // return appointmentOutcomeRecordList;
    // }

    // // HELPER METHOD TO PARSE A APPOINTMENTOUTCOMERECORD OBJECT FROM A CSV LINE
    // private AppointmentOutcomeRecord parseAppointmentOutcomeRecord(String line) {
    // try {
    // String[] fields = line.split(",");
    // if (fields.length != 6) {
    // System.err.println("Invalid record format: " + line);
    // return null;
    // }

    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // String appointmentId = fields[1];
    // LocalDate date = LocalDate.parse(fields[2], formatter);
    // String serviceType = fields[3];
    // String medicationStatus = fields[4];
    // String consultationNotes = fields[5];

    // return new AppointmentOutcomeRecord(appointmentId, date, serviceType,
    // medicationStatus, consultationNotes);

    // } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
    // System.err.println("Error parsing record: " + line + " - " + e.getMessage());
    // return null;
    // }
    // }
}