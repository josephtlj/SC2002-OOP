package daos;

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
import Doctor.Appointment.Appointment;

import Doctor.Appointment.AppointmentOutcomeRecord;
import Doctor.Appointment.AppointmentTimeSlot;

public class AppointmentOutcomeRecordDao 
{
    // ATTRIBUTES
    private static String APPOINTMENTOUTCOMERECORDDB_PATH;
    private File appointmentOutcomeRecordFile;
    private static String DOCTORAPPOINTMENTSLOTSDB_PATH;
    private File doctorAppointmentSlotsFile;

    //CONSTRUCTOR
    public AppointmentOutcomeRecordDao(String ID)
    {
        //LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("resources/config.properties")) 
        {
            Properties prop = new Properties();
            prop.load(input);
            APPOINTMENTOUTCOMERECORDDB_PATH = prop.getProperty("APPOINTMENTOUTCOMERECORDDB_PATH", "data/AppointmentOutComeRecord");
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }

        //LOCATE THE CORRECT CSV FILE WITH MATCHING ID
        File treatmentRecDir = new File(APPOINTMENTOUTCOMERECORDDB_PATH);
        if (treatmentRecDir.exists() && treatmentRecDir.isDirectory()) 
        {
            File[] files = treatmentRecDir.listFiles(name -> name.equals(ID + "_AppOutRec.csv"));
            this.appointmentOutcomeRecordFile = files[0]; //ASSIGN MATCHING FILE
    
        }
    }

    

    public void updateAppointmentOutcomeRecord(AppointmentOutcomeRecord record) {
        if (appointmentOutcomeRecordFile == null) {
            System.err.println("Appointment outcome record file not initialized.");
            return;
        }
    
        List<String> updatedLines = new ArrayList<>();
        boolean recordUpdated = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(appointmentOutcomeRecordFile))) {
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
            return;
        }
    
        if (!recordUpdated) {
            System.err.println("No matching record found for Appointment ID: " + record.getAppointmentId());
            return;
        }
    
        // Overwrite the file with updated content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentOutcomeRecordFile))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


    //FIND APPOINTMENT OUTCOME RECORD 
    public AppointmentOutcomeRecord findAppointmentOutcomeRecord(String patientID, LocalDate date, AppointmentTimeSlot timeSlot) {
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
                if (parts.length >= 5) { // Ensure there are enough columns
                    String appointmentId = parts[0].trim();
                    String recordDate = parts[1].trim();
                    String serviceType = parts[2].trim();
                    String consultationNotes = parts[3].trim();
                    String medicationStatus = parts[4].trim();
    
                    // Match date and time slot
                    if (recordDate.equals(formattedDate)) {
                        // Create and return the record if matches are found
                        return new AppointmentOutcomeRecord(
                                appointmentId,
                                date,
                                serviceType,
                                consultationNotes
                        );
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        System.err.println("No matching appointment outcome record found.");
        return null;
    }
    
    

    //RETURN LIST OF COMPLETED APPOINTMENTS
    public List<Appointment> completedAppointments(String ID) {
        List<Appointment> completedAppointments = new ArrayList<>();
        
        // LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            DOCTORAPPOINTMENTSLOTSDB_PATH = prop.getProperty("DOCTORAPPOINTMENTSLOTSDB_PATH", "data/Doctor/AppointmentSlots");
        } catch (IOException ex) {
            ex.printStackTrace();
            return completedAppointments; // Return empty list if configuration fails
        }

        // LOCATE THE CORRECT CSV FILE WITH MATCHING ID
        File appointmentSlotsDir = new File(DOCTORAPPOINTMENTSLOTSDB_PATH);
        if (appointmentSlotsDir.exists() && appointmentSlotsDir.isDirectory()) {
            File[] files = appointmentSlotsDir.listFiles(name -> name.getName().equals(ID + "_appSlot.csv"));
            if (files != null && files.length > 0) {
                doctorAppointmentSlotsFile = files[0]; // ASSIGN MATCHING FILE
            } else {
                System.err.println("No matching file found for ID: " + ID);
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
                
                if (parts.length >= 6) { // Ensure there are at least 6 columns
                    String status = parts[0].trim();
                    String date = parts[1].trim();
                    String startTime = parts[2].trim();
                    String endTime = parts[3].trim();
                    String patientID = parts[4].trim();
                    String doctorID = parts[5].trim();

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
}
