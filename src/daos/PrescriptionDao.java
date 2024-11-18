package src.daos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import src.models.Prescription;

import src.interfaces.PrescriptionDaoInterface;

public class PrescriptionDao implements PrescriptionDaoInterface {
    private static String PRESCRIPTIONDB_PATH;;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PRESCRIPTIONDB_PATH = prop.getProperty("PRESCRIPTIONDB_PATH", "prescriptionDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PRESCRIPTIONDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Prescription prescription = parsePrescription(line);
                prescriptions.add(prescription);
            }

        } catch (IOException e) {
            System.err.println("Error reading medicine database: " + e.getMessage());
            e.printStackTrace();
        }

        return prescriptions;
    }

    // HELPER METHOD TO PARSE A PRESCRIPTION OBJECT FROM A CSV LINE
    private Prescription parsePrescription(String line) {
        try {
            String[] fields = line.split(",");
            if (fields.length != 4) {
                System.err.println("Invalid record format: " + line);
                return null;
            }

            UUID prescriptionId = UUID.fromString(fields[0]);
            String medicineName = fields[1];
            Prescription.Status status = Prescription.Status.valueOf(fields[2]);
            String medicineId = fields[3];

            return new Prescription(prescriptionId, medicineName, status, medicineId);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing record: " + line + " - " + e.getMessage());
            return null;
        }
    }
}
