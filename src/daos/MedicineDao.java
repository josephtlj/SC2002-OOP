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
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import src.models.Medicine;
import src.interfaces.MedicineDaoInterface;

public class MedicineDao implements MedicineDaoInterface {
    private static String MEDICINEDB_PATH;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            MEDICINEDB_PATH = prop.getProperty("MEDICINEDB_PATH", "medicineDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void createMedicine(String medicineName, int medicineQuantity, int medicineAlert) {
        Medicine medicine = new Medicine(UUID.randomUUID(), medicineName, medicineQuantity, medicineAlert);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEDICINEDB_PATH, true))) {

            String csvLine = formatMedicineToCSV(medicine);

            // WRITE NEW MEDICINE OBJECT TO MEDICINEDB.CSV
            writer.write(csvLine);
            writer.newLine();

            System.out.println("Medicine successfully added.");
        } catch (IOException e) {
            System.err.println("Error writing to the medicine database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Medicine> getAllMedicine() {
        List<Medicine> medicines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MEDICINEDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Medicine medicine = parseMedicine(line);
                medicines.add(medicine);
            }

        } catch (IOException e) {
            System.err.println("Error reading medicine database: " + e.getMessage());
            e.printStackTrace();
        }

        return medicines;
    }

    @Override
    public Medicine getMedicineByMedicineId(UUID medicineId) {
        try (BufferedReader br = new BufferedReader(new FileReader(MEDICINEDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Medicine medicine = parseMedicine(line);
                if (medicine != null && medicine.getMedicineId().equals(medicineId)) {
                    return medicine;
                }
            }

            // IF MEDICINE NOT FOUND
            return null;

        } catch (IOException e) {
            System.err.println("Error reading medicine database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateMedicine(Medicine medicine) {
        List<String> lines = new ArrayList<>();
        boolean medicineFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(MEDICINEDB_PATH))) {
            // SKIP HEADER ROW
            String header = br.readLine();
            // KEEP HEADER ROW IN OUTPUT
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 4 && UUID.fromString(fields[0]).equals(medicine.getMedicineId())) {
                    // UPDATE MATCHING MEDICINE RECORD
                    String updatedLine = formatMedicineToCSV(medicine);
                    lines.add(updatedLine);
                    medicineFound = true;
                } else {
                    // KEEP EXISTING MEDICINE RECORD
                    lines.add(line);
                }
            }

            if (!medicineFound) {
                throw new IllegalArgumentException(
                        "Medicine with medcineId " + medicine.getMedicineId() + " not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the medicine database: " + e.getMessage());
            e.printStackTrace();
        }

        // UPDATE MEDICINEDB.CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEDICINEDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the medicine database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMedicineByMedicineId(UUID medicineId) {
        List<String> lines = new ArrayList<>();
        boolean medicineFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(MEDICINEDB_PATH))) {
            // READ HEADER ROW
            String header = br.readLine();
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 4 && UUID.fromString(fields[0]).equals(medicineId)) {
                    // SKIP MATCHING MEDICINE WITH CORRESPONDING MEDICINEID
                    medicineFound = true;
                    continue;
                }
                lines.add(line);
            }

            if (!medicineFound) {
                throw new IllegalArgumentException("Medicine with medicineId " + medicineId + " not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the medicine database: " + e.getMessage());
            e.printStackTrace();
        }

        // UPDATE MEDICINEDB.CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEDICINEDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the medicine database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // HELPER METHOD TO PARSE A MEDICINE OBJECT FROM A CSV LINE
    private Medicine parseMedicine(String line) {
        try {
            String[] fields = line.split(",");
            if (fields.length != 4) {
                System.err.println("Invalid record format: " + line);
                return null;
            }

            UUID medicineId = UUID.fromString(fields[0]);
            String medicineName = fields[1];
            int medicineQuantity = Integer.parseInt(fields[2]);
            int medicineAlert = Integer.parseInt(fields[3]);

            return new Medicine(medicineId, medicineName, medicineQuantity, medicineAlert);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing record: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // HELPER METHOD TO FORMAT MEDICINE OBJECT INTO CSV LINE
    private String formatMedicineToCSV(Medicine medicine) {
        return String.join(",",
                medicine.getMedicineId().toString(),
                medicine.getMedicineName(),
                String.valueOf(medicine.getMedicineQuantity()),
                String.valueOf(medicine.getMedicineAlert()));
    }
}
