package src.controllers;

import java.time.LocalDate;
import java.util.List;

import src.models.AppointmentOutcomeRecord;
import src.models.Medicine;
import src.models.Prescription;
import src.models.Session;
import src.interfaces.PharmacistServiceInterface;

public class PharmacistController {
    private final PharmacistServiceInterface pharmacistService;
    private final MedicineController medicineController;
    private final ReplenishmentRequestController replenishmentRequestController;
    private final AppointmentOutcomeRecordController appointmentOutcomeRecordController;

    public PharmacistController(PharmacistServiceInterface pharmacistService,
            MedicineController medicineController, ReplenishmentRequestController replenishmentRequestController) {
        this.pharmacistService = pharmacistService;
        this.medicineController = medicineController;
        this.replenishmentRequestController = replenishmentRequestController;
        this.appointmentOutcomeRecordController = new AppointmentOutcomeRecordController(0);
    }

    
    /** 
     * @param hospitalId
     * @param newPassword
     * @param confirmPassword
     * @return boolean
     */
    public boolean handleUpdatePassword(String hospitalId, String newPassword, String confirmPassword) {
        try {
            pharmacistService.updatePassword(hospitalId, newPassword, confirmPassword);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Medicine> handleViewMedicationInventory() {
        try {
            return medicineController.handleViewMedicationInventory();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // public List<AppointmentOutcomeRecord> handleViewAppointmentOutcomeRecordsByDay(LocalDate date) {
    //     try {
    //         return appointmentOutcomeRecordController.handleViewAppointmentOutcomeRecordsByDay(date);
    //     } catch (IllegalArgumentException e) {
    //         System.out.println(e.getMessage());
    //         return null;
    //     }
    // }

    public boolean handleSubmitReplenishmentRequest(String medicineName, int replenishmentQuantity) {
        try {
            return replenishmentRequestController.handleSubmitReplenishmentRequest(medicineName, replenishmentQuantity);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
