package dataGenerationCode;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PatientAppointmentGenerator {

    public static void main(String[] args) throws IOException {
        // Define patient IDs
        List<String> patientIds = Arrays.asList(
                "PA6fe25d", "PA703270", "PA4d3e2b", "PA03c629", "PA440eab",
                "PA672bda", "PA698316", "PA7345d6", "PA42a038", "PA96a4df"
        );

        // Define doctor IDs (corresponding to file names)
        List<String> doctorIds = Arrays.asList("D001", "D002", "D003", "D004", "D005", "D006");

        // Define the cutoff date
        LocalDate cutoffDate = LocalDate.of(2024, 11, 16);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Loop through each patient
        for (String patientId : patientIds) {
            String outputFileName = patientId + "_AppOutRec.csv";
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                // Write the header
                writer.write("Doctor ID,Appointment ID,Date,Service Type,Medication Status,Consultation Notes");
                writer.newLine();

                // Check each doctor's file
                for (String doctorId : doctorIds) {
                    String doctorFileName = doctorId + "_AppSlot.csv";
                    if (Files.exists(Paths.get(doctorFileName))) {
                        // Read the doctor's file
                        try (BufferedReader reader = new BufferedReader(new FileReader(doctorFileName))) {
                            String line = reader.readLine(); // Skip header
                            while ((line = reader.readLine()) != null) {
                                String[] columns = line.split(",");
                                String dateStr = columns[0];
                                String appointment = columns[4];
                                String status = columns[6];
                                String currentPatientId = columns[5];

                                // Check if the row matches the criteria
                                LocalDate appointmentDate = LocalDate.parse(dateStr, dateFormatter);
                                if (appointmentDate.isBefore(cutoffDate) &&
                                    "Yes".equals(appointment) &&
                                    "CONFIRMED".equals(status) &&
                                    patientId.equals(currentPatientId)) {
                                    
                                    // Generate Appointment ID
                                    String appointmentId = UUID.randomUUID().toString();

                                    // Randomly assign Service Type and Consultation Notes
                                    String serviceType = getRandomServiceType();
                                    String consultationNotes = getRandomConsultationNotes();

                                    // Medication Status
                                    String medicationStatus = "DISPENSED";

                                    // Write the row to the patient file
                                    writer.write(String.join(",",
                                            doctorId,
                                            appointmentId,
                                            dateStr,
                                            serviceType,
                                            medicationStatus,
                                            consultationNotes
                                    ));
                                    writer.newLine();
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("CSV file generated for patient: " + patientId);
        }
    }

    // Helper method to randomly choose a Service Type
    private static String getRandomServiceType() {
        List<String> serviceTypes = Arrays.asList("General Consultation", "X-Ray", "Blood Test");
        Random random = new Random();
        return serviceTypes.get(random.nextInt(serviceTypes.size()));
    }

    // Helper method to randomly choose Consultation Notes
    private static String getRandomConsultationNotes() {
        List<String> consultationNotes = Arrays.asList(
                "Need follow up appointment",
                "Follow up appointment not necessary"
        );
        Random random = new Random();
        return consultationNotes.get(random.nextInt(consultationNotes.size()));
    }
}

