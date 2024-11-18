// package done;
// import java.util.List;

// import done.MedicineDAO;

// public class InventoryService {
//     public void viewInventory() {
//         try {
//             // Load medicines from the CSV
//             MedicineDAO.loadMedicinesFromCSV();
//             List<MedicineDAO> medicines = MedicineDAO.getMedicines();
    
//             // Display a header for the table
//             System.out.println("===================================================================================");
//             System.out.println("| Medicine ID                          | Name            | Quantity | Alert Level |");
//             System.out.println("===================================================================================");
    
//             // Display each medicine in the list
//             for (MedicineDAO medicine : medicines) {
//                 System.out.println(medicine);  // This calls the toString() method of MedicineDAO
//             }
    
//             // Display a footer for the table
//             System.out.println("===================================================================================");
    
//         } catch (Exception e) {
//             System.out.println("Error loading inventory: " + e.getMessage());
//         }
//     }
    
// }
