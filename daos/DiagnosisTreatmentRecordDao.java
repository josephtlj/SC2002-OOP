package daos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.time.*;
import java.time.format.DateTimeFormatter;

import Doctor.Appointment.Appointment;
import Doctor.Appointment.AppointmentTimeSlot;
import models.DiagnosisTreatmentRecord;

public class DiagnosisTreatmentRecordDao 
{
    // ATTRIBUTES
    private static String DIAGNOSISTREATMENTRECORDDB_PATH;
    private File diagnosisTreatmentRecordFile;

    // CONSTRUCTOR
    public DiagnosisTreatmentRecordDao(String ID) 
    {
        //LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("resources/config.properties")) 
        {
            Properties prop = new Properties();
            prop.load(input);
            DIAGNOSISTREATMENTRECORDDB_PATH = prop.getProperty("DIAGNOSISTREATMENTRECORDDB_PATH", "data/DiagnosisTreatmentRecords");
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }

        //LOCATE THE CORRECT CSV FILE WITH MATCHING ID
        File treatmentRecDir = new File(DIAGNOSISTREATMENTRECORDDB_PATH);
        if (treatmentRecDir.exists() && treatmentRecDir.isDirectory()) 
        {
            File[] files = treatmentRecDir.listFiles(name -> name.equals(ID + "_TreatRec.csv"));
            this.diagnosisTreatmentRecordFile = files[0]; //ASSIGN MATCHING FILE
    
        }

    }

    //UPDATE DIAGNOSIS TREATMENT RECORD FOR PATIENT ON SPECIFIC DATE
    public void updateDiagnosisTreatmentRecord(String diagnosis, String prescription, String treatmentPlan, LocalDate date)
    {
        File tempFile = new File(diagnosisTreatmentRecordFile.getParent(), "temp.csv");
    
        try (BufferedReader br = new BufferedReader(new FileReader(diagnosisTreatmentRecordFile));
         PrintWriter pw = new PrintWriter(tempFile)) 
         {
        
            // Read the header row and write it to the temporary file
            String header = br.readLine();
            pw.println(header);

            // Process each line to find the matching date and update fields
            String line;
            boolean recordUpdated = false;

            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 6) 
                { 
                    String doctorID = parts[0].trim();
                    String appointmentDateStr = parts[1].trim();
                    String appointmentTimeSlot = parts[2].trim();
                    String existingDiagnosis = parts[3].trim();
                    String existingPrescription = parts[4].trim();
                    String existingTreatmentPlan = parts[5].trim();

                    // Check if the appointment date matches the provided date
                    if (appointmentDateStr.equals(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) 
                    {
                        // Update fields if non-null values are provided
                        if (diagnosis != null) 
                        {
                        existingDiagnosis = diagnosis.trim();
                        }
                        if (prescription != null)
                        {
                            existingPrescription = prescription.trim();
                        }
                        if (treatmentPlan != null) 
                        {
                            existingTreatmentPlan = treatmentPlan.trim();
                        }
                        recordUpdated = true;
                    }

                    // Write the (possibly updated) line to the temporary file
                    pw.println(String.join(",", doctorID, appointmentDateStr, appointmentTimeSlot, existingDiagnosis, existingPrescription, existingTreatmentPlan));
                } else {
                    // If the line format is invalid, copy it as-is to preserve data integrity
                    pw.println(line);
                }
            }

            if (!recordUpdated) 
            {
            System.out.println("No matching record found for the specified date.");
            }

        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        // Replace the original file with the updated file
        if (!diagnosisTreatmentRecordFile.delete()) 
        {
            System.err.println("Failed to delete the original file.");
            return;
        }

        if (!tempFile.renameTo(diagnosisTreatmentRecordFile)) 
        {
            System.err.println("Failed to rename the temporary file.");
        }
    }

    //READ DIAGNOSIS TREATMENT RECORD FOR PATIENT ON SPECIFIC DATE
    public DiagnosisTreatmentRecord getDiagnosisTreatmentRecord(LocalDate date)
    {
        DiagnosisTreatmentRecord diagnosisRecord = null;

        try (BufferedReader br = new BufferedReader(new FileReader(diagnosisTreatmentRecordFile))) 
        {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV to locate the record with the given date
            String line;
            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 6) 
                {
                    String doctorID = parts[0].trim();
                    String appointmentDateStr = parts[1].trim();
                    String appointmentTimeSlot = parts[2].trim();
                    String diagnosis = parts[3].trim();
                    String prescription = parts[4].trim();
                    String treatmentPlan = parts[5].trim();

                    // Check if the appointment date matches the provided date
                    if (appointmentDateStr.equals(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) 
                    {
                        String[] timeSlots = appointmentTimeSlot.split(" - ");
                        String startTimeStr = timeSlots[0].trim();
                        String endTimeStr = timeSlots[1].trim();

                        // Create an Appointment object
                        Appointment appointment = new Appointment("confirmed", appointmentDateStr, startTimeStr, endTimeStr, null, doctorID);

                        // Create and return the DiagnosisTreatmentRecord object
                        diagnosisRecord = new DiagnosisTreatmentRecord(diagnosis, prescription, treatmentPlan, appointment);
                        break; // Exit the loop once the record is found
                    }
                }
            }
        }
        catch (IOException e) 
        {
        e.printStackTrace();
        }

        return diagnosisRecord; 
    }

    //READ ALL DIAGNOSIS TREATMENT RECORDS OF A PATIENT
    public List<DiagnosisTreatmentRecord> getAllDiagnosisTreatmentRecords(String patientID) 
    {
        List<DiagnosisTreatmentRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(diagnosisTreatmentRecordFile)))
        {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV and create a Diagnosis Treatment Record object
            String line;
            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 6) // Ensure the correct number of columns
                {
                    // Parse CSV fields
                String doctorID = parts[0].trim();
                String appointmentDateStr = parts[1].trim();
                String appointmentTimeSlot = parts[2].trim();
                String diagnosis = parts[3].trim();
                String prescription = parts[4].trim();
                String treatmentPlan = parts[5].trim();

                // Convert date and time strings to appropriate formats
                String appointmentDateStrTrimmed = appointmentDateStr.trim(); // Trim the date string
                String[] timeSlots = appointmentTimeSlot.split(" - ");
                String startTimeStr = timeSlots[0].trim(); // Trim the start time string
                String endTimeStr = timeSlots[1].trim(); // Trim the end time string

                // Create an Appointment object
                Appointment appointment = new Appointment("confirmed", appointmentDateStrTrimmed, startTimeStr, endTimeStr, patientID, doctorID);

                // Create a DiagnosisTreatmentRecord object
                DiagnosisTreatmentRecord treatRec = new DiagnosisTreatmentRecord(diagnosis, prescription, treatmentPlan, appointment);

                // Add the record to the list
                records.add(treatRec);
            }
        }
    } 
    catch (IOException e) 
    {
        e.printStackTrace();
    }

    return records;
    }

    // ADD NEW DIAGNOSIS TREATMENT RECORD
public void addDiagnosisTreatmentRecord(DiagnosisTreatmentRecord treatRec) {
    File tempFile = new File(diagnosisTreatmentRecordFile.getParent(), "temp.csv");

    try (
        BufferedReader br = new BufferedReader(new FileReader(diagnosisTreatmentRecordFile));
        PrintWriter pw = new PrintWriter(tempFile)
    ) {
        // Read the header and write it to the temp file
        String header = br.readLine();
        pw.println(header);

        // Collect all existing rows and add the new record
        List<String> allRecords = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            allRecords.add(line);
        }

        // Format the new DiagnosisTreatmentRecord as a CSV row
        Appointment appointment = treatRec.getAppointment();
        AppointmentTimeSlot timeSlot = appointment.getAppointmentTimeSlot(); 
        String diagnosis = treatRec.getDiagnosis() != null ? treatRec.getDiagnosis() : "NA";
        String prescription = treatRec.getPrescription() != null ? treatRec.getPrescription() : "NA";
        String treatmentPlan = treatRec.getTreatmentPlan() != null ? treatRec.getTreatmentPlan() : "NA";

        String newRecord= appointment.getDoctorID()+","+appointment.getAppointmentDate()+","+
        timeSlot.getStartTime().toString() + " - " + timeSlot.getEndTime().toString()+","+diagnosis+","+prescription+
        ","+treatmentPlan;
        
        // Add the new record to the list
        allRecords.add(newRecord);

        // Sort all records in ascending order based on date and time
        allRecords.sort((record1, record2) -> {
            String[] parts1 = record1.split(",");
            String[] parts2 = record2.split(",");
            
            LocalDate date1 = LocalDate.parse(parts1[1].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate date2 = LocalDate.parse(parts2[1].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (!date1.equals(date2)) {
                return date1.compareTo(date2); // Compare by date
            }

            // Compare by start time if dates are the same
            String[] timeSlot1 = parts1[2].split(" - ");
            String[] timeSlot2 = parts2[2].split(" - ");
            LocalTime startTime1 = LocalTime.parse(timeSlot1[0].trim(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime startTime2 = LocalTime.parse(timeSlot2[0].trim(), DateTimeFormatter.ofPattern("HH:mm"));

            return startTime1.compareTo(startTime2);
        });

        // Write sorted records to the temp file
        for (String record : allRecords) {
            pw.println(record);
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    // Replace the original file with the updated file
    if (!diagnosisTreatmentRecordFile.delete()) {
        System.err.println("Failed to delete the original file.");
        return;
    }

    if (!tempFile.renameTo(diagnosisTreatmentRecordFile)) {
        System.err.println("Failed to rename the temporary file.");
    }
}

    

}
