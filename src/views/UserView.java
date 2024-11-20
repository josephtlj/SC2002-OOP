package src.views;

import java.util.Scanner;

import src.controllers.UserController;

public class UserView {
    private final UserController userController;
    private final Scanner scanner = new Scanner(System.in);

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void showWelcomeMessage() {
        System.out.println("""
                =============================================================
                |                      Welcome To The                       |
                |             Hospital Management System (HMS)!             |
                =============================================================
                """);
    }

    
    /** 
     * @return int
     */
    public int showInitialMenu() {
        while (true) {
            try {
                System.out.println("""
                        (1) Login
                        (2) Exit Program
                        """);
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1 || choice == 2) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a choice between 1 and 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public void showFarewellMessage() {
        System.out.println("""
                =====================================================================
                |     Thank you for using the Hospital Management System (HMS)!     |
                =====================================================================
                    """);
    }

    public void showUserLogin() {
        boolean loginStatus = false;
        String hospitalId;
        String password;

        while (!loginStatus) {
            try {
                System.out.print("Enter hospital ID: ");
                hospitalId = scanner.nextLine();
                System.out.print("Enter password: ");
                password = scanner.nextLine();
                loginStatus = userController.handleLogin(hospitalId, password);
                if (!loginStatus) {
                    System.out.println("Login failed. Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("Invalid Login Credentials.");
            }
        }

        System.out.println("Login successful!");

    }
}
