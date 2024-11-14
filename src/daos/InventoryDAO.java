package daos;

import Models.Inventory;  // Changed to use Inventory instead of InventoryItem
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryDAO {
    private static final String FILE_NAME = "Medicine_List.csv"; // The name of your CSV file

    // Save a list of Inventory objects to the CSV file
    public void saveAll(List<Inventory> inventoryList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Inventory item : inventoryList) {
                writer.println(toCsvString(item)); // Convert each Inventory object to CSV string
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all Inventory objects from the CSV file
    public List<Inventory> loadAll() {
        List<Inventory> inventoryList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inventoryList.add(fromCsvString(line)); // Convert CSV string back to Inventory object
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }

    // Convert an Inventory object to a CSV string
    private String toCsvString(Inventory item) {
        return item.getMedicineId() + "," +
               item.getMedicineName() + "," +
               item.getMedicineQuantity() + "," +
               item.getMedicineAlert();
    }

    private Inventory fromCsvString(String csv) {
    String[] parts = csv.split(",");
    String medicineId = parts[0]; // UUID is saved as a String in the CSV
    String medicineName = parts[1];
    int medicineQuantity = Integer.parseInt(parts[2]);
    int medicineAlert = Integer.parseInt(parts[3]);

    // Return a new Inventory object using the constructor with medicineName, medicineQuantity, and medicineAlert
    // The medicineId is ignored here as the constructor generates a new UUID
    Inventory inventory = new Inventory(medicineName, medicineQuantity, medicineAlert);
    //inventory.setMedicineId(UUID.fromString(medicineId)); // Set the medicineId to the one from CSV
    return inventory;
}

    // Add a new Inventory item to the CSV file
    public void addInventoryItem(Inventory item) {
        List<Inventory> inventoryList = loadAll();
        inventoryList.add(item);
        saveAll(inventoryList);
    }

    // Find an Inventory item by its medicineId
    public Inventory findInventoryItemById(String medicineId) {
        List<Inventory> inventoryList = loadAll();
        for (Inventory item : inventoryList) {
            if (item.getMedicineId().equals(medicineId)) {
                return item;
            }
        }
        return null;
    }

    // Update an Inventory item in the CSV file
    public void updateInventoryItem(Inventory updatedItem) {
        List<Inventory> inventoryList = loadAll();
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).getMedicineId().equals(updatedItem.getMedicineId())) {
                inventoryList.set(i, updatedItem);
                break;
            }
        }
        saveAll(inventoryList);
    }

    // Remove an Inventory item from the CSV file
    public void deleteInventoryItem(String medicineId) {
        List<Inventory> inventoryList = loadAll();
        inventoryList.removeIf(item -> item.getMedicineId().equals(medicineId));
        saveAll(inventoryList);
    }
}
