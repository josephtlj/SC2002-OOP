package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CSVGenerator {
    public static void main(String[] args) {
        String header = "Date,Start Time,End Time,Availability,Appointment,PatientID,Status";

        // Patients list from the array
        List<String> patientIds = Arrays.asList(
                "PA6fe25d", "PA703270", "PA4d3e2b", "PA03c629", "PA440eab",
                "PA672bda", "PA698316", "PA7345d6", "PA42a038", "PA96a4df"
        );

        // Date formatter to format the date as dd/MM/yyyy
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Initialize start date and end date
        LocalDate startDate = LocalDate.of(2024, 1, 1); // Start date
        LocalDate endDate = LocalDate.of(2024, 12, 31); // End date
        LocalDate statusChangeDate = LocalDate.of(2024, 11, 16); // Date after which status can include PENDING
        Random random = new Random();

        // Generate six CSV files
        for (int fileNumber = 1; fileNumber <= 6; fileNumber++) {
            String fileName = String.format("D%03d_AppSlot.csv", fileNumber);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                // Write the header
                writer.write(header);
                writer.newLine();

                // Loop through each day in the range
                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                    // Initialize start and end time for each day
                    LocalTime startTime = LocalTime.of(9, 0); // Start time
                    LocalTime endTime = LocalTime.of(17, 0); // End time of the working day

                    // Generate appointments for the day
                    while (startTime.isBefore(endTime)) {
                        String startTimeStr = startTime.toString();
                        String endTimeStr = startTime.plusMinutes(30).toString();

                        // Random availability
                        boolean isAvailable = random.nextBoolean();
                        boolean hasAppointment = !isAvailable && random.nextBoolean(); // If not available, maybe an appointment

                        String patientId = hasAppointment ? patientIds.get(random.nextInt(patientIds.size())) : "NA";
                        String availability = isAvailable ? "Yes" : "No";
                        String appointment = hasAppointment ? "Yes" : "No";

                        // Determine status
                        String status;
                        if (hasAppointment) {
                            if (date.isBefore(statusChangeDate)) {
                                status = "CONFIRMED"; // Before 16/11/2024, status is always CONFIRMED
                            } else {
                                status = random.nextBoolean() ? "CONFIRMED" : "PENDING"; // After 16/11/2024, mix of CONFIRMED and PENDING
                            }
                        } else {
                            status = "NA"; // No appointment, status is NA
                        }

                        // Write the row to the file
                        writer.write(String.join(",",
                                date.format(dateFormatter), // Format date as dd/MM/yyyy
                                startTimeStr,
                                endTimeStr,
                                availability,
                                appointment,
                                patientId,
                                status
                        ));
                        writer.newLine();

                        // Increment the time by 30 minutes
                        startTime = startTime.plusMinutes(30);
                    }
                }

                System.out.println("CSV file generated: " + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
