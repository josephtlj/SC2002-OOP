package Doctor.DiagnosisTreatmentRecord;

import java.util.Scanner;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DiagnosisTreatmentRecordView 
{
    //ATTRIBUTES
    private Scanner scanner = new Scanner(System.in);
    DiagnosisTreatmentRecordManager treatmentRecordManager;

    //CONSTRUCTOR
    public DiagnosisTreatmentRecordView(DiagnosisTreatmentRecordManager treatmentRecordManager)
    {
        this.treatmentRecordManager= treatmentRecordManager;
        printView();
    }

    //METHODS
    public void printView()
    {
        //DATE VALIDATION NOT INCLUDED YET
        
        System.out.print("Enter the date of the record you want to update (dd/MM/yyyy): ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date;
        try 
        {
            date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

        // Menu to choose which field to update
        System.out.println("What would you like to update?");
        System.out.println("1. Diagnosis");
        System.out.println("2. Prescription");
        System.out.println("3. Treatment Plan");
        System.out.print("Enter your choice (1-3): ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                printUpdateDiagnosis(date);
                break;
            case "2":
                printUpdatePrescription(date);
                break;
            case "3":
                printUpdateTreatmentPlan(date);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        
    }

    public void printUpdateDiagnosis(LocalDate date)
    {
        System.out.print("Enter the new diagnosis: ");
        String diagnosis = scanner.nextLine().trim(); // Trim whitespace
        treatmentRecordManager.setDiagnosis(diagnosis, date); 
        System.out.println("Diagnosis has been successfully updated.");
    }

    public void printUpdatePrescription(LocalDate date)
    {
        System.out.print("Enter the new prescription: ");
        String prescription = scanner.nextLine().trim(); // Trim whitespace
        treatmentRecordManager.setPrescription(prescription, date); 
        System.out.println("Prescription has been successfully updated.");
    }

    public void printUpdateTreatmentPlan(LocalDate date)
    {
        System.out.print("Enter the new treatment plan: ");
        String treatmentPlan = scanner.nextLine().trim(); // Trim whitespace
        treatmentRecordManager.setTreatmentPlan(treatmentPlan, date); 
        System.out.println("Treatment plan has been successfully updated.");
    }
}
