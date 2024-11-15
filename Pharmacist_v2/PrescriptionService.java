import java.util.Scanner;
import java.util.UUID;

public class PrescriptionService {
    public void updatePrescriptionStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Prescription ID to update: ");
        UUID prescriptionId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter new status: ");
        String newStatus = scanner.nextLine();

        if (PrescriptionDAO.updatePrescriptionStatus(prescriptionId, newStatus)) {
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("Prescription not found.");
        }
    }
}
