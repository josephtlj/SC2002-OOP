import java.io.IOException;
import java.util.*;
public class pharmacist
{
    public static void main(String[] args) 
    {
        pharmacist pharmacist = new pharmacist(); // Create an instance of Pharmacist
        Scanner scanner = new Scanner(System.in);
        //List<AppointmentOutcomeRecord> appointmentRecords = new ArrayList<>();
        List<Medicine> medications = new ArrayList<>();
        UUID prescriptionID = UUID.randomUUID(); // Generate a unique ID
        String initialStatus = "Pending"; // Set an initial status (or another appropriate string)
        MedicinePrescription prescription = new MedicinePrescription(prescriptionID, initialStatus);
        Medicine medicine=new Medicine();

        while (true) 
        {
            System.out.println("PHARMACIST MENU");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice)
            {
                // case 1:
                //     pharmacist.viewAppointmentOutcomeRecords(appointmentRecords);
                //     break;
                
                case 2:
                    String newStatus = "DISPENSED";
                    pharmacist.updatePrescriptionStatus(prescription, newStatus);
                    break;
                
                case 3:
                    pharmacist.viewMedicineInventory();
                    break;
                
                case 4:
                    System.out.print("Enter quantity to request for replenishment: ");
                    int requestQuantity = scanner.nextInt();
                    pharmacist.submitReplenishmentRequest(medicine, requestQuantity);
                    break;
                
                case 5:
                    System.out.println("Logging out and returning to home screen.");
                    return;
                
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    // public void viewAppointmentOutcomeRecords(List<AppointmentOutcomeRecord> records) 
    // {
    //     System.out.println("=== Appointment Outcome Records ===");
    //     for (AppointmentOutcomeRecord record : records) 
    //     {
    //         System.out.println("Record ID: " + record.getRecordID());
    //         System.out.println("Prescribed Medication: " + record.getPrescribedMedication());
    //         System.out.println("Consultation Notes: " + record.getConsultationNote());
    //         System.out.println("-------------------------------");
    //     }
    // }

    public void updatePrescriptionStatus(MedicinePrescription prescription, String newStatus) 
    {
        System.out.println("Updating Prescription Status.");
        prescription.setStatus(newStatus);
        System.out.println("Prescription ID: " + prescription.getPrescriptionId() + " updated to: " + newStatus);
    }

    public void viewMedicineInventory() 
    {
        System.out.println("=== Medication Inventory ===");

        try {
            // Load medicines from CSV file
            Medicine.loadMedicinesFromCSV();
            
            // Access the loaded medicines list
            List<Medicine> medicines = Medicine.getMedications(); 

            // Display each medicine in the inventory
            for (Medicine medicine : medicines) {
                System.out.println("Medicine ID: " + medicine.getMedicineID());
                System.out.println("Name: " + medicine.getMedicineName());
                System.out.println("Stock Level: " + medicine.getMedicineQuantity());
                System.out.println("Minimum Alert Level: " + medicine.getMedicineAlert());
                System.out.println("-------------------------------");
            }
        } catch (IOException e) 
        {
            System.out.println("Error loading medicines from CSV: " + e.getMessage());
        }
    }
    public void submitReplenishmentRequest(Medicine medicine, int requestQuantity) 
    {
        if (requestQuantity <= 0) 
        {
            System.out.println("Invalid request quantity. Must be greater than zero.");
            return;
        }
        ReplenishmentRequest request = new ReplenishmentRequest();
        request.setRequestId(UUID.randomUUID());
        request.setRequestedQuantity(requestQuantity);
        request.setStatus("Pending");

        System.out.println("Replenishment request for " + medicine.getMedicineName() + " submitted.");
        System.out.println("Requested Quantity: " + requestQuantity);
        System.out.println("Request Status: " + request.getStatus());
    }

}