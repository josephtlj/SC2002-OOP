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

import src.models.Administrator;
import src.models.Staff;
import src.interfaces.AdministratorDaoInterface;

public class AdministratorDao implements AdministratorDaoInterface {
    private static String ADMINISTRATORDB_PATH;

    static {
        try (InputStream input = new FileInputStream("src/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            ADMINISTRATORDB_PATH = prop.getProperty("ADMINISTRATORDB_PATH", "administratorDB.csv");
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    
    /** 
     * @param hospitalId
     * @return Administrator
     */
    @Override
    public Administrator getAdministratorByHospitalId(String hospitalId) {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMINISTRATORDB_PATH))) {
            // SKIP HEADER ROW
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Administrator administrator = parseAdministrator(line);
                if (administrator != null && administrator.getHospitalId().equals(hospitalId)) {
                    return administrator;
                }
            }

            // IF ADMINISTRATOR NOT FOUND
            return null;

        } catch (IOException e) {
            System.err.println("Error reading administrator database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    
    /** 
     * @return List<Staff>
     */
    @Override
    public List<Staff> getAllStaffAdministrators() {
        List<Staff> administrators = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ADMINISTRATORDB_PATH))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV and create a Pharmacist object
            String line;
            while ((line = br.readLine()) != null) {
                Administrator administrator = parseAdministrator(line);

                // Create Staff object and add to the list
                Staff StaffAdministrator = new Staff(administrator.getHospitalId(), administrator.getPassword(),
                        administrator.getRole(), administrator.getSalt(), administrator.getIsFirstLogin(),
                        administrator.getName(), administrator.getGender(), administrator.getAge());
                administrators.add(StaffAdministrator);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return administrators;
    }

    @Override
    public void updateAdministrator(Administrator administrator) {
        List<String> lines = new ArrayList<>();
        boolean administratorFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(ADMINISTRATORDB_PATH))) {
            // SKIP HEADER ROW
            String header = br.readLine();
            // KEEP HEADER ROW IN OUTPUT
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 8 && fields[0].equals(administrator.getHospitalId())) {
                    // UPDATE MATCHING ADMINISTRATOR RECORD
                    String updatedLine = formatAdministratorToCSV(administrator);
                    lines.add(updatedLine);
                    administratorFound = true;
                } else {
                    // KEEP EXISTING ADMINISTRATOR RECORD
                    lines.add(line);
                }
            }

            if (!administratorFound) {
                throw new IllegalArgumentException(
                        "Administrator with hospitalId " + administrator.getHospitalId() + " not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading the administrator database: " + e.getMessage());
            e.printStackTrace();
        }

        // UPDATE ADMINISTRATORDB.CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMINISTRATORDB_PATH))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the admninistrator database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // HELPER METHOD TO PARSE A ADMINISTRATOR OBJECT FROM A CSV LINE
    private Administrator parseAdministrator(String line) {
        try {
            String[] fields = line.split(",");
            if (fields.length != 8) {
                System.err.println("Invalid record format: " + line);
                return null;
            }

            String hospitalId = fields[0];
            String password = fields[1];
            Administrator.Role role = Administrator.Role.valueOf(fields[2]);
            byte[] salt = Base64.getDecoder().decode(fields[3]);
            boolean isFirstLogin = Boolean.parseBoolean(fields[4]);
            String name = fields[5];
            Administrator.Gender gender = Administrator.Gender.valueOf(fields[6]);
            int age = Integer.parseInt(fields[7]);

            return new Administrator(hospitalId, password, role, salt, isFirstLogin, name, gender, age);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing record: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // HELPER METHOD TO FORMAT ADMINISTRATOR OBJECT INTO CSV LINE
    private String formatAdministratorToCSV(Administrator administrator) {
        return String.join(",",
                administrator.getHospitalId(),
                administrator.getPassword(),
                administrator.getRole().name(),
                Base64.getEncoder().encodeToString(administrator.getSalt()),
                String.valueOf(administrator.getIsFirstLogin()),
                administrator.getName(),
                administrator.getGender().name(),
                String.valueOf(administrator.getAge()));
    }
}
