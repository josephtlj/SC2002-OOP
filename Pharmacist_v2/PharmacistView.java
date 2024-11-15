
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PharmacistView 
{
    private final PharmacistController controller;
    private static Scanner scanner;

    public PharmacistView(PharmacistController controller) 
    {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() 
    {
        while (true) 
        {
            
            int choice;
            System.out.println("""
                
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                   Welcome Pharmacist                      |
                        =============================================================
                        Please select an option:
                        (1) View Appointment Outcome Record
                        (2) Update Prescription Status
                        (3) View Medication Inventory
                        (4) Submit Replenishment Request
                        (5) Logout
                                
                         """);
        
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) 
            {
                case 1:
                       controller.viewAppointmentOutcomeRecords();
                       break;
                case 2: 
                       updatePrescriptionStatus();
                       break;
                case 3: 
                       controller.viewMedicineInventory();
                       break;
                case 4: 
                       submitReplenishmentRequest();
                       break;
                case 5: 
                {
                       System.out.println("Logging out and returning to home screen."); 
                       return;
                }
                default: 
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
        
    }

    private void updatePrescriptionStatus() {
        System.out.println("Enter Prescription ID: ");
        
        // Clear any leftover newline character
        if (scanner.hasNextLine()) scanner.nextLine();
    
        String prescriptionId = scanner.nextLine().trim(); // Trim to remove any extra spaces
        
        // Check if the input is a valid UUID format
        try {
            UUID uuid = UUID.fromString(prescriptionId);
            //System.out.println("Enter new status: ");
            //String newStatus = scanner.nextLine().trim();
            String newStatus = "DISPENSED";
            
            // Call the controller to update the status
            controller.updatePrescriptionStatus(uuid, newStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Prescription ID format. Please enter a valid UUID.");
        }
    }
    
    public void submitReplenishmentRequest() {
        try {
            // Load medicines from CSV before attempting any replenishment
            MedicineDAO.loadMedicinesFromCSV();
            List<MedicineDAO> medicines = MedicineDAO.getMedicines();
    
            // If no medicines are available for replenishment, show a message
            if (medicines.isEmpty()) {
                System.out.println("No medicines available for replenishment.");
                return;
            }
    
            // Prompt the pharmacist to select a medicine
            System.out.println("Select the medicine you want to replenish:");
            for (int i = 0; i < medicines.size(); i++) {
                MedicineDAO medicine = medicines.get(i);
                System.out.println("(" + (i + 1) + ") " + medicine.getMedicineName() +
                        " - Current Quantity: " + medicine.getMedicineQuantity());
            }
    
            // User input for selecting a medicine
            System.out.print("Enter the number of the medicine: ");
            int choice = scanner.nextInt();
    
            // Validate the user's choice
            if (choice < 1 || choice > medicines.size()) {
                System.out.println("Invalid choice. Please try again.");
                return;
            }
    
            // Get the selected medicine
            MedicineDAO selectedMedicine = medicines.get(choice - 1);
    
            // Ask the pharmacist for the quantity they want to replenish
            System.out.print("Enter requested quantity for replenishment: ");
            int requestedQuantity = scanner.nextInt();
    
            // Validate the requested quantity
            if (requestedQuantity <= 0) {
                System.out.println("Invalid quantity. Please enter a positive number.");
                return;
            }
    
            // Update the medicine quantity
            int newQuantity = selectedMedicine.getMedicineQuantity() + requestedQuantity;
            selectedMedicine.setMedicineQuantity(newQuantity);
    
            // Update the medicine in the DAO (list)
            MedicineDAO.updateMedicine(selectedMedicine);
    
            // Save the updated medicines back to the CSV
            MedicineDAO.saveMedicinesToCSV();
    
            System.out.println("Replenishment request submitted successfully for " + selectedMedicine.getMedicineName() +
                    " with quantity: " + requestedQuantity + ". New quantity: " + newQuantity);
    
        } catch (Exception e) {
            System.out.println("Error while submitting replenishment request: " + e.getMessage());
        }
    }
    
    
}

    