package src.daos;

import java.io.*;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import src.interfaces.DoctorDaoInterface;
import src.models.Doctor;
import src.models.Staff;
import src.models.User;
import src.utils.ENUM.DoctorDepartment;

public class DoctorDao implements DoctorDaoInterface {
    // LOAD NECESSARY PATHS FROM CONFIG.PROPERTIES
    private static String DOCTORDB_PATH;

    static {
        // LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            DOCTORDB_PATH = prop.getProperty("DOCTORDB_PATH", "DoctorDB.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    /** 
     * @return List<Doctor>
     */
    // READ ALL DOCTORS
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORDB_PATH))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV and create a Doctor object
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 8) // Ensure the correct number of columns
                {
                    // Parse CSV fields
                    String id = parts[0];
                    String password = parts[1];
                    Doctor.Role role = Doctor.Role.valueOf(parts[2]);
                    byte[] salt = Base64.getDecoder().decode(parts[3]);
                    boolean isFirstLogin = Boolean.parseBoolean(parts[4]);
                    String name = parts[5];
                    Doctor.Gender gender = Doctor.Gender.valueOf(parts[6]);
                    int age = Integer.parseInt(parts[7]);
                    DoctorDepartment department = DoctorDepartment.valueOf(parts[8]);

                    // Create Doctor object and add to the list
                    Doctor doctor = new Doctor(id, password, role, salt, isFirstLogin, name, gender, age, department);
                    doctors.add(doctor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    
    /** 
     * @return List<Staff>
     */
    public List<Staff> getAllStaffDoctors(){
        List<Staff> doctors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORDB_PATH))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV and create a Doctor object
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 9) // Ensure the correct number of columns
                {
                    // Parse CSV fields
                    String id = parts[0];
                    String password = parts[1];
                    Doctor.Role role = Doctor.Role.valueOf(parts[2]);
                    byte[] salt = Base64.getDecoder().decode(parts[3]);
                    boolean isFirstLogin = Boolean.parseBoolean(parts[4]);
                    String name = parts[5];
                    Doctor.Gender gender = Doctor.Gender.valueOf(parts[6]);
                    int age = Integer.parseInt(parts[7]);
                    DoctorDepartment department = DoctorDepartment.valueOf(parts[8]);

                    // Create Staff object and add to the list
                    Staff doctor = new Staff(id, password, role, salt, isFirstLogin, name, gender, age);
                    
                    
                    doctors.add(doctor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    
    /** 
     * @param doctorHospitalId
     * @return Doctor
     */
    // READ DOCTOR BY HOSPITAL ID
    public Doctor getDoctorByHospitalId(String doctorHospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 9) // Ensure the correct number of columns
                {

                    // Parse CSV fields
                    String id = parts[0];
                    String password = parts[1];
                    Doctor.Role role = Doctor.Role.valueOf(parts[2]);
                    byte[] salt = Base64.getDecoder().decode(parts[3]);
                    boolean isFirstLogin = Boolean.parseBoolean(parts[4]);
                    String name = parts[5];
                    Doctor.Gender gender = Doctor.Gender.valueOf(parts[6]);
                    int age = Integer.parseInt(parts[7]);
                    DoctorDepartment department = DoctorDepartment.valueOf(parts[8]);

                    // IF HOSPITALID FOUND, RETURN DOCTOR DATA AS USER OBJECT
                    if (id.equals(doctorHospitalId)) {
                        return new Doctor(id, password, role, salt, isFirstLogin, name, gender, age, department);
                    }
                }
            }
            // IF DOCTOR NOT FOUND
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE DOCTOR'S PASSWORD USING HOSPITALID
    public void updateDoctorPasswordByHospitalId(String doctorNewPassword, byte[] newSalt, String doctorHospitalId) {
        List<String> lines = new ArrayList<>();
        boolean doctorFound = false;
    
        // Read the file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORDB_PATH))) {
            // Read and store the header
            String header = br.readLine();
            lines.add(header);
    
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
    
                // Validate the record format (number of fields matches your Doctor model)
                if (fields.length == 9 && fields[0].equals(doctorHospitalId)) {
                    // Update the doctor record
                    Doctor updatedDoctor = new Doctor(
                            fields[0],                             // hospitalId
                            doctorNewPassword,                    // password
                            Doctor.Role.valueOf(fields[2]),       // role
                            newSalt,                              // salt
                            false,                   // isFirstLogin (updated)
                            fields[5],                            // name
                            Doctor.Gender.valueOf(fields[6]),     // gender
                            Integer.parseInt(fields[7]),          // age
                            DoctorDepartment.valueOf(fields[8])   // department
                    );
    
                    // Convert updated doctor back to CSV format
                    String updatedLine = formatDoctorToCSV(updatedDoctor);
                    lines.add(updatedLine);
                    doctorFound = true;
                } else {
                    // Keep the existing line if it's not the target record
                    lines.add(line);
                }
            }
    
            if (!doctorFound) {
                throw new IllegalArgumentException(
                        "Doctor with hospitalId " + doctorHospitalId + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the doctor database: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    
        // Write all lines back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTORDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
            System.out.println("Doctor password updated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to the doctor database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    private String formatDoctorToCSV(Doctor doctor) {
        return String.join(",",
                doctor.getHospitalId(),
                doctor.getPassword(),
                doctor.getRole().name(),
                Base64.getEncoder().encodeToString(doctor.getSalt()),
                String.valueOf(doctor.getIsFirstLogin()),
                doctor.getName(),
                doctor.getGender().name(),
                String.valueOf(doctor.getAge()),
                doctor.getDepartment());
    }
}