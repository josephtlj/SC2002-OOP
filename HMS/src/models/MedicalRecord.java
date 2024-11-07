package HMS.src.models;

import java.util.UUID;
import java.util.Date;

import java.util.regex.Pattern;

import HMS.src.models.DiagnosisTreatmentRecord;

public class MedicalRecord {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum BloodType {
        A_POS("A+"),
        A_NEG("A-"),
        B_POS("B+"),
        B_NEG("B-"),
        AB_POS("AB+"),
        AB_NEG("AB-"),
        O_POS("O+"),
        O_NEG("O-");

        private final String label;

        BloodType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    // Instance Variables
    private UUID medicalRecordId;
    private String name;
    private Date dob;
    private Gender gender;
    private String phoneNumber;
    private String emailAddress;
    private BloodType bloodType;
    private DiagnosisTreatmentRecord[] pastDiagnosisTreatment;

    private Patient patient;

    // Instance Methods
    public MedicalRecord(UUID medicalRecordId, String name, Date dob, Gender gender, String phoneNumber, String emailAddress,
            BloodType bloodType, DiagnosisTreatmentRecord[] pastDiagnosisTreatment, Patient patient) {
        this.medicalRecordId = medicalRecordId!= null ? medicalRecordId : UUID.randomUUID();
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

    public Gender getGender() {
        return this.gender;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public BloodType getBloodType() {
        return this.bloodType;
    }

    public DiagnosisTreatmentRecord[] getPastDiagnosisTreatment() {
        return this.pastDiagnosisTreatment;
    }

    public Patient getPatient() {
        return this.patient;
    }

    // SET Methods
    public void setName(String name){
        this.name = name;
    }

    public void setDob(Date dob){
        this.dob = dob;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

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

    public void setBloodType(BloodType bloodType){
        this.bloodType = bloodType;
    }

    public void setPastDiagnosisTreatment(DiagnosisTreatmentRecord[] diagnosisTreatmentRecord){
        this.pastDiagnosisTreatment = diagnosisTreatmentRecord;
    }

    // Supporting Methods
    public boolean validateEmailAddress(String emailAddress) {
        return Pattern.matches(EMAIL_REGEX, emailAddress);
    }
}
