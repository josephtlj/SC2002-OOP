

import java.io.*;
import java.util.*;

public class PrescriptionDAO {
    private static final String PRESCRIPTION_FILE = "Pharmacist_v2/Prescriptions.csv";
    private static List<PrescriptionDAO> prescriptions = new ArrayList<>();

    // Data members for each prescription record
    private UUID prescriptionId;
    private String medicineName;
    private String status;

    // Constructor
    public PrescriptionDAO(UUID prescriptionId, String medicineName, String status) {
        this.prescriptionId = prescriptionId;
        this.medicineName = medicineName;
        this.status = status;
    }

    // Default constructor for creating new records
    public PrescriptionDAO() {
        this.prescriptionId = UUID.randomUUID();
        this.medicineName = "";
        this.status = "Pending";
    }

    // Getter and setter methods for each data field
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

    // Method to load all prescriptions from CSV file
    public static List<PrescriptionDAO> loadAllPrescriptions() {
        prescriptions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(PRESCRIPTION_FILE))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1); // -1 to keep trailing empty fields
                if (values.length < 3) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                UUID prescriptionId = UUID.fromString(values[0].trim());
                String medicineName = values[1].trim();
                String status = values[2].trim();

                // Add the loaded prescription to the list
                PrescriptionDAO prescription = new PrescriptionDAO(prescriptionId, medicineName, status);
                prescriptions.add(prescription);
            }
        } catch (IOException e) {
            System.out.println("Error loading prescriptions: " + e.getMessage());
        }
        return prescriptions;
    }

    // Method to update the status of a prescription by ID
    public static boolean updatePrescriptionStatus(UUID prescriptionId, String newStatus) {
        // Load all prescriptions to make sure the list is up-to-date
        loadAllPrescriptions();

        // Find the prescription by ID and update its status
        for (PrescriptionDAO prescription : prescriptions) {
            if (prescription.getPrescriptionId().equals(prescriptionId)) {
                prescription.setStatus(newStatus);
                savePrescriptions(); // Save the updated prescriptions to CSV
                return true;
            }
        }
        return false; // Return false if the prescription was not found
    }

    // Method to save all prescriptions back to the CSV file
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

    // Method to find a prescription by ID
    public static PrescriptionDAO findPrescriptionById(UUID prescriptionId) {
        for (PrescriptionDAO prescription : prescriptions) {
            if (prescription.getPrescriptionId().equals(prescriptionId)) {
                return prescription;
            }
        }
        return null;
    }

    // Getter for all prescriptions
    public static List<PrescriptionDAO> getPrescriptions() {
        return prescriptions;
    }

    // Setter for prescriptions list (if needed)
    public static void setPrescriptions(List<PrescriptionDAO> prescriptions) {
        PrescriptionDAO.prescriptions = prescriptions;
    }
}
