// package done;
// import java.io.*;
// import java.util.*;

// public class MedicineDAO {

//     // Data members
//     private UUID medicineId;
//     private String medicineName;
//     private int medicineQuantity;
//     private int medicineAlert;
//     private static final String medicineFile = "Pharmacist_v2/Medicine.csv"; // File path for medicine data
//     private static List<MedicineDAO> medicines = new ArrayList<>(); // In-memory list of medicines

//     // Default Constructor
//     public MedicineDAO() {
//         this.medicineId = UUID.randomUUID(); // Default to a random UUID
//         this.medicineName = "";  // Default to empty string
//         this.medicineQuantity = 0; // Default to 0 quantity
//         this.medicineAlert = 0; // Default to 0 alert level
//     }

//     // Constructor with parameters
//     public MedicineDAO(UUID medicineId, String medicineName, int medicineQuantity, int medicineAlert) {
//         this.medicineId = medicineId;
//         this.medicineName = medicineName;
//         this.medicineQuantity = medicineQuantity;
//         this.medicineAlert = medicineAlert;
//     }

//     // Getter and Setter methods
//     public UUID getMedicineId() {
//         return medicineId;
//     }

//     public void setMedicineId(UUID medicineId) {
//         this.medicineId = medicineId;
//     }

//     public String getMedicineName() {
//         return medicineName;
//     }

//     public void setMedicineName(String medicineName) {
//         this.medicineName = medicineName;
//     }

//     public int getMedicineQuantity() {
//         return medicineQuantity;
//     }

//     public void setMedicineQuantity(int medicineQuantity) {
//         this.medicineQuantity = medicineQuantity;
//     }

//     public int getMedicineAlert() {
//         return medicineAlert;
//     }

//     public void setMedicineAlert(int medicineAlert) {
//         this.medicineAlert = medicineAlert;
//     }

//     // Override the toString() method to provide meaningful output
//     @Override
// public String toString() {
//     return String.format("| %-36s | %-15s | %-8d | %-11d |", 
//                          medicineId.toString(), medicineName, medicineQuantity, medicineAlert);
// }


//     // CRUD Operations: Load medicines from CSV
//     public static void loadMedicinesFromCSV() throws IOException {
//         medicines.clear(); // Clear existing medicines before loading
//         try (BufferedReader br = new BufferedReader(new FileReader(medicineFile))) {
//             String line;
//             br.readLine(); // Skip header line
//             while ((line = br.readLine()) != null) {
//                 String[] values = line.split(",", -1); // Use -1 to keep empty trailing fields
//                 if (values.length < 4) {
//                     System.out.println("Skipping invalid line: " + line);
//                     continue; // Skip invalid lines
//                 }
//                 UUID medicineId = UUID.fromString(values[0].trim());
//                 String medicineName = values[1].trim();
//                 int medicineQuantity = values[2].trim().isEmpty() ? 0 : Integer.parseInt(values[2].trim());
//                 int medicineAlert = values[3].trim().isEmpty() ? 0 : Integer.parseInt(values[3].trim());

//                 MedicineDAO medicine = new MedicineDAO(medicineId, medicineName, medicineQuantity, medicineAlert);
//                 medicines.add(medicine);
//             }
//         }
//     }

//     // CRUD Operations: Save medicines to CSV
//     public static void saveMedicinesToCSV() throws IOException {
//         try (BufferedWriter bw = new BufferedWriter(new FileWriter(medicineFile))) {
//             bw.write("medicineId,medicineName,medicineQuantity,medicineAlert");
//             bw.newLine();

//             for (MedicineDAO medicine : medicines) {
//                 StringBuilder sb = new StringBuilder();
//                 sb.append(medicine.getMedicineId()).append(",");
//                 sb.append(medicine.getMedicineName()).append(",");
//                 sb.append(medicine.getMedicineQuantity()).append(",");
//                 sb.append(medicine.getMedicineAlert());
//                 bw.write(sb.toString());
//                 bw.newLine();
//             }
//         }
//     }

//     // CRUD Operations: Find a medicine by ID
//     public static MedicineDAO findMedicineById(UUID medicineId) {
//         for (MedicineDAO medicine : medicines) {
//             if (medicine.getMedicineId().equals(medicineId)) {
//                 return medicine;
//             }
//         }
//         return null;  // Return null if not found
//     }

//     // CRUD Operations: Update medicine quantity
//     public static void updateMedicine(MedicineDAO updatedMedicine) {
//         for (int i = 0; i < medicines.size(); i++) {
//             if (medicines.get(i).getMedicineId().equals(updatedMedicine.getMedicineId())) {
//                 medicines.set(i, updatedMedicine);  // Update the medicine in the list
//                 return;
//             }
//         }
//     }

//     // CRUD Operations: Delete medicine by ID
//     public static void deleteMedicine(UUID medicineId) {
//         medicines.removeIf(medicine -> medicine.getMedicineId().equals(medicineId));
//     }

//     // Get all medicines
//     public static List<MedicineDAO> getMedicines() {
//         return medicines;
//     }
// }
