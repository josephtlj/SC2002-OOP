package src.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import src.models.MedicalRecord;
import src.interfaces.MedicalRecordDaoInterface;

public class MedicalRecordDao implements MedicalRecordDaoInterface {
    private static String MEDICALRECORDDB_PATH;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            MEDICALRECORDDB_PATH = prop.getProperty("MEDICALRECORDDB_PATH", "medicalRecordDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public MedicalRecord getMedicalRecordByHospitalId(String hospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(MEDICALRECORDDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                MedicalRecord medicalRecord = parseMedicalRecord(line);
                if (medicalRecord != null && medicalRecord.getPatient().equals(hospitalId)) {
                    return medicalRecord;
                }
            }

            // IF PATIENT NOT FOUND
            return null;

        } catch (IOException e) {
            System.err.println("Error reading patient database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        List<String> lines = new ArrayList<>();
        boolean medicalRecordFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(MEDICALRECORDDB_PATH))) {
            // SKIP HEADER ROW
            String header = br.readLine();
            // KEEP HEADER ROW IN OUTPUT
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 9 && UUID.fromString(fields[0]).equals(medicalRecord.getMedicalRecordId())) {
                    // UPDATE MATCHING MEDICAL RECORD RECORD
                    String updatedLine = formatMedicalRecordToCSV(medicalRecord);
                    lines.add(updatedLine);
                    medicalRecordFound = true;
                } else {
                    // KEEP EXISTING MEDICAL RECORD RECORD
                    lines.add(line);
                }
            }

            if (!medicalRecordFound) {
                throw new IllegalArgumentException(
                        "Medical Record with medicalRecordId " + medicalRecord.getMedicalRecordId() + " not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the medical record database: " + e.getMessage());
            e.printStackTrace();
        }

        // UPDATE MEDICALRECORDDB.CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEDICALRECORDDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the medical record database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // HELPER METHOD TO PARSE A MEDICALRECORD OBJECT FROM A CSV LINE
    private MedicalRecord parseMedicalRecord(String line) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String[] fields = line.split(",");
            if (fields.length != 9) {
                System.err.println("Invalid record format: " + line);
                return null;
            }

            UUID medicalRecordId = UUID.fromString(fields[0]);
            String name = fields[1];
            Date dob = null;
            try {
                dob = sdf.parse(fields[2]); // Handle ParseException for date parsing
            } catch (java.text.ParseException e) {
                System.err.println("Invalid date format for line: " + line + " - " + e.getMessage());
            }
            MedicalRecord.Gender gender = MedicalRecord.Gender.valueOf(fields[3]);
            String phoneNumber = fields[4];
            String emailAddress = fields[5];
            MedicalRecord.BloodType bloodType = MedicalRecord.BloodType.fromLabel(fields[6]);
            // String pastDiagnosisTreatment = fields[7];
            String hospitalId = fields[8];

            return new MedicalRecord(medicalRecordId, name, dob, gender, phoneNumber, emailAddress, bloodType, null,
                    hospitalId);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing record: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // HELPER METHOD TO FORMAT MEDICAL RECORD OBJECT INTO CSV LINE
    private String formatMedicalRecordToCSV(MedicalRecord medicalRecord) {
        return String.join(",",
                medicalRecord.getMedicalRecordId().toString(),
                medicalRecord.getName(),
                new SimpleDateFormat("yyyy-MM-dd").format(medicalRecord.getDob()),
                medicalRecord.getGender().name(),
                medicalRecord.getPhoneNumber(),
                medicalRecord.getEmailAddress(),
                medicalRecord.getBloodType().getLabel(),
                null,
                // String.join(";", medicalRecord.getPastDiagnosisTreatment()),
                medicalRecord.getPatient());
    }

}
