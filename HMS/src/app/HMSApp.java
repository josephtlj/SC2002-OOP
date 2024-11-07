package HMS.src.app;

import java.util.Scanner;

import HMS.src.controllers.PatientActions;
import HMS.src.csv.AssignInitialData;
import HMS.src.models.Patient;
import HMS.src.models.User;

import java.util.InputMismatchException;

public class HMSApp {

    public static void main(String[] args) {

        // INITIALISING DATA
        String dataPath = "HMS/data";

        // PATIENT
        String patientFile = "patientData.csv";
        Patient[] patientList = new Patient[10];
        AssignInitialData patientAssigner = new AssignInitialData();
        patientAssigner.assignPatientData(patientList, dataPath, patientFile);

        // INITIALISING GLOBAL VARIABLES
        Scanner userScanner = new Scanner(System.in);
        boolean loginSuccessful = false;
        String hospitalIdCredential = null;
        String passwordCredential = null;

        PatientActions patientActions = new PatientActions();

        // MAIN PROGRAM

        // SYSTEM EXECUTES TILL USER WANTS TO EXIT SYSTEM
        int userChoice = 0;

        do {
            try {
                System.out.println("""
                        ===========================================================
                        |            Hospital Management System (HMS)!            |
                        |                        LOGIN PAGE                       |
                        ===========================================================
                        Please select an option:
                        (1) Patient
                        (2) Staff
                        (3) Exit System
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.print("Your Choice: ");
                userChoice = userScanner.nextInt();

                // ASSIGNING RESPECTIVE INTERFACE TO USER BASED ON USER ROLE
                switch (userChoice) {
                    case 1:
                        userScanner.nextLine();
                        // PATIENT LOGIN INTERFACE
                        Patient currentPatient = null;

                        while (!loginSuccessful) {
                            System.out.println("\nEnter your HospitalID: ");
                            hospitalIdCredential = userScanner.nextLine();
                            System.out.println("\nEnter your Password: ");
                            passwordCredential = userScanner.nextLine();

                            // SEARCHES DATABASE FOR CORRESPONDING HOSPITALID
                            for (int patientIndex = 0; patientIndex < patientList.length - 1; patientIndex++) {
                                if (patientList[patientIndex].getHospitalId().equals(hospitalIdCredential)) {
                                    Patient patient = patientList[patientIndex];
                                    if (patient.verifyPassword(passwordCredential)) {
                                        loginSuccessful = true;
                                        currentPatient = patient;
                                        break;
                                    }
                                    ;
                                }
                            }

                            if (currentPatient == null) {
                                System.out.println("UserId does not exist!");
                            }
                        }

                        patientActions.showPatientMenu(currentPatient);

                        break;
                    case 2:
                        userScanner.nextLine();
                        // STAFF LOGIN INTERFACE
                        break;
                    case 3:
                        // EXIT PROGRAM
                        break;

                    default:
                        System.out.println("Please enter a valid choice!\n");
                        break;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please enter a valid integer only!\n");
                userScanner.nextLine();
            }
        } while (userChoice != 3);

        // EXITING SYSTEM
        System.out.println("""
                =====================================================================
                |     Thank you for using the Hospital Management System (HMS)!     |
                =====================================================================
                    """);

        // TERMINATE USER SCANNER WHEN PROGRAM TERMINATES
        userScanner.close();
    }
}
