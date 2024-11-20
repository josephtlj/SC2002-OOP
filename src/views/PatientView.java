package src.views;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import src.models.MedicalRecord;
import src.models.Session;
import src.controllers.PatientController;
import src.daos.AppointmentOutcomeRecordDao;
import src.daos.DoctorAppointmentDao;
import src.models.Appointment;
import src.models.AppointmentOutcomeRecord;
import src.models.AppointmentTimeSlot;

import src.daos.DoctorAppointmentDao;

public class PatientView {
    private final PatientController patientController;
    private final Scanner scanner = new Scanner(System.in);

    public PatientView(PatientController patientController) {
        this.patientController = patientController;
    }

    public void showMainMenu() {
        int patientChoice = 99999;
        while (patientChoice != 9) {
            try {

                if(Session.getCurrentSession().getCurrentUser().getIsFirstLogin()){
                    System.out.println("As this is your first login, kindly reset your password.");
                    showUpdatePassword();
                }

                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                      Welcome Patient                      |
                        =============================================================
                        Please select an option:
                        (1) View Medical Record
                        (2) Update Personal Information
                        (3) View Appointment Slots
                        (4) Schedule Appointment
                        (5) Reschedule Appointment
                        (6) Cancel Appointment
                        (7) View Scheduled Appointments
                        (8) View Past Appointment Outcome Records
                        (9) Logout
                        """);

                System.out.print("Enter your choice: ");
                patientChoice = Integer.parseInt(scanner.nextLine());

                if (patientChoice >= 1 && patientChoice <= 9) {
                    switch (patientChoice) {
                        case 1:
                            showMedicalRecord();
                            break;
                        case 2:
                            showUpdatePersonalInformation();
                            break;
                        case 3:
                            showViewAppointmentSlots();

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
                            showViewPastAppointmentOutcomeRecords();
                            break;
                        case 9:
                            Session.getCurrentSession().logout();
                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a choice between 1 and 9.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

    }

    private void showMedicalRecord() {
        try {
            String patientHospitalId = Session.getCurrentSession().getCurrentUser().getHospitalId();

            System.out.println("""
                    =============================================================
                    |             Hospital Management System (HMS)!             |
                    |                   View Medical Record                     |
                    =============================================================
                    """);
            MedicalRecord medicalRecord = patientController.handleViewMedicalRecord(patientHospitalId);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String formattedDob = sdf.format(medicalRecord.getDob());

            System.out.printf("%-30s %-30s\n", "Name:", medicalRecord.getName());
            System.out.printf("%-30s %-30s\n", "Date of Birth:", formattedDob);
            System.out.printf("%-30s %-30s\n", "Gender:", medicalRecord.getGender());
            System.out.printf("%-30s %-30s\n", "Phone Number:", medicalRecord.getPhoneNumber());
            System.out.printf("%-30s %-30s\n", "Email Address:", medicalRecord.getEmailAddress());
            System.out.printf("%-30s %-30s\n", "Blood Type:", medicalRecord.getBloodType());
            System.out.printf("%-30s %-30s\n", "Past Diagnosis Treatments:", medicalRecord.getPastDiagnosisTreatment());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }

    }

    private void showUpdatePersonalInformation() {
        try {
            String patientHospitalId = Session.getCurrentSession().getCurrentUser().getHospitalId();

            int patientChoice = 99999;
            while (patientChoice != 4) {

                showMedicalRecord();

                System.out.println("""
                        =============================================================
                        |             Hospital Management System (HMS)!             |
                        |                Update Personal Information                |
                        =============================================================
                        Please select an option:
                        (1) Update Password
                        (2) Update Email Address
                        (3) Update Phone Number
                        (4) Back to previous page
                        """);

                System.out.print("Enter your choice: ");
                patientChoice = Integer.parseInt(scanner.nextLine());

                if (patientChoice >= 1 && patientChoice <= 4) {
                    switch (patientChoice) {
                        case 1:
                            showUpdatePassword();
                            break;
                        case 2:
                            boolean updateEmailAddress = false;
                            while (!updateEmailAddress) {
                                System.out.println("""
                                        =============================================================
                                        |             Hospital Management System (HMS)!             |
                                        |                   Update Email Address                    |
                                        =============================================================
                                        """);
                                System.out.println("Enter your new email address:");
                                String newEmailAddress = scanner.nextLine();

                                updateEmailAddress = patientController.handleUpdateEmailAddress(patientHospitalId, newEmailAddress);
                                
                            }

                            System.out.println("""
                                    =============================================================
                                    |             Hospital Management System (HMS)!             |
                                    |            Successfully updated email address!            |
                                    =============================================================
                                    """);
                            break;
                        case 3:
                            boolean updatePhoneNumber = false;
                            while (!updatePhoneNumber) {
                                System.out.println("""
                                        =============================================================
                                        |             Hospital Management System (HMS)!             |
                                        |                       Update Phone Number                        |
                                        =============================================================
                                        """);
                                System.out.println("Enter your new phone number:");
                                String newPhoneNumber = scanner.nextLine();

                                updatePhoneNumber = patientController.handleUpdatePhoneNumber(patientHospitalId, newPhoneNumber);
                            }

                            System.out.println("""
                                    =============================================================
                                    |             Hospital Management System (HMS)!             |
                                    |            Successfully updated phone number!             |
                                    =============================================================
                                    """);
                            break;
                        case 4:

                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a choice between 1 and 4.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void showViewAppointmentSlots() {
        try {
            String patientHospitalId = Session.getCurrentSession().getCurrentUser().getHospitalId();

            System.out.println("""
                    =============================================================
                    |             Hospital Management System (HMS)!             |
                    |           View Available Appointment Time Slots            |
                    =============================================================
                    """);

            DoctorAppointmentDao appointSlotDao = new DoctorAppointmentDao(patientHospitalId);
            List<Appointment> availableTimeSlots = appointSlotDao.getAvailableAppointmentSlots();

            for (Appointment appointment : availableTimeSlots) {
                System.out.println("-".repeat(60));
                System.out.printf("%-25s %-30s\n", "Date:", appointment.getAppointmentDate());
                System.out.printf("%-25s %-30s\n", "Appointment Time Slot:", appointment.getAppointmentTimeSlot());
                System.out.printf("%-25s %-30s\n", "Doctor ID:", appointment.getDoctorID());
                System.out.println("-".repeat(60));
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void showViewPastAppointmentOutcomeRecords() {
        try {
            String patientHospitalId = Session.getCurrentSession().getCurrentUser().getHospitalId();

            System.out.println("""
                    =============================================================
                    |             Hospital Management System (HMS)!             |
                    |          View Past Appointment Outcome Records            |
                    =============================================================
                    """);
            AppointmentOutcomeRecordDao apptOutRecDao = new AppointmentOutcomeRecordDao(patientHospitalId);
            List<AppointmentOutcomeRecord> apptOutRecList = apptOutRecDao
                    .getAllAppointmentOutcomeRecords(patientHospitalId);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            for (AppointmentOutcomeRecord apptOutRec : apptOutRecList) {
                System.out.println("-".repeat(60));
                System.out.printf("%-25s %-30s\n", "Appointment Record Id:", apptOutRec.getAppointmentRecordId());
                System.out.printf("%-25s %-30s\n", "Date:", apptOutRec.getDate());
                System.out.printf("%-25s %-30s\n", "Services Provided:", apptOutRec.getServiceType());
                System.out.printf("%-25s %-30s\n", "Consultation Notes:", apptOutRec.getConsultationNotes());
                System.out.println("-".repeat(60));
            }
            return;

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void showUpdatePassword() {
        boolean updatePassword = false;
        String patientHospitalId = Session.getCurrentSession().getCurrentUser().getHospitalId();

        while (!updatePassword) {
            System.out.println("""
                    =============================================================
                    |             Hospital Management System (HMS)!             |
                    |                      Update Password                      |
                    =============================================================
                    """);

            System.out.println("Enter your new password:");
            String newPassword = scanner.nextLine();
            System.out.println("Confirm your new password:");
            String confirmPassword = scanner.nextLine();

            updatePassword = patientController.handleUpdatePassword(patientHospitalId, newPassword,
                    confirmPassword);
        }

        System.out.println("""
                =============================================================
                |             Hospital Management System (HMS)!             |
                |              Successfully updated password!               |
                =============================================================
                """);
    }
}
