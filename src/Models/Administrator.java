package Models;
import java.io.*;
import java.util.*;

public class Administrator extends User {
    private UUID adminId;
    private List<ReplenishmentRequest> replenishmentRequests;
    private List<AdministratorMedicineLog> medicineLogs;
    private Map<String, Staff> staffList;
    //need to subintegrate to test
    private Map<UUID, Medicine> medicineInventory;
    private Map<String, MedicalRecord> medicalRecords;
    private List<Appointment> appointments;

    private static final String STAFF_DATA_FILE = "staffData.csv";
    private static final String MEDICAL_RECORD_DATA_FILE = "medicalRecordData.csv";
    private static final String MEDICINE_LIST_FILE = "Medicine_List.csv";

    public Administrator(UUID adminId, String staffID, String name, String password) throws IOException {
        super(staffID, name, password);
        this.adminId = adminId;
        this.replenishmentRequests = new ArrayList<>();
        this.medicineLogs = new ArrayList<>();
        this.staffList = new HashMap<>();
        this.medicineInventory = new HashMap<>();
        this.medicalRecords = new HashMap<>();
        this.appointments = new ArrayList<>();

        loadStaffData();
        loadMedicalRecords();
        loadMedicineList();
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

    // Display filtered list of staff by role, gender, or other criteria
    public void displayFilteredStaff(String role, String gender, Integer age) {
        staffList.values().stream()
            .filter(staff -> (role == null || staff.getRole().equals(role)) &&
                             (gender == null || staff.getGender().equals(gender)) &&
                             (age == null || staff.getAge() == age))
            .forEach(staff -> System.out.println("Staff ID: " + staff.getStaffID() +
                                                 ", Name: " + staff.getName() +
                                                 ", Role: " + staff.getRole()));
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
