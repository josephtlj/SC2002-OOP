
package src.data.AppointmentOutcomeRecord;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class CSVFileGenerator {

    public static void main(String[] args) {
        // Patient IDs
        String[] patientIDs = {
                "PA6fe25d", "PA703270", "PA4d3e2b", "PA03c629", "PA440eab",
                "PA672bda", "PA698316", "PA7345d6", "PA42a038", "PA96a4df"
        };

        // Define the start and end date range
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 16);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Loop over the 10 patients
        for (String patientID : patientIDs) {
            // Read the doctor and date information from the TreatRec file
            List<String[]> treatRecData = readTreatRecCSV(patientID);

            // Generate the AppOutRec CSV file using the data from TreatRec
            generateAppOutRecCSV(patientID, treatRecData, dateFormatter);
        }
    }

    // Method to read TreatRec CSV
    private static List<String[]> readTreatRecCSV(String patientID) {
        List<String[]> treatRecData = new ArrayList<>();
        String treatRecFileName = "DiagnosisTreatmentRecords/" + patientID + "_TreatRec.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(treatRecFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                treatRecData.add(columns);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return treatRecData;
    }

    // Method to generate AppOutRec CSV based on TreatRec data
    private static void generateAppOutRecCSV(String patientID, List<String[]> treatRecData,
            DateTimeFormatter dateFormatter) {
        // File name for the AppOutRec file
        String appOutRecFileName = patientID + "_AppOutRec.csv";

        try (PrintWriter writer = new PrintWriter(new File(appOutRecFileName))) {
            // Write header
            writer.println("Doctor ID,Appointment ID,Date,Service Type,Medication Status,Consultation Notes");

            // Randomize consultation notes
            Random rand = new Random();

            // Loop through the TreatRec data to generate matching AppOutRec data
            for (String[] treatRec : treatRecData) {
                String doctorID = treatRec[0]; // Doctor ID
                String date = treatRec[1]; // Date in dd/MM/yyyy format

                // Generate unique Appointment ID (similar to UUID)
                String appointmentID = UUID.randomUUID().toString();

                // Generate consultation notes (randomly "NIL" or "Need follow up appointment")
                String consultationNotes = rand.nextBoolean() ? "NIL" : "Need follow up appointment";

                // Fixed values
                String serviceType = "General Consultation";
                String medicationStatus = "Dispensed";

                // Write a new row
                writer.println(String.join(",",
                        doctorID,
                        appointmentID,
                        date,
                        serviceType,
                        medicationStatus,
                        consultationNotes));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}