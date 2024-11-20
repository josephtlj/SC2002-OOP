package src.views;

import java.util.InputMismatchException;
import java.util.Scanner;

import src.controllers.DiagnosisTreatmentRecordController;
import src.controllers.MedicalRecordController;
import src.interfaces.DiagnosisTreatmentRecordViewInterface;
import src.models.MedicalRecord;

import java.time.*;

public class DiagnosisTreatmentRecordView implements DiagnosisTreatmentRecordViewInterface {
    // ATTRIBUTES
    private Scanner scanner = new Scanner(System.in);
    DiagnosisTreatmentRecordController treatmentRecordManager;
    InputForDateMonthTimeSlotView inputForDateMonthTimeSlotView = new InputForDateMonthTimeSlotView();
    private final MedicalRecordController medicalRecordController;

    // CONSTRUCTOR
    public DiagnosisTreatmentRecordView(MedicalRecordController medicalRecordController) {
        this.medicalRecordController = medicalRecordController;
        printGetPatientID();
    }

    // METHODS
    public void printUpdateDiagnosisTreatmentRecordView() {

        LocalDate date = inputForDateMonthTimeSlotView.viewWhichDate();

        int choice = -1;

        do {
            try {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |               Update Patient Medical Record               |
                        =============================================================
                        Please select an option to update:
                        (1) Diagnosis
                        (2) Prescription
                        (3) Treatment Plan
                        (4) Go back to previous page
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.println("Your Choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        printUpdateDiagnosis(date);
                        break;
                    case 2:
                        printUpdatePrescription(date);
                        break;
                    case 3:
                        printUpdateTreatmentPlan(date);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please enter a valid integer only!");
            }

        } while (choice != 9);

    }

    
    /** 
     * @param date
     */
    public void printUpdateDiagnosis(LocalDate date) {
        System.out.print("Enter the new diagnosis: ");
        String diagnosis = scanner.nextLine().trim(); // Trim whitespace
        treatmentRecordManager.setDiagnosis(diagnosis, date);
        System.out.println("Diagnosis has been successfully updated.");
    }

    
    /** 
     * @param date
     */
    public void printUpdatePrescription(LocalDate date) {
        System.out.print("Enter the new prescription: ");
        String prescription = scanner.nextLine().trim(); // Trim whitespace
        treatmentRecordManager.setPrescription(prescription, date);
        System.out.println("Prescription has been successfully updated.");
    }

    
    /** 
     * @param date
     */
    public void printUpdateTreatmentPlan(LocalDate date) {
        System.out.print("Enter the new treatment plan: ");
        String treatmentPlan = scanner.nextLine().trim(); // Trim whitespace
        treatmentRecordManager.setTreatmentPlan(treatmentPlan, date);
        System.out.println("Treatment plan has been successfully updated.");
    }

    public void printGetPatientID() {
        String patientID = null;
        boolean validInput = false;
        MedicalRecord medicalRecord;
        do {
            try {
                System.out.println("Enter patient ID:");
                patientID = scanner.nextLine().trim(); // remove any whitespaces

                // CHECK IF PATIENTID IS IN DATABASE
                medicalRecord = medicalRecordController.handleViewMedicalRecord(patientID);
                if (medicalRecord == null) {
                    System.out.println("Invalid patient ID.Please try again.");
                    validInput = false;
                } else {
                    validInput = true;
                    this.treatmentRecordManager = new DiagnosisTreatmentRecordController(patientID);
                    printUpdateDiagnosisTreatmentRecordView();
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Invalid format for patientID");
                validInput = false;
            }
        } while (!validInput);
    }
}