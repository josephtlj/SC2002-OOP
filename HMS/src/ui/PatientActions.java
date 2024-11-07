package HMS.src.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PatientActions {

    public static void showOptions() {

        // INITIALISE USER SCANNER FOR PATIENT INPUT
        Scanner userScanner = new Scanner(System.in);

        // ITERATE TILL SYSTEM RECEIVES VALID INPUT
        int userChoice = 0;

        do {
            try {
                System.out.println("""
                        ============================================
                        |                Home Page                 |
                        |             Welcome Patient!             |
                        ============================================
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
                userChoice = userScanner.nextInt();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Please choose a valid option!\n");
                userScanner.nextLine();
            }

        } while (userChoice != 2);

        // TERMINATE USER SCANNER WHEN PROGRAM
        userScanner.close();
    }
}
