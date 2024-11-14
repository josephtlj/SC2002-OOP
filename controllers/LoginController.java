package controllers;

// import java.util.List;
// import java.util.ArrayList;
// import java.util.Scanner;  // Optional for reading input from user (if needed)

import models.User;
import models.Patient;
// import HMS.src.models.Staff;
import daos.LoginDao;

public class LoginController {
    private LoginDao loginDao;

    public LoginController() {
        this.loginDao = new LoginDao();
    }

    // RETRIEVE SPECIFIC PATIENT FROM DATABASE USING HOSPITALID
    public Patient getPatientByHospitalId(String userHospitalId) {
        return loginDao.getPatientByHospitalId(userHospitalId);
    }

    // // RETRIEVE SPECIFIC STAFF FROM DATABASE USING HOSPITALID
    // public Staff getStaffByHospitalId(String userHospitalId) {
    // return loginDao.getStaffByHospitalId(userHospitalId);
    // }



    
    // LOGIN
    public boolean userLogin(String userHospitalId, String userPassword, String userRole) {
        // RETRIEVE CORRESPONDING USER FROM DATABASE WITH SAME HOSPITALID
        User user = null;

        switch (userRole) {
            case "PATIENT":
                user = getPatientByHospitalId(userHospitalId);
                break;

            // case "DOCTOR":
            // user = getStaffByHospitalId(userHospitalId);
            // break;
            default:
                System.out.println("Invalid User Role.");
                break;
        }

        if (user == null) {
            System.out.println("Hospital ID cannot be empty.");
            return false;
        } else {
            if (user.hashPassword(userPassword, user.getSalt()).equals(user.getPassword())) {
                return true;
            } else {
                System.out.println("Login Failed.");
                return false;
            }
        }
    }

}