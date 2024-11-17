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

    // HELPER METHOD TO PARSE A PATIENT OBJECT FROM A CSV LINE
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

}
