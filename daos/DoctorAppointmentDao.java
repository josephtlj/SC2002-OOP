package daos;

import java.io.*;
import java.util.*;
import Enum.AppointmentStatus;
import interfaces.DoctorAppointmentDaoInterface;
import models.Appointment;
import models.AppointmentTimeSlot;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DoctorAppointmentDao implements DoctorAppointmentDaoInterface
{
    // ATTRIBUTES
    private static String DOCTORAPPOINTMENTSLOTSDB_PATH;
    private File doctorAppointmentSlotsFile;

    // CONSTRUCTOR
    public DoctorAppointmentDao(String ID) 
    {
        //LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("resources/config.properties")) 
        {
            Properties prop = new Properties();
            prop.load(input);
            DOCTORAPPOINTMENTSLOTSDB_PATH = prop.getProperty("DOCTORAPPOINTMENTSLOTSDB_PATH", "data/Doctor/AppointmentSlots");
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }

        //LOCATE THE CORRECT CSV FILE WITH MATCHING ID
        File appointmentSlotsDir = new File(DOCTORAPPOINTMENTSLOTSDB_PATH);
        if (appointmentSlotsDir.exists() && appointmentSlotsDir.isDirectory()) 
        {
            File[] files = appointmentSlotsDir.listFiles(name -> name.equals(ID + "_appSlot.csv"));
            this.doctorAppointmentSlotsFile = files[0]; //ASSIGN MATCHING FILE
    
        }

    }

    //READ ALL APPOINTMENTSLOTS
    public List<Appointment> getAllAppointments(String doctorID) 
    {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(doctorAppointmentSlotsFile)))  //is this correct?
        {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV and create a Appointment object
            String line;
            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(","); // Split the line by commas
                if (parts.length == 7) // Ensure the correct number of columns
                {
                    // Parse CSV fields
                    String date = parts[0];
                    String startTime = parts[1];
                    String endTime = parts[2];
                    String appointmentYesNo = parts[4];
                    String patientID= parts[5];
                    String status= parts[6];

                    if(appointmentYesNo.equals("Yes"))
                    {
                        //Create a Appointment object and add to the list
                        Appointment newAppointment = new Appointment(status, date, startTime, endTime,patientID, doctorID );
                        appointments.add(newAppointment);
                    }
                }
            }
        } 
    catch (IOException e) 
    {
        e.printStackTrace();
    }
    return appointments;
    }


    // UPDATE APPOINTMENT AVAILABILITY
    public boolean updateAppointmentAvailability(LocalDate date, String availability) 
    {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(doctorAppointmentSlotsFile))) 
        {
            // Read the header and add it to updatedLines
            String header = reader.readLine();
            updatedLines.add(header);

            // Iterate through the file and update the availability for the given date
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(","); // Split the line by commas

                if (parts.length == 7) // Ensure the correct number of columns
                {
                    // Parse the Date field (first column)
                    LocalDate lineDate = LocalDate.parse(parts[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    // If the date matches, update the availability column
                    if (lineDate.equals(date)) 
                    {
                        parts[3] = availability; // Update the "Availability" column

                        // If availability is set to "No", set dependent columns to "NA"
                        if (availability.equalsIgnoreCase("No")) 
                        {
                            parts[4] = "NA"; // Appointment
                            parts[5] = "NA"; // PatientID
                            parts[6] = "NA"; // Status
                        }
                    }
                    // Rebuild the line and add it to updatedLines
                    updatedLines.add(String.join(",", parts));
                }
            }

            // Write updated data back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(doctorAppointmentSlotsFile))) 
            {
                for (String updatedLine : updatedLines) 
                {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }
            return true;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE APPOINTMENT STATUS
    public boolean updateAppointmentStatus(Appointment appointment) 
    {
        // Retrieve appointment details
        AppointmentStatus status = appointment.getAppointmentStatus();
        LocalDate date = appointment.getAppointmentDate();
        AppointmentTimeSlot timeSlot = appointment.getAppointmentTimeSlot();
        String patientID = appointment.getPatientID();

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(doctorAppointmentSlotsFile))) 
        {
            // Read the header and add it to updatedLines
            String header = reader.readLine();
            updatedLines.add(header);

            // Iterate through the file to find the matching appointment
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(","); // Split the line by commas

                if (parts.length == 7) // Ensure the correct number of columns
                {
                    // Parse the Date, Start Time, and End Time fields
                    LocalDate lineDate = LocalDate.parse(parts[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalTime startTime = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime endTime = LocalTime.parse(parts[2], DateTimeFormatter.ofPattern("HH:mm"));

                    // If the appointment matches the date and time slot, update fields
                    if (lineDate.equals(date) && 
                        startTime.equals(timeSlot.getStartTime()) && 
                        endTime.equals(timeSlot.getEndTime())) 
                    {
                        switch (status) 
                        {
                            case PENDING:
                                parts[3] = "No"; // Availability
                                parts[4] = "Yes"; // Appointment
                                parts[5] = patientID; // PatientID
                                parts[6] = "PENDING"; // Status
                                break;

                            case CONFIRMED:
                                parts[6] = "PENDING"; // Change status to PENDING
                                break;

                            case DECLINED:
                                parts[3] = "Yes"; // Availability
                                parts[4] = "No"; // Appointment
                                parts[5] = "NA"; // PatientID
                                parts[6] = "NA"; // Status
                                break;

                            default:
                                break; // No changes for other statuses
                        }
                    }
                    // Rebuild the line and add it to updatedLines
                    updatedLines.add(String.join(",", parts));
                }
            }

            // Write the updated data back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(doctorAppointmentSlotsFile))) 
            {
                for (String updatedLine : updatedLines) 
                {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }
            return true;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
}


}
