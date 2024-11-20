package src.models;

import java.util.UUID;
import java.util.Date;

public class MedicalRecord {

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

        // Custom method to get the BloodType by label (e.g., "A+" or "B-")
        public static BloodType fromLabel(String label) {
            for (BloodType type : BloodType.values()) {
                if (type.getLabel().equals(label)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No BloodType found for label " + label);
        }
    }

    // INSTANCE VARIABLES
    private UUID medicalRecordId;
    private String name;
    private Date dob;
    private Gender gender;
    private String phoneNumber;
    private String emailAddress;
    private BloodType bloodType;
    private DiagnosisTreatmentRecord[] pastDiagnosisTreatment;

    private String patient;

    // INSTANCE METHODS
    public MedicalRecord(UUID medicalRecordId, String name, Date dob, Gender gender, String phoneNumber,
            String emailAddress,
            BloodType bloodType, DiagnosisTreatmentRecord[] pastDiagnosisTreatment, String patient) {
        this.medicalRecordId = medicalRecordId != null ? medicalRecordId : UUID.randomUUID();
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
        this.pastDiagnosisTreatment = pastDiagnosisTreatment;
        this.patient = patient;
    }

    
    /** 
     * @return UUID
     */
    public UUID getMedicalRecordId() {
        return this.medicalRecordId;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return this.name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public BloodType getBloodType() {
        return this.bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public DiagnosisTreatmentRecord[] getPastDiagnosisTreatment() {
        return this.pastDiagnosisTreatment;
    }

    public void setPastDiagnosisTreatment(DiagnosisTreatmentRecord[] diagnosisTreatmentRecord) {
        this.pastDiagnosisTreatment = diagnosisTreatmentRecord;
    }

    public String getPatient() {
        return this.patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
}