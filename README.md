# Hospital Management System (HMS)

## Overview
The **Hospital Management System (HMS)** is a Java-based application designed to automate hospital operations and streamline administrative processes. It ensures efficient management of hospital resources, enhances patient care, and provides an intuitive interface for every user. This project was developed with the goal of addressing the needs of various hospital stakeholders, including patients, doctors, pharmacists, and administrators.

---

## Key Features

### 1. Role-Specific Functionalities
HMS provides unique access and capabilities tailored to each user role:

#### **All Users:**
- Secure login with hospital ID and a default password ("password").
- Password change feature upon initial login.
- Role-specific menus for streamlined navigation.

#### **Patient:**
- Access personal medical records (e.g., Patient ID, past diagnoses, treatments).
- Update non-medical personal information.
- Manage appointments:
  - View available slots.
  - Schedule, reschedule, or cancel appointments.
  - Check appointment status (e.g., confirmed, canceled, completed).
- Review past appointment outcome records.

#### **Doctor:**
- Access and update patient medical records (e.g., diagnoses, prescriptions).
- Manage appointments:
  - Set availability.
  - Accept or decline appointment requests.
  - View upcoming appointments.
- Record appointment outcomes (e.g., consultation notes, prescribed medications).

#### **Pharmacist:**
- Manage prescription statuses (e.g., pending to dispensed).
- View and monitor medication inventory.
- Submit replenishment requests for low-stock medications.

#### **Administrator:**
- Manage hospital staff (e.g., add, update, or remove staff members).
- Oversee appointments and their statuses.
- Manage medication inventory (e.g., approve replenishment requests).
- Initialize the system with data imported from files (e.g., staff, patients, inventory).

---

### 2. Extra Feature: Leave Management for Hospital Staff
Our project includes an additional feature for hospital staff to apply for annual and medical leave:
- Staff can check leave availability and apply for annual or medical leave.
- If three or more staff members are already on annual leave for a particular day, further annual leave requests for that day will be automatically denied.

---

## User-Friendly Interface
HMS was designed with an intuitive interface to ensure ease of use for all stakeholders:
- **Clear Role-Based Menus:** Each role has a tailored menu, reducing complexity and making navigation straightforward.
- **Automated Updates:** Appointment slots and statuses are updated in real-time based on user actions.
- **Error Handling:** The system provides helpful error messages and guides users to take corrective actions.

---

## System Initialization
- The system can be initialized with predefined data imported from files:
  - Staff list (e.g., doctors, pharmacists).
  - Patient list.
  - Medication inventory (e.g., stock levels, low stock alerts).

---

## Installation and Usage
1. Clone the repository from GitHub.
2. Open the project in your Java development environment (e.g., Eclipse).
3. Run the `Main` class to start the HMS.
4. Login with the default credentials to explore the functionalities:
   - Username: D00x (x can be any number from 1 to 6)
   - Password: password (you will be prompted to change this on first login).

---

## Future Enhancements
- Integration with external systems for real-time inventory updates.
- Enhanced analytics for patient and staff management.
- Multi-language support for broader accessibility.

---

