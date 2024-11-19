package src.daos;

import java.io.*;
import java.util.*;

import src.interfaces.DoctorDaoInterface;
import src.models.Doctor;

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
                    String name = parts[1];
                    String department = parts[2];
                    String gender = parts[3];
                    int age = Integer.parseInt(parts[4]);
                    String password = parts[5];
                    boolean isFirstLogin = Boolean.parseBoolean(parts[6]);
                    byte[] salt = Base64.getDecoder().decode(parts[7]);

                    // Create Doctor object and add to the list
                    Doctor doctor = new Doctor(id, name, department, password, isFirstLogin, salt);
                    doctors.add(doctor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    // READ DOCTOR BY HOSPITAL ID
    public Doctor getDoctorByHospitalId(String doctorHospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 8) // Ensure the correct number of columns
                {
                    // Parse CSV fields
                    String id = parts[0];
                    String name = parts[1];
                    String department = parts[2];
                    String gender = parts[3];
                    int age = Integer.parseInt(parts[4]);
                    String password = parts[5];
                    boolean isFirstLogin = Boolean.parseBoolean(parts[6]);
                    byte[] salt = Base64.getDecoder().decode(parts[7]);

                    // IF HOSPITALID FOUND, RETURN DOCTOR DATA AS USER OBJECT
                    if (id.equals(doctorHospitalId)) {
                        return new Doctor(id, name, department, password, isFirstLogin, salt);
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
    public void updateDoctorPasswordByHospitalId(String doctorNewPassword, String doctorHospitalId) {
        List<Doctor> doctors = getAllDoctors();

        // UPDATE DOCTOR'S PASSWORD WITH MATCHING HOSPITALID
        for (Doctor doctor : doctors) {
            if (doctor.getHospitalId().equals(doctorHospitalId)) {
                doctor.setPassword(doctorNewPassword);
                doctor.setIsFirstLogin(false);
                break; // Exit the loop once the doctor is found and updated
            }
        }

        // WRITE BACK TO DOCTORDB.CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCTORDB_PATH))) {
            writer.write("ID,Name,Department,Age,Gender,Password,IsFirstLogin,salt\n");

            // Write each doctor's data
            for (Doctor doctor : doctors) {
                StringBuilder sb = new StringBuilder();
                sb.append(doctor.getHospitalId()).append(",")
                        .append(doctor.getRole().name()).append(",")
                        .append(doctor.getDepartment()).append(",")
                        .append(doctor.getGender()).append(",")
                        .append(doctor.getAge()).append(",")
                        .append(doctor.getPassword()).append(",")
                        .append(doctor.getIsFirstLogin()).append("\n")
                        .append(Base64.getEncoder().encodeToString(doctor.getSalt())).append(",");
                writer.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}