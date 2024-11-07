package HMS.src.controllers;

import java.util.InputMismatchException;
import java.util.Scanner;

import HMS.src.models.MedicalRecord;
import HMS.src.models.Patient;

public class PatientActions {

    public void showPatientMenu(Patient currentPatient) {

        // INITIALISE USER SCANNER FOR PATIENT INPUT
        Scanner patientScanner = new Scanner(System.in);

        // ITERATE TILL SYSTEM RECEIVES VALID INPUT
        int userChoice = 0;

        do {
            try {
                System.out.println("""
                        ==============================================
                        |                Patient Menu                |
                        ==============================================
                        Please select an option:
                        (1) View Medical Record
                        (2) Update Personal information
                        (3) View Available AppointmentSlots
                        (4) Schedule an Appointment
                        (5) Reschedule an Appointment
                        (6) Cancel an Appointment
                        (7) View Scheduled Appointment(s)
                        (8) View Past Appointment Outcome Record(s)
                        (9) Logout
                        """);
                System.out.print("Your Choice: ");
                userChoice = patientScanner.nextInt();

                switch (userChoice) {
                    case 1:
                        // VIEW MEDICAL RECORD
                        viewMedicalRecord(currentPatient);
                        break;

                    case 2:
                        // UPDATE PERSONAL INFORMATION
                        updatePersonalInformation(currentPatient);
                        break;

                    case 3:

                        break;

                    case 4:

                        break;

                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:

                        break;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please choose a valid option!\n");
                patientScanner.nextLine();
            }

        } while (userChoice != 9);

        // TERMINATE USER SCANNER WHEN PROGRAM
        patientScanner.close();
    }

    private void viewMedicalRecord(Patient patient) {
        MedicalRecord medicalRecord = patient.viewMedicalRecord();
        if (medicalRecord != null) {
            System.out.println(String.format("""
                    ==============================================
                    |               Medical Record               |
                    ==============================================
                    Name: %s
                    Date Of Birth: %s
                    Gender: %s,
                    Phone Number: %s
                    Email Address: %s
                    Blood Type: %s
                    Past Diagnosis & Treatment: %s
                    """, medicalRecord.getName(), medicalRecord.getDob(), medicalRecord.getGender(),
                    medicalRecord.getPhoneNumber(), medicalRecord.getEmailAddress(), medicalRecord.getBloodType(),
                    medicalRecord.getPastDiagnosisTreatment()));
        } else {
            System.out.println("No medical record found.");
        }
    }

    private void updatePersonalInformation(Patient patient) {
        MedicalRecord medicalRecord = patient.viewMedicalRecord();
        if (medicalRecord != null) {
            System.out.println("""
                    ==============================================
                    |        Update Personal Information         |
                    ==============================================
                    Name: %s
                    Date Of Birth: %s
                    Gender: %s,
                    Phone Number: %s
                    Email Address: %s
                    Blood Type: %s
                    Past Diagnosis & Treatment: %s
                    """);
        } else {
            System.out.println("No medical record found.");
        }
    }
}