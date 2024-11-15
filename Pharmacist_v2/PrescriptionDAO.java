import java.io.*;
import java.util.*;

public class PrescriptionDAO {
    private static final String PRESCRIPTION_FILE = "Pharmacist_v2/Prescriptions.csv";
    private static List<PrescriptionDAO> prescriptions = new ArrayList<>();

    // Fields for each prescription record
    private UUID prescriptionId;
    private String medicineName;
    private String status;

    // Constructor
    public PrescriptionDAO(UUID prescriptionId, String medicineName, String status) {
        this.prescriptionId = prescriptionId;
        this.medicineName = medicineName;
        this.status = status;
    }

    // Getter and Setter methods
    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // CRUD Operations: Load all prescriptions from the CSV file
    public static List<PrescriptionDAO> loadAllPrescriptions() {
        prescriptions.clear(); // Clear the existing list before loading
        try (BufferedReader br = new BufferedReader(new FileReader(PRESCRIPTION_FILE))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1); // Split by commas, keeping empty fields
                if (values.length < 3) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                UUID prescriptionId = UUID.fromString(values[0].trim());
                String medicineName = values[1].trim();
                String status = values[2].trim();
                PrescriptionDAO prescription = new PrescriptionDAO(prescriptionId, medicineName, status);
                prescriptions.add(prescription);
            }
        } catch (IOException e) {
            System.out.println("Error loading prescriptions: " + e.getMessage());
        }
        return prescriptions;
    }

    // CRUD Operations: Save all prescriptions to the CSV file
    public static void savePrescriptions() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRESCRIPTION_FILE))) {
            bw.write("prescriptionId,medicineName,status");
            bw.newLine();

            for (PrescriptionDAO prescription : prescriptions) {
                StringBuilder sb = new StringBuilder();
                sb.append(prescription.getPrescriptionId()).append(",");
                sb.append(prescription.getMedicineName()).append(",");
                sb.append(prescription.getStatus());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving prescriptions: " + e.getMessage());
        }
    }

    // CRUD Operations: Find prescription by ID
    public static PrescriptionDAO findPrescriptionById(UUID prescriptionId) {
        for (PrescriptionDAO prescription : prescriptions) {
            if (prescription.getPrescriptionId().equals(prescriptionId)) {
                return prescription;
            }
        }
        return null;  // Return null if prescription is not found
    }

    // CRUD Operations: Update prescription status
    public static boolean updatePrescriptionStatus(UUID prescriptionId, String newStatus) {
        loadAllPrescriptions(); // Load existing prescriptions before updating
        for (PrescriptionDAO prescription : prescriptions) {
            if (prescription.getPrescriptionId().equals(prescriptionId)) {
                prescription.setStatus(newStatus);
                savePrescriptions();  // Save updated list back to file
                return true;
            }
        }
        return false;  // Return false if prescription was not found
    }
}
