package done;
// import java.util.Scanner;
// import java.util.UUID;

// public class ReplenishmentService {
//     public void submitReplenishmentRequest() {
//         Scanner scanner = new Scanner(System.in);
//         System.out.print("Enter Medicine ID for replenishment: ");
//         UUID medicineId = UUID.fromString(scanner.nextLine());
//         System.out.print("Enter requested quantity: ");
//         int requestedQuantity = scanner.nextInt();

//         ReplenishmentRequestDAO newRequest = new ReplenishmentRequestDAO(UUID.randomUUID(), medicineId, requestedQuantity, "Pending");
//         ReplenishmentRequestDAO.addRequest(newRequest);

//         try {
//             ReplenishmentRequestDAO.saveRequestsToCSV();
//             System.out.println("Replenishment request submitted successfully.");
//         } catch (Exception e) {
//             System.out.println("Error saving replenishment request: " + e.getMessage());
//         }
//     }
// }
