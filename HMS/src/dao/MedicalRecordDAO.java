package HMS.src.dao;

import HMS.src.models.MedicalRecord;
import HMS.src.models.MedicalRecord.BloodType;
import HMS.src.models.MedicalRecord.Gender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MedicalRecordDAO {

    private static final String CSV_FILE_PATH = "HMS/data/medicalRecordData.csv";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // Method to fetch the medical record by hospitalId from CSV
    public MedicalRecord getMedicalRecordByHospitalId(String hospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Split CSV line into values
                String[] values = line.split(",");

                // Check if the hospitalId matches the desired one
                if (values[8].equals(hospitalId)) { // Assuming hospitalId is in the 9th column (index 8)
                    return parseMedicalRecord(values);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        // If no record is found, return null
        return null;
    }

    // Helper method to parse CSV values into a MedicalRecord object
    private MedicalRecord parseMedicalRecord(String[] values) {
        try {
            UUID medicalRecordId = UUID.fromString(values[0]);
            String name = values[1];
            Date dob = DATE_FORMAT.parse(values[2]);
            Gender gender = Gender.valueOf(values[3].toUpperCase());
            String phoneNumber = values[4];
            String emailAddress = values[5];
            BloodType bloodType = BloodType.valueOf(values[6].replace("+", "_POS").replace("-", "_NEG"));
            // Additional parsing for pastDiagnosisTreatment can be added here if needed

            // Assuming Patient is passed as null here; you would associate it in the
            // controller or caller
            return new MedicalRecord(medicalRecordId, name, dob, gender, phoneNumber, emailAddress, bloodType, null, null);
        } catch (ParseException e) {
            System.err.println("Date format error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid enum value: " + e.getMessage());
        }
        return null;
    }
}
