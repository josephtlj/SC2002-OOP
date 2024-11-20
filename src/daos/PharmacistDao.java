package src.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import src.models.Pharmacist;
import src.models.Staff;
import src.interfaces.PharmacistDaoInterface;

public class PharmacistDao implements PharmacistDaoInterface {
    private static String PHARMACISTDB_PATH;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            PHARMACISTDB_PATH = prop.getProperty("PHARMACISTDB_PATH", "pharmacistDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    
    /** 
     * @param hospitalId
     * @return Pharmacist
     */
    @Override
    public Pharmacist getPharmacistByHospitalId(String hospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(PHARMACISTDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Pharmacist pharmacist = parsePharmacist(line);
                if (pharmacist != null && pharmacist.getHospitalId().equals(hospitalId)) {
                    return pharmacist;
                }
            }

            // IF PATIENT NOT FOUND
            return null;

        } catch (IOException e) {
            System.err.println("Error reading pharmacist database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    
    /** 
     * @return List<Staff>
     */
    @Override
    public List<Staff> getAllStaffPharmacists(){
        List<Staff> pharmacists = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PHARMACISTDB_PATH))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV and create a Pharmacist object
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 8) // Ensure the correct number of columns
                {
                    Pharmacist pharmacist = parsePharmacist(line);

                    // Create Staff object and add to the list
                    Staff Staffpharmacist = new Staff(pharmacist.getHospitalId(), pharmacist.getPassword(), pharmacist.getRole(), pharmacist.getSalt(), pharmacist.getIsFirstLogin(), pharmacist.getName(), pharmacist.getGender(), pharmacist.getAge());
                    pharmacists.add(Staffpharmacist);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pharmacists;
    }

    @Override
    public void updatePharmacist(Pharmacist pharmacist) {
        List<String> lines = new ArrayList<>();
        boolean pharmacistFound = false;

        System.out.println(true);

        try (BufferedReader br = new BufferedReader(new FileReader(PHARMACISTDB_PATH))) {
            // SKIP HEADER ROW
            String header = br.readLine();
            // KEEP HEADER ROW IN OUTPUT
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 8 && fields[0].equals(pharmacist.getHospitalId())) {
                    // UPDATE MATCHING MEDICAL RECORD RECORD
                    String updatedLine = formatPharmacistToCSV(pharmacist);
                    lines.add(updatedLine);
                    pharmacistFound = true;
                } else {
                    // KEEP EXISTING MEDICAL RECORD RECORD
                    lines.add(line);
                }
            }

            if (!pharmacistFound) {
                throw new IllegalArgumentException(
                        "Medical Record with hospitalId " + pharmacist.getHospitalId() + " not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the medical record database: " + e.getMessage());
            e.printStackTrace();
        }

        // UPDATE MEDICALRECORDDB.CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PHARMACISTDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the medical record database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // HELPER METHOD TO PARSE A PHARMACIST OBJECT FROM A CSV LINE
    private Pharmacist parsePharmacist(String line) {
        try {
            String[] fields = line.split(",");
            if (fields.length != 8) {
                System.err.println("Invalid record format: " + line);
                return null;
            }

            String hospitalId = fields[0];
            String password = fields[1];
            Pharmacist.Role role = Pharmacist.Role.valueOf(fields[2]);
            byte[] salt = Base64.getDecoder().decode(fields[3]);
            boolean isFirstLogin = Boolean.parseBoolean(fields[4]);
            String name = fields[5];
            Pharmacist.Gender gender = Pharmacist.Gender.valueOf(fields[6]);
            int age = Integer.parseInt(fields[7]);

            return new Pharmacist(hospitalId, password, role, salt, isFirstLogin, name, gender, age);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing record: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // HELPER METHOD TO FORMAT PHARMACIST OBJECT INTO CSV LINE
    private String formatPharmacistToCSV(Pharmacist pharmacist) {
        return String.join(",",
                pharmacist.getHospitalId(),
                pharmacist.getPassword(),
                pharmacist.getRole().name(),
                Base64.getEncoder().encodeToString(pharmacist.getSalt()),
                String.valueOf(pharmacist.getIsFirstLogin()),
                pharmacist.getName(),
                pharmacist.getGender().name(),
                String.valueOf(pharmacist.getAge()));
    }
}
