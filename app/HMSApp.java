package app;

import models.Session;
import models.User;
import views.LoginView;
import views.PatientView;

import java.util.Scanner;
import java.util.InputMismatchException;

public class HMSApp {

    public static void main(String[] args) {

        // INSTANTIATING VIEWS
        LoginView loginView = new LoginView();
        PatientView patientView = new PatientView();

        // INITIALISING GLOBAL VARIABLES
        Scanner userScanner = new Scanner(System.in);

        // MAIN PROGRAM
        // SYSTEM EXECUTES TILL USER WANTS TO EXIT SYSTEM
        int userChoice = 0;

        do {
            try {
                if (Session.isLoggedIn()) {
                    User currentUser = Session.getCurrentUser();
                    switch (currentUser.getRole()) {
                        case PATIENT:
                            patientView.displayPatientView();
                            break;

                        case DOCTOR:

                            break;

                        case PHARMACIST:

                            break;
                        case ADMINISTRATOR:

                            break;

                        default:
                            break;
                    }
                } else {
                    System.out.println("""
                            =============================================================
                            |                      Welcome To The                       |
                            |             Hospital Management System (HMS)!             |
                            =============================================================
                            Please select an option:
                            (1) Login
                            (2) Exit System
                            """);

                    // RETRIEVE USER'S CHOICE
                    System.out.print("Your Choice: ");
                    userChoice = userScanner.nextInt();

                    // ASSIGNING RESPECTIVE INTERFACE TO USER BASED ON USER ROLE
                    switch (userChoice) {
                        case 1:
                            // LOGIN
                            loginView.displayLoginView();

                            break;
                        case 2:
                            // EXIT PROGRAM
                            break;

                        default:
                            System.out.println("Please enter a valid choice!\n");
                            break;
                    }
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please enter a valid integer only!\n");
                userScanner.nextLine();
            }
        } while (userChoice != 2);

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