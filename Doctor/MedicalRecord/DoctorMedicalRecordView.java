package Doctor.MedicalRecord;

import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.MedicalRecord;

public class DoctorMedicalRecordView 
{
    //ATTRIBUTES
    Scanner scanner= new Scanner(System.in);
    DoctorMedicalRecordManager manager;

    //CONSTRUCTOR
    public DoctorMedicalRecordView(DoctorMedicalRecordManager manager)
    {
        printViewFindPatient();
        this.manager= manager;
    }

    //METHODS
    public void printViewFindPatient()
    {
        String patientID=null;
        boolean validInput=false;
        MedicalRecord medicalRecord;
        do
        {
            try
            {
                System.out.println("Enter patient ID:");
                patientID= scanner.nextLine().trim(); //remove any whitespaces

                //CHECK IF PATIENTID IS IN DATABASE
                medicalRecord=manager.getMedicalRecord(patientID);
                if(medicalRecord==null)
                {
                    System.out.println("Invalid patient ID.Please try again.");
                    validInput=false;
                }
                else
                {
                    validInput=true;
                    printMedicalRecord(medicalRecord);
                }

            }
            catch(InputMismatchException inputMismatchException)
            {
                System.out.println("Invalid format for patientID");
                validInput=false;
            }
        }while(!validInput);
    }

    public void printMedicalRecord(MedicalRecord medicalRecord)
    {
        System.out.println("""
                    =============================================================
                    |             Hospital Management System (HMS)!             |
                    |                   View Medical Record                     |
                    =============================================================
                    """);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String formattedDob = sdf.format(medicalRecord.getDob());

            System.out.printf("%-30s %-30s\n", "Name:", medicalRecord.getName());
            System.out.printf("%-30s %-30s\n", "Date of Birth:", formattedDob);
            System.out.printf("%-30s %-30s\n", "Gender:", medicalRecord.getGender());
            System.out.printf("%-30s %-30s\n", "Phone Number:", medicalRecord.getPhoneNumber());
            System.out.printf("%-30s %-30s\n", "Email Address:", medicalRecord.getEmailAddress());
            System.out.printf("%-30s %-30s\n", "Blood Type:", medicalRecord.getBloodType());
            System.out.printf("%-30s %-30s\n", "Past Diagnosis Treatments:", medicalRecord.getPastDiagnosisTreatment());
    }
}
