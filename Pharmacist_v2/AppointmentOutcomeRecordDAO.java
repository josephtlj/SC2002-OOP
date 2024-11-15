

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecordDAO {
    // Data members
    private String appointmentId;
    private LocalDate date;
    private String serviceType;
    private String consultationNotes;
    private String medicationStatus;

    // Constructor
     public AppointmentOutcomeRecordDAO(String appointmentId, LocalDate date, String serviceType) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.serviceType = serviceType;
        this.medicationStatus = "Pending";  // Default status
    }

    // Getters and setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public String getMedicationStatus() {
        return medicationStatus;
    }

    public void setMedicationStatus(String medicationStatus) {
        this.medicationStatus = medicationStatus;
    }

    // Static method to load appointment records from a CSV file
    public static List<AppointmentOutcomeRecordDAO> loadAllRecords(String filePath) {
        List<AppointmentOutcomeRecordDAO> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line if there is one

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1); // Split by commas, allowing empty fields
                if (values.length < 5) {
                    System.out.println("Skipping invalid line: " + line);
                    continue; // Skip invalid lines
                }

                // Parse values into fields
                String appointmentId = values[0].trim();
                LocalDate date = LocalDate.parse(values[1].trim());
                String serviceType = values[2].trim();
                String consultationNotes = values[3].trim();
                String medicationStatus = values[4].trim();

                // Create a new AppointmentOutcomeRecord and add to the list
                AppointmentOutcomeRecordDAO record = new AppointmentOutcomeRecordDAO(appointmentId, date, serviceType);
                record.setConsultationNotes(consultationNotes);
                record.setMedicationStatus(medicationStatus);

                records.add(record);
            }
        } catch (IOException e) {
            System.out.println("Error reading appointment records: " + e.getMessage());
        }

        return records;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId + "\n" +
               "Date: " + date + "\n" +
               "Service Type: " + serviceType + "\n" +
               "Consultation Notes: " + consultationNotes + "\n" +
               "Medication Status: " + medicationStatus + "\n" +
               "=============================================================";
    }
}
