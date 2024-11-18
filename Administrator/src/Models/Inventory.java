package Administrator.src.Models;

// Inventory.java

import java.util.UUID;

public class Inventory {
    private UUID medicineId;           // Unique ID for each medicine
    private String medicineName;       // Name of the medicine
    private int medicineQuantity;      // Current stock quantity of the medicine
    private int medicineAlert;         // Low stock alert threshold

    // Constructor
    public Inventory(String medicineName, int medicineQuantity, int medicineAlert) {
        this.medicineId = UUID.randomUUID(); // Generates a unique ID
        this.medicineName = medicineName;
        this.medicineQuantity = medicineQuantity;
        this.medicineAlert = medicineAlert;
    }

    // Getters and Setters
    public UUID getMedicineId() {
        return medicineId;
    }
    public void setMedicineId() {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(int medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public int getMedicineAlert() {
        return medicineAlert;
    }

    public void setMedicineAlert(int medicineAlert) {
        this.medicineAlert = medicineAlert;
    }

    // Additional Methods

    // Checks if the current stock is below the alert threshold
    public boolean isBelowAlertLevel() {
        return medicineQuantity < medicineAlert;
    }

    // Adjusts the medicine quantity (e.g., when stock is used or replenished)
    public void adjustQuantity(int adjustment) {
        this.medicineQuantity += adjustment;
    }

    // Override toString() for easy display of item details
    @Override
    public String toString() {
        return "Medicine [ID=" + medicineId +
               ", Name=" + medicineName +
               ", Quantity=" + medicineQuantity +
               ", Alert Level=" + medicineAlert + "]";
    }
}

