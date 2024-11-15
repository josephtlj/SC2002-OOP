package DoctorView;

import Doctor.*;
import models.Session;
import java.util.Scanner;

public class DoctorChangePasswordView 
{
    //ATTRIBUTES
    private Scanner doctorScanner = new Scanner(System.in);

    //CONSTRUCTOR
    public DoctorChangePasswordView(DoctorPasswordManager passwordManager)
    {
        ChangePasswordView(passwordManager);
    }

    //METHODS
    public void ChangePasswordView(DoctorPasswordManager passwordManager)
    {
        System.out.println("""
                =====================================================================================
                |                         Hospital Management System (HMS)!                         |
                |                                  Welcome Doctor                                   |
                =====================================================================================
                As this is your first time logging into your account, kindly reset your password.
                """);

        while(true)
        {
        ChangePasswordOutcome outcome;
        String newPassword = null;
        String confirmPassword = null;

        System.out.print("Enter your new password: ");
        newPassword = doctorScanner.nextLine();
        System.out.print("Re-enter your new password: ");
        confirmPassword = doctorScanner.nextLine();
    
        outcome= passwordManager.updateDoctorPassword(newPassword, confirmPassword, Session.getCurrentUser().getHospitalId()); 

        if(outcome.getOutcome())
        {
            System.out.println("""
                    =====================================================================================
                    |                         Hospital Management System (HMS)!                         |
                    |              Password Successfully Updated! Kindly login once again.              |
                    =====================================================================================
                    """);
            Session.logout();
            return;
        }
        else
        {
            if(outcome.getErrorType()== PasswordErrorType.NO_MATCH)
            {
                PasswordErrorNoMatchView(passwordManager);
            }
            else
            {
                PasswordErrorSameAsOldView(passwordManager);
            }
        }
        }
        
    }

    public void PasswordErrorNoMatchView(DoctorPasswordManager passwordManager)
    {
        System.out.println("Passwords do not match. Kindly try again.");
    }

    public void PasswordErrorSameAsOldView(DoctorPasswordManager passwordManager)
    {
        System.out.println("New password must not be the same as old password. Kindly try again.");
    }
    
}
