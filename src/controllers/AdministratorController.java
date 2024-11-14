package controllers;

import managers.AdministratorAppointmentManager;
import managers.StaffManager;
import managers.InventoryManager;
import managers.ReplenishmentRequestManager;
import Models.Appointment;
import Models.Inventory;
import Models.Staff;
import java.util.UUID;


import java.util.List;

public class AdministratorController {
    private StaffManager staffManager;
    private AdministratorAppointmentManager appointmentManager;
    private InventoryManager inventoryManager;

    // Constructor
    public AdministratorController(StaffManager staffManager, 
                                   AdministratorAppointmentManager appointmentManager, 
                                   InventoryManager inventoryManager) {
        this.staffManager = staffManager;
        this.appointmentManager = appointmentManager;
        this.inventoryManager = inventoryManager;
    }

    // Add a new staff member
    public void addStaff(Staff staff) {
        staffManager.addStaff(staff);
        System.out.println("Staff member added successfully.");
    }

    // Update an existing staff member
    public void updateStaff(Staff updatedStaff) {
        staffManager.updateStaff(updatedStaff);
        System.out.println("Staff member updated successfully.");
    }

    // Remove a staff member by hospital ID
    public void deleteStaff(String hospitalId) {
        staffManager.deleteStaff(hospitalId);
        System.out.println("Staff member removed successfully.");
    }

    // Find a specific staff member by their hospital ID
    public void findStaffById(String hospitalId) {
        Staff staff = staffManager.findStaffById(hospitalId);
        if (staff != null) {
            System.out.println("Staff found: " + staff);
        } else {
            System.out.println("Staff member not found.");
        }
    }

    // Display all staff members
    public void displayAllStaff() {
        List<Staff> staffList = staffManager.getAllStaff();
        if (!staffList.isEmpty()) {
            for (Staff staff : staffList) {
                System.out.println(staff);
            }
        } else {
            System.out.println("No staff members found.");
        }
    }

    // Filter staff by role, gender, age, or department
    public void filterStaff(String role, String gender, Integer age, String department) {
        List<Staff> filteredStaff = staffManager.filterStaff(role, gender, age, department);
        if (!filteredStaff.isEmpty()) {
            for (Staff staff : filteredStaff) {
                System.out.println(staff);
            }
        } else {
            System.out.println("No staff members match the given criteria.");
        }
    }

    // Display all appointments
    public void displayAllAppointments() {
        appointmentManager.displayAllAppointments();
    }

    // Display appointments by status
    public void displayAppointmentsByStatus(String status) {
        appointmentManager.displayAppointmentsByStatus(status);
    }

    // View the inventory
    public void viewInventory() {
        inventoryManager.viewInventory();
    }

    // Add a new inventory item
    public void addInventoryItem(String medicineName, int medicineQuantity, int medicineAlert) {
        inventoryManager.addInventoryItem(medicineName, medicineQuantity, medicineAlert);
        System.out.println("Inventory item added successfully.");
    }

    // Update an existing inventory item
    public void updateInventoryItem(String medicineId, int newQuantity) {
        inventoryManager.updateInventoryItem(medicineId, newQuantity);
        System.out.println("Inventory item updated successfully.");
    }

    // Remove an inventory item
    public void removeInventoryItem(String medicineId) {
        inventoryManager.removeInventoryItem(medicineId);
        System.out.println("Inventory item removed successfully.");
    }

    // View items below alert level in inventory
    public void viewAlertLevelInventory() {
        inventoryManager.viewAlertLevelInventory();
    }

    // Replenish stock for an inventory item
    public void replenishStock(String medicineId, int quantityToReplenish) {
        inventoryManager.replenishStock(medicineId, quantityToReplenish);
        System.out.println("Stock replenished successfully.");
    }
}
