package Models;

import java.util.*;

package Models;

import java.util.*;

public class Administrator extends User {
    private UUID adminId;
    private List<ReplenishmentRequest> replenishmentRequests;
    private List<AdministratorMedicineLog> medicineLogs;
    private Map<String, Staff> staffList;
    private Map<UUID, Medicine> medicineInventory;
    private Map<String, MedicalRecord> medicalRecords;
    private List<Appointment> appointments;

    public Administrator(UUID adminId, String staffID, String name, String password) {
        super(staffID, name, password);
        this.adminId = adminId;
        this.replenishmentRequests = new ArrayList<>();
        this.medicineLogs = new ArrayList<>();
        this.staffList = new HashMap<>();
        this.medicineInventory = new HashMap<>();
        this.medicalRecords = new HashMap<>();
        this.appointments = new ArrayList<>();
        populateSampleData();
    }

    private void populateSampleData() {
        // Medical Records
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("8c49ce65-e409-467c-a21d-ca847c3c9ae4", "James Hudson", "1985-09-02", "MALE", "692-806-4334", "james.hudson@gmail.com", "A+", "", "PA6fe25d"));
        medicalRecords.add(new MedicalRecord("6c9f0436-251a-4a03-97ad-dad69884a5ab", "Emma Johnson", "2003-11-27", "FEMALE", "488-869-1039", "emma.johnson@gmail.com", "A-", "", "PA703270"));
        medicalRecords.add(new MedicalRecord("02c51449-bd1d-4a57-ab04-7c2c13fc088a", "Liam Smith", "1972-10-16", "MALE", "614-236-2429", "liam.smith@gmail.com", "B+", "", "PA4d3e2b"));
        // Add these records to the map
        for (MedicalRecord record : medicalRecords) {
            this.medicalRecords.put(record.getId(), record);
        }

        // Medicines
        this.medicineInventory.put(UUID.randomUUID(), new Medicine("Paracetamol", 100, 20));
        this.medicineInventory.put(UUID.randomUUID(), new Medicine("Ibuprofen", 50, 10));
        this.medicineInventory.put(UUID.randomUUID(), new Medicine("Amoxicillin", 75, 15));
    }

    // Method to display all medical records
    public void displayAllMedicalRecords() {
        medicalRecords.forEach((id, record) -> {
            System.out.println("ID: " + id + ", Name: " + record.getName() + ", Blood Type: " + record.getBloodType());
        });
    }

    // Method to display all medicines
    public void displayAllMedicines() {
        medicineInventory.forEach((id, medicine) -> {
            System.out.println("Medicine ID: " + id + ", Name: " + medicine.getName() +
                    ", Quantity: " + medicine.getMedicineQuantity() + ", Alert Line: " + medicine.getAlertLine());
        });
    }

    // Additional methods to manage records and inventory could go here...

    // Example hardcoded administrators
    public static List<Administrator> createHardcodedAdministrators() {
        List<Administrator> administrators = new ArrayList<>();

        Administrator admin1 = new Administrator(
            UUID.fromString("123e4567-e89b-12d3-a456-556642440000"),
            "ADM001",
            "Alice Wong",
            "securepassword1"
        );

        Administrator admin2 = new Administrator(
            UUID.fromString("123e4567-e89b-12d3-a456-556642440001"),
            "ADM002",
            "Bob Tan",
            "securepassword2"
        );

        Administrator admin3 = new Administrator(
            UUID.fromString("123e4567-e89b-12d3-a456-556642440002"),
            "ADM003",
            "Carol Lee",
            "securepassword3"
        );

        administrators.add(admin1);
        administrators.add(admin2);
        administrators.add(admin3);

        return administrators;
    }


    // Approve a replenishment request
    public void approveReplenishmentRequest(UUID requestId) {
        ReplenishmentRequest request = replenishmentRequests.stream()
                .filter(r -> r.getRequestID().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Request not found"));

        Medicine medicine = medicineInventory.get(request.getMedicineId());
        if (medicine == null) {
            throw new IllegalStateException("Medicine not found in inventory");
        }

        medicine.setMedicineQuantity(medicine.getMedicineQuantity() + request.getQuantity());
        AdministratorMedicineLog log = new AdministratorMedicineLog(request.getMedicineId(), request.getQuantity(), "replenish");
        medicineLogs.add(log);

        request.setStatus("Approved");
        System.out.println("Replenishment request approved for medicine ID: " + request.getMedicineId());
    }

    // View and manage hospital staff
    public void viewManageHospitalStaff() {
        if (staffList.isEmpty()) {
            System.out.println("No staff found.");
        } else {
            staffList.forEach((staffID, staff) -> {
                System.out.println("Staff ID: " + staffID + ", Name: " + staff.getName() + ", Role: " + staff.getRole());
            });
        }
    }

    // Add a staff member
    public void addStaff(String staffID, String name, String role, String gender, boolean isActive) {
        Staff newStaff = new Staff(staffID, name, role, gender, isActive);
        staffList.put(staffID, newStaff);
        System.out.println("Staff added successfully: " + name);
    }

    // Update a staff member's information
    public void updateStaff(String staffID, String name, String role, String gender) {
        Staff staff = staffList.get(staffID);
        if (staff != null) {
            staff.setName(name);
            staff.setRole(role);
            staff.setGender(gender);
            System.out.println("Staff information updated successfully: " + name);
        } else {
            System.out.println("Staff ID not found.");
        }
    }

    // Remove a staff member
    public void removeStaff(String staffID) {
        if (staffList.remove(staffID) != null) {
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Staff ID not found.");
        }
    }

    public void displayFilteredStaff(String role, String gender, Integer age, Department department) {
        staffList.values().stream()
            .filter(staff -> (role == null || staff.getRole().equals(role)) &&
                             (gender == null || staff.getGender().equals(gender)) &&
                             (age == null || staff.getAge() == age) &&
                             (!(staff instanceof Doctor) || 
                             (department == null || ((Doctor) staff).getDepartment() == department)))
            .forEach(staff -> System.out.println("Staff ID: " + staff.getStaffID() +
                                                 ", Name: " + staff.getName() +
                                                 ", Role: " + staff.getRole() +
                                                 (staff instanceof Doctor ? 
                                                 ", Department: " + ((Doctor) staff).getDepartment() : "")));
    }
    

    // View appointment details
    public void viewAppointment() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            appointments.forEach(appointment -> {
                System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                        ", Patient ID: " + appointment.getPatientID() +
                        ", Doctor ID: " + appointment.getDoctorID() +
                        ", Status: " + appointment.getStatus() +
                        ", Date: " + appointment.getDateTime());
            });
        }
    }

    // View and manage medicine inventory
    public void viewManageMedicine() {
        if (medicineInventory.isEmpty()) {
            System.out.println("No medicines found in inventory.");
        } else {
            medicineInventory.forEach((medicineId, medicine) -> {
                System.out.println("Medicine ID: " + medicineId + ", Name: " + medicine.getName() +
                        ", Quantity: " + medicine.getMedicineQuantity() +
                        ", Alert Line: " + medicine.getAlertLine());
            });
        }
    }

    // Add a new medicine to the inventory
    public void addMedicine(String name, int quantity, int alertLine) {
        UUID medicineId = UUID.randomUUID();
        Medicine medicine = new Medicine(medicineId, name, quantity, alertLine);
        medicineInventory.put(medicineId, medicine);
        System.out.println("Medicine added successfully: " + name);
    }

    // Update medicine stock level or alert line
    public void updateMedicine(UUID medicineId, int newQuantity, int newAlertLine) {
        Medicine medicine = medicineInventory.get(medicineId);
        if (medicine != null) {
            medicine.setMedicineQuantity(newQuantity);
            medicine.setAlertLine(newAlertLine); //can adjust threshhold if demand more for example? 
            System.out.println("Medicine updated successfully: " + medicine.getName());
        } else {
            System.out.println("Medicine ID not found.");
        }
    }

    // Remove a medicine from the inventory
    public void removeMedicine(UUID medicineId) {
        if (medicineInventory.remove(medicineId) != null) {
            System.out.println("Medicine removed successfully.");
        } else {
            System.out.println("Medicine ID not found.");
        }
    }
}
