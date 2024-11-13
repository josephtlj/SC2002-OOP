import java.io.*;
import java.util.*;

public class Medicine 
{

    // Data members
    private UUID medicineId;
    private String medicineName;
    private int medicineQuantity;
    private int medicineAlert;
    private static final String medicineFile= "src/Medications.csv";
    private static List<Medicine> medicines = new ArrayList<>();

    //Default Constructor
    public Medicine() 
    {
        this.medicineId = UUID.randomUUID(); // Default to a random UUID
        this.medicineName = "";  // Default to empty string
        this.medicineQuantity = 0; // Default to 0 quantity
        this.medicineAlert = 0; // Default to 0 alert level
    }

    public Medicine(UUID medicineId, String medicineName, int medicineQuantity, int medicineAlert) 
    {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineQuantity = medicineQuantity;
        this.medicineAlert = medicineAlert;
    }

    
public static void loadMedicinesFromCSV() throws IOException {
    medicines.clear(); // Clear existing medicines before loading
    System.out.println("\n[DEV] Loading: " + medicineFile);

    try (BufferedReader br = new BufferedReader(new FileReader(medicineFile))) {
        String line;
        br.readLine(); // Skip header line

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",", -1); // Use -1 to keep empty trailing fields
            if (values.length < 4) {
                System.out.println("Skipping invalid line: " + line);
                continue; // Skip invalid lines
            }

            // Parse or generate UUID for medicine ID
            UUID medicineId;
            try {
                medicineId = UUID.fromString(values[0].trim());
            } catch (IllegalArgumentException e) {
                // Generate a new UUID if the format is invalid
                medicineId = UUID.randomUUID();
                System.out.println("[DEV] Invalid UUID format in CSV; generating new UUID for entry: " + line);
            }

            // Parse other values
            String medicineName = values[1].trim();
            int medicineQuantity = values[2].trim().isEmpty() ? 0 : Integer.parseInt(values[2].trim());
            int medicineAlert = values[3].trim().isEmpty() ? 0 : Integer.parseInt(values[3].trim());

            // Create a new Medicine object and add it to the list
            Medicine medicine = new Medicine(medicineId, medicineName, medicineQuantity, medicineAlert);
            medicines.add(medicine);
            System.out.println("[DEV] Loaded: " + medicine); // Debug output
        }
    }

    System.out.println("[DEV] Total medicines loaded: " + medicines.size()); // Debug output
}
    @Override
    public String toString() 
    {
        return "Medicine{" +
                "medicineId=" + medicineId +
                ", medicineName='" + medicineName + '\'' +
                ", medicineQuantity=" + medicineQuantity +
                ", medicineAlert=" + medicineAlert +
                '}';
    }

    public static void saveMedicinesToCSV() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(medicineFile))) {
            // Write header
            bw.write("medicineId,medicineName,medicineQuantity,medicineAlert");
            bw.newLine();

            for (Medicine medicine : medicines) {
                StringBuilder sb = new StringBuilder();
                sb.append(medicine.getMedicineID()).append(",");
                sb.append(medicine.getMedicineName()).append(",");
                sb.append(medicine.getMedicineQuantity()).append(",");
                sb.append(medicine.getMedicineAlert());
                
                bw.write(sb.toString());
                bw.newLine();
            }
        }

        System.out.println("[DEV] Medicines saved to CSV file: " + medicineFile);
    }

    public static List<Medicine> getMedications() 
    {
        return new ArrayList<>(medicines); // Return a copy to preserve encapsulation
    }


    // Getter for medicineId
    public UUID getMedicineID() 
    {
        return medicineId;
    }

    // Setter for medicineId
    public void setMedicineID(UUID medicineId) 
    {
        this.medicineId = medicineId;
    }

    // Getter for medicineName
    public String getMedicineName() 
    {
        return medicineName;
    }

    // Setter for medicineName
    public void setMedicineName(String medicineName) 
    {
        this.medicineName = medicineName;
    }

    // Getter for medicineQuantity
    public int getMedicineQuantity() 
    {
        return medicineQuantity;
    }

    // Setter for medicineQuantity
    public void setMedicineQuantity(int medicineQuantity) 
    {
        this.medicineQuantity = medicineQuantity;
    }

    // Getter for medicineAlert
    public int getMedicineAlert() 
    {
        return medicineAlert;
    }

    // Setter for medicineAlert
    public void setMedicineAlert(int medicineAlert) 
    {
        this.medicineAlert = medicineAlert;
    }
}
