package views;

import java.util.InputMismatchException;
import java.util.Scanner;

import models.Session;
import models.User;
import interfaces.LoginInteraction;
import controllers.LoginController;

public class LoginView implements LoginInteraction {

    private Scanner loginScanner = new Scanner(System.in);

    @Override
    public void displayLoginView() {
        boolean loginSuccessful = false;
        String hospitalId = null;
        String password = null;

        // FUNCTION EXECUTES TILL USER WANTS TO GO BACK TO HOMEPAGE
        int userChoice = 0;

        do {
            try {
                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                        Login Page                         |
                        =============================================================
                        Please select an option:
                        (1) Patient
                        (2) Staff
                        (3) Go back to previous page
                        """);

                // RETRIEVE USER'S CHOICE
                System.out.print("Your Choice: ");
                userChoice = loginScanner.nextInt();
                loginScanner.nextLine();

                // IF USER'S CHOICE IS 3, RETURN TO HOMEPAGE
                if (userChoice == 3) {
                    return;
                } else {
                    while (loginSuccessful == false) {
                        // PROMPT USER TO ENTER LOGIN CREDENTIALS
                        System.out.println("Please enter your Hospital ID:");
                        hospitalId = loginScanner.nextLine();

                        System.out.println("Please enter your Password:");
                        password = loginScanner.nextLine();

                        // INITIALISE LOGINCONTROLLER
                        LoginController loginController = new LoginController();

                        // VALIDATE LOGIN CREDENTIALS
                        switch (userChoice) {
                            case 1:
                                loginSuccessful = loginController.userLogin(hospitalId, password, "PATIENT");
                                break;
                            case 2:
                                loginSuccessful = loginController.userLogin(hospitalId, password, "STAFF");
                                break;

                            default:
                                System.out.println("Please enter a valid choice!\n");
                                break;
                        }

                        if (loginSuccessful == false) {
                            System.out.println("Invalid Login Credentials.");
                        } else {
                            System.out.println("Login Successful.");
                            User currentUser = null;
                            switch (userChoice) {
                                case 1:
                                    currentUser = loginController.getPatientByHospitalId(hospitalId);
                                    break;
                                // case 2:
                                // currentUser = loginController.getStaffByHospitalId(hospitalId);
                                // break;

                                default:
                                    break;
                            }
                            Session.setCurrentUser(currentUser);
                            return;
                        }
                    }
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please enter a valid integer only!\n");
                loginScanner.nextLine();
            }

        } while (userChoice != 3);
    }
}