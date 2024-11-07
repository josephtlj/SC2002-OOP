package HMS.src;
import java.util.UUID;
import java.util.Date;

import java.util.regex.Pattern;

import HMS.src.management.Patient;

public class MedicalRecord {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    // Instance Variables
    private UUID medicalRecordId;
    private String name;
    private Date dob;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String bloodType;
    private DiagnosisTreatmentRecord[] pastDiagnosisTreatment;

    private Patient patient;

    // Instance Methods
    public MedicalRecord() {
        this.medicalRecordId = UUID.randomUUID();
        this.name = null;
        this.dob = null;
        this.gender = null;
        this.phoneNumber = null;
        this.emailAddress = null;
        this.bloodType = null;
        this.pastDiagnosisTreatment = null;
        this.patient = null;
    }

    public MedicalRecord(String name, Date dob, String gender, String phoneNumber, String emailAddress,
            String bloodType, DiagnosisTreatmentRecord[] pastDiagnosisTreatment, Patient patient) {
        this.medicalRecordId = UUID.randomUUID();
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
        this.pastDiagnosisTreatment = pastDiagnosisTreatment;
        this.patient = patient;
    }

    // Get Methods
    public UUID getMedicalRecordId() {
        return this.medicalRecordId;
    }

    public String getName() {
        return this.name;
    }

    public Date getDob() {
        return this.dob;
    }

    public String getGender() {
        return this.gender;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public DiagnosisTreatmentRecord[] getPastDiagnosisTreatment() {
        return this.pastDiagnosisTreatment;
    }

    public Patient getPatient() {
        return this.patient;
    }

    // SET Methods
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        if (validateEmailAddress(emailAddress)) {
            this.emailAddress = emailAddress;
        } else {
            System.out.println("Invalid format for Email Address!");
        }
    }

    // Supporting Methods
    public boolean validateEmailAddress(String emailAddress) {
        return Pattern.matches(EMAIL_REGEX, emailAddress);
    }
}
