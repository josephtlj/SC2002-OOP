import java.util.List;
import java.util.UUID;


public class PharmacistController 
{
    private final AppointmentOutcomeRecordDAO appointmentDAO;
    private final MedicineDAO medicineDAO;
    private final ReplenishmentRequestDAO replenishmentDAO;
    private final PrescriptionDAO prescriptionDAO;
    private static final String APPOINTMENT_RECORDS_FILE = "Pharmacist_v2/Appointments.csv";

    public PharmacistController(AppointmentOutcomeRecordDAO appointmentDAO, MedicineDAO medicineDAO, ReplenishmentRequestDAO replenishmentDAO, PrescriptionDAO prescriptionDAO) 
    {
        this.appointmentDAO = appointmentDAO;
        this.medicineDAO = medicineDAO;
        this.replenishmentDAO = replenishmentDAO;
        this.prescriptionDAO= prescriptionDAO;
    }

    public void viewAppointmentOutcomeRecords() 
    {
        System.out.println("""
                
                        =============================================================
                        |              Appointment Outcome Record                   |
                        =============================================================
                               
                            """);
           List<AppointmentOutcomeRecordDAO> records = AppointmentOutcomeRecordDAO.loadAllRecords(APPOINTMENT_RECORDS_FILE);
           // Check if records were loaded successfully
                if (records.isEmpty()) 
                {
                System.out.println("No appointment records found.");
                return;
                }
                        
                // Display each record
                for (AppointmentOutcomeRecordDAO record : records) 
                {
                System.out.println(record); // Calls the overridden toString method in AppointmentOutcomeRecord
                }
    }

    public static void updatePrescriptionStatus(UUID prescriptionId, String newStatus) 
    {
        // Call the PrescriptionDAO to update the status
        boolean isUpdated = PrescriptionDAO.updatePrescriptionStatus(prescriptionId, newStatus);

        // Print the appropriate message based on whether the prescription was found and updated
        if (isUpdated) 
        {
            System.out.println("Prescription status updated to: " + newStatus);
        } else 
        {
            System.out.println("Prescription not found.");
        }
    }
    

    
    public static void viewMedicineInventory() 
    {
        System.out.println("""
                
        =============================================================
        |                      Medicine Inventory                   |
        =============================================================
               
            """);
            try {
                // Load medicines from CSV file if not already loaded
                MedicineDAO.loadMedicinesFromCSV();
    
                List<MedicineDAO> medicines = MedicineDAO.getMedicines();
    
                // If the list is empty, notify the pharmacist
                if (medicines.isEmpty()) 
                {
                    System.out.println("No medicines available in inventory.");
                    return;
                }
    
                // Display the list of medicines
                for (MedicineDAO medicine : medicines) 
                {
                    System.out.println("ID: " + medicine.getMedicineId());
                    System.out.println( "Name: " + medicine.getMedicineName());
                    System.out.println("Quantity: " + medicine.getMedicineQuantity());
                    System.out.println("Alert Level: " + medicine.getMedicineAlert());
                }
    
            } catch (Exception e) 
            {
                System.out.println("Error while viewing the medicine inventory: " + e.getMessage());
            }
            System.out.println("============================================================");
    }

    public void submitReplenishmentRequest(int requestedQuantity, UUID medicineId) {
        try {
            // Validate the requested quantity
            if (requestedQuantity <= 0) {
                System.out.println("Invalid quantity. Please enter a positive number.");
                return;
            }

            // Create a new replenishment request with "Pending" status
            ReplenishmentRequestDAO newRequest = new ReplenishmentRequestDAO(UUID.randomUUID(), medicineId, requestedQuantity, "Pending");

            // Add the request to the database (in-memory list)
            ReplenishmentRequestDAO.addRequest(newRequest);

            // Save updated list to the CSV file
            ReplenishmentRequestDAO.saveRequestsToCSV();

            System.out.println("Replenishment request submitted successfully with quantity: " + requestedQuantity);

        } catch (Exception e) {
            System.out.println("Error while submitting replenishment request: " + e.getMessage());
        }
}
}