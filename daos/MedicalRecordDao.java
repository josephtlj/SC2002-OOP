package daos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import models.Patient;
import models.MedicalRecord;
import models.DiagnosisTreatmentRecord;

public class MedicalRecordDao {
    private PatientDao patientDao;

    public MedicalRecordDao() {
        this.patientDao = new PatientDao();
    }

    // LOAD NECESSARY PATHS FROM CONFIG.PROPERTIES
    private static String MEDICALRECORDDB_PATH;

    static {
        // LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("HMS/src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            MEDICALRECORDDB_PATH = prop.getProperty("MEDICALRECORDDB_PATH", "medicalRecordDB.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // READ ALL MEDICAL RECORDS
    public List<MedicalRecord> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(MEDICALRECORDDB_PATH))) {
            // Skip header row
            br.readLine();

            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 8) {
                    UUID medicalRecordId = UUID.fromString(fields[0]);
                    String name = fields[1];
                    Date dob = sdf.parse(fields[2]);
                    MedicalRecord.Gender gender = MedicalRecord.Gender.valueOf(fields[3].toUpperCase());
                    String phoneNumber = fields[4];
                    String emailAddress = fields[5];
                    MedicalRecord.BloodType bloodType = MedicalRecord.BloodType.valueOf(fields[6].toUpperCase());
                    DiagnosisTreatmentRecord[] pastDiagnosisTreatment = null; // You can implement parsing this field
                    String hospitalId = fields[7]; // Patient's hospitalId (foreign key)

                    Patient patient = patientDao.getPatientByHospitalId(hospitalId);

                    MedicalRecord medicalRecord = new MedicalRecord(medicalRecordId, name, dob, gender, phoneNumber,
                            emailAddress, bloodType, pastDiagnosisTreatment, patient);

                    medicalRecords.add(medicalRecord);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return medicalRecords;
    }

    // READ SPECIFIC PATIENT'S MEDICAL RECORD
    public MedicalRecord getMedicalRecordByHospitalId(String userHospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(MEDICALRECORDDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 9) {
                    UUID medicalRecordId = UUID.fromString(fields[0]);
                    String name = fields[1];
                    Date dob = sdf.parse(fields[2]);
                    MedicalRecord.Gender gender = MedicalRecord.Gender.valueOf(fields[3].toUpperCase());
                    String phoneNumber = fields[4];
                    String emailAddress = fields[5];
                    MedicalRecord.BloodType bloodType = MedicalRecord.BloodType.fromLabel(fields[6]);
                    DiagnosisTreatmentRecord[] pastDiagnosisTreatment = null;
                    String hospitalId = fields[8]; // Patient's hospitalId (foreign key)

                    System.out.println(hospitalId);
                    System.out.println(userHospitalId);

                    // IF HOSPITALID FOUND, RETURN MEDICAL RECORD DATA AS USER OBJECT
                    if (hospitalId.equals(userHospitalId)) {
                        Patient patient = patientDao.getPatientByHospitalId(hospitalId);

                        MedicalRecord medicalRecord = new MedicalRecord(medicalRecordId, name, dob, gender, phoneNumber,
                                emailAddress, bloodType, pastDiagnosisTreatment, patient);

                        return medicalRecord;
                    }
                }
            }
            // IF PATIENT NOT FOUND
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}