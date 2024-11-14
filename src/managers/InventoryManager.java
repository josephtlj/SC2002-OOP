package managers;

import daos.InventoryDAO;
import Models.Inventory;

import java.util.List;

public class InventoryManager {
    private InventoryDAO inventoryDAO;

    // Constructor to initialize the InventoryDAO
    public InventoryManager() {
        this.inventoryDAO = new InventoryDAO();
    }

    // View the current inventory
    public void viewInventory() {
        List<Inventory> inventoryList = inventoryDAO.loadAll();
        if (inventoryList.isEmpty()) {
            System.out.println("No items in the inventory.");
        } else {
            for (Inventory item : inventoryList) {
                System.out.println(item);
            }
        }
    }

    // Add a new inventory item to the stock
    public void addInventoryItem(String medicineName, int medicineQuantity, int medicineAlert) {
        Inventory newItem = new Inventory(medicineName, medicineQuantity, medicineAlert);
        inventoryDAO.addInventoryItem(newItem);
        System.out.println("Added new inventory item: " + newItem);
    }

    // Update the stock level of an existing inventory item
    public void updateInventoryItem(String medicineId, int newQuantity) {
        Inventory item = inventoryDAO.findInventoryItemById(medicineId);
        if (item != null) {
            item.setMedicineQuantity(newQuantity);
            inventoryDAO.updateInventoryItem(item);
            System.out.println("Updated inventory item: " + item);
        } else {
            System.out.println("Inventory item not found.");
        }
    }

    // Remove an inventory item from stock
    public void removeInventoryItem(String medicineId) {
        Inventory item = inventoryDAO.findInventoryItemById(medicineId);
        if (item != null) {
            inventoryDAO.deleteInventoryItem(medicineId);
            System.out.println("Removed inventory item: " + item);
        } else {
            System.out.println("Inventory item not found.");
        }
    }

    // Replenish the stock when a replenishment request is approved
    public void replenishStock(String medicineId, int quantityToReplenish) {
        Inventory item = inventoryDAO.findInventoryItemById(medicineId);
        if (item != null) {
            item.adjustQuantity(quantityToReplenish);  // Increase the quantity
            inventoryDAO.updateInventoryItem(item);
            System.out.println("Replenished stock for item: " + item);
        } else {
            System.out.println("Inventory item not found.");
        }
    }

    // Display items that are below the low stock alert threshold
    public void viewAlertLevelInventory() {
        List<Inventory> inventoryList = inventoryDAO.loadAll();
        boolean found = false;
        for (Inventory item : inventoryList) {
            if (item.isBelowAlertLevel()) {  // Checks if quantity is below the medicineAlert threshold
                System.out.println("Item below alert level: " + item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items are below the low stock alert level.");
        }
    }
}
