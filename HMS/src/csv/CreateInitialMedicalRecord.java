package HMS.src.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import HMS.src.models.DiagnosisTreatmentRecord;
import HMS.src.models.MedicalRecord;
import HMS.src.models.Patient;
import HMS.src.models.User.Role;
import HMS.src.models.MedicalRecord.Gender;
import HMS.src.models.MedicalRecord.BloodType;

public class CreateInitialMedicalRecord {

    // Reads patients from CSV file and returns a list of Patient objects
    public static List<Patient> readPatientsFromCSV(String filePath) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstRow = true; // Flag to skip header

            while ((line = br.readLine()) != null) {
                if (firstRow) {
                    firstRow = false; // Skip header row
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String hospitalId = parts[0];
                    String hashedPassword = parts[1];
                    Role role = Role.valueOf(parts[2].toUpperCase());
                    byte[] salt = Base64.getDecoder().decode(parts[3]);
                    boolean isFirstLogin = Boolean.parseBoolean(parts[4]);

                    boolean isHashed = true;

                    // Create a new Patient object and add it to the list
                    Patient patient = new Patient(hospitalId, hashedPassword, role, salt, isFirstLogin, isHashed);
                    patients.add(patient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // Generates Medical Records for each Patient
    public static List<MedicalRecord> generateMedicalRecords(List<Patient> patients) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        // DUMMY DATA for fields other than Patient
        String[] dummyNames = { "James Hudson", "Emma Johnson", "Liam Smith", "Olivia Williams", "Noah Brown",
                "Ava Jones", "Sophia Miller", "Isabella Davis", "Mason Garcia", "Mia Martinez" };
        Gender[] genders = { Gender.MALE, Gender.FEMALE };
        BloodType[] bloodTypes = BloodType.values();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < patients.size(); i++) {
            // Generate random dummy data
            UUID medicalRecordId = null;
            String name = dummyNames[i % dummyNames.length];
            Date dob = generateRandomDOB();
            Gender gender = genders[i % genders.length];
            String phoneNumber = generateRandomPhoneNumber();
            String emailAddress = name.toLowerCase().replace(" ", ".") + "@gmail.com";
            BloodType bloodType = bloodTypes[i % bloodTypes.length];

            // Placeholder for past diagnosis and treatments
            DiagnosisTreatmentRecord[] pastDiagnosisTreatment = generatePlaceholderDiagnosisTreatment();
            // Create a new MedicalRecord for each Patient
            MedicalRecord medicalRecord = new MedicalRecord(
                    medicalRecordId, name, dob, gender, phoneNumber, emailAddress, bloodType, pastDiagnosisTreatment,
                    patients.get(i));

            medicalRecords.add(medicalRecord);
        }

        return medicalRecords;
    }

    private static Date generateRandomDOB() {
        Calendar calendar = Calendar.getInstance();
        int year = 1970 + (int) (Math.random() * 40); // DOB between 1970 and 2010
        int month = (int) (Math.random() * 12);
        int day = 1 + (int) (Math.random() * 28); // Simple day range for all months
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    private static String generateRandomPhoneNumber() {
        int num1 = (int) (Math.random() * 900) + 100;
        int num2 = (int) (Math.random() * 900) + 100;
        int num3 = (int) (Math.random() * 9000) + 1000;
        return String.format("%03d-%03d-%04d", num1, num2, num3);
    }

    private static DiagnosisTreatmentRecord[] generatePlaceholderDiagnosisTreatment() {
        // Placeholder for past diagnosis and treatment records
        // DiagnosisTreatmentRecord diagnosis = new DiagnosisTreatmentRecord("Cold",
        // "Rest and fluids", new Date());
        return null; // Array with one record for simplicity
    }

    public static void saveMedicalRecordsToCSV(List<MedicalRecord> medicalRecords, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV header
            writer.write(
                    "medicalRecordId,name,dob,gender,phonenumber,emailAddress,bloodType,pastDiagnosisTreatment,hospitalId\n");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Write each patient's data to the CSV
            for (MedicalRecord medicalRecord : medicalRecords) {
                String medicalRecordId = medicalRecord.getMedicalRecordId().toString();
                String name = medicalRecord.getName();
                String dob = dateFormat.format(medicalRecord.getDob());
                String gender = medicalRecord.getGender().name();
                String phoneNumber = medicalRecord.getPhoneNumber();
                String emailAddress = medicalRecord.getEmailAddress();
                String bloodType = medicalRecord.getBloodType().getLabel();
                String pastDiagnosisTreatment = formatDiagnosisTreatment(medicalRecord.getPastDiagnosisTreatment());
                String hospitalId = medicalRecord.getPatient().getHospitalId();

                // Write the patient details in CSV format
                writer.write(
                        String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", medicalRecordId, name, dob, gender, phoneNumber,
                                emailAddress, bloodType, pastDiagnosisTreatment, hospitalId));
            }

            System.out.println("Medical saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving patients to CSV: " + e.getMessage());
        }
    }

    private static String formatDiagnosisTreatment(DiagnosisTreatmentRecord[] records) {
        if (records == null || records.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (DiagnosisTreatmentRecord record : records) {
            sb.append(record.getDiagnosis()).append(":").append(record.getTreatment()).append(";");
        }
        // Remove trailing semicolon
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Step 1: Read patients from CSV
        String filePath = "HMS/data/patientData.csv";
        List<Patient> patients = readPatientsFromCSV(filePath);

        // Step 2: Generate Medical Records for each Patient
        List<MedicalRecord> medicalRecords = generateMedicalRecords(patients);

        // SAVE PATIENTS TO CSV FILE
        saveMedicalRecordsToCSV(medicalRecords, "HMS/data/medicalRecordData.csv");

    }
}
