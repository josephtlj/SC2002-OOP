package daos;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Doctor.Calendar.CalendarDayStatus;

public class DoctorCalendarDao 
{
    // ATTRIBUTES
    private static String DOCTORCALENDARDB_PATH;
    private File doctorCalendarFile; 

    // CONSTRUCTOR
    public DoctorCalendarDao(String ID) 
    {
        //LOAD CONFIGURATION FROM CONFIG.PROPERTIES FILE
        try (InputStream input = new FileInputStream("resources/config.properties")) 
        {
            Properties prop = new Properties();
            prop.load(input);
            DOCTORCALENDARDB_PATH = prop.getProperty("DOCTORCALENDAREDB_PATH", "data/Doctor/Calendar");
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }

        //LOCATE THE CORRECT CSV FILE WITH MATCHING ID
        File calendarDir = new File(DOCTORCALENDARDB_PATH);
        if (calendarDir.exists() && calendarDir.isDirectory()) 
        {
            File[] files = calendarDir.listFiles(name -> name.equals(ID + "_Cal.csv"));
            this.doctorCalendarFile = files[0]; //ASSIGN MATCHING FILE
    
        }

    }

    // METHOD TO READ DATES FOR SPECIFIC STATUS
    private List<LocalDate> getDatesByStatus(CalendarDayStatus status) 
    {
        List<LocalDate> dates = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(doctorCalendarFile))) 
        {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(",");
                LocalDate date = LocalDate.parse(parts[0], java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (CalendarDayStatus.valueOf(parts[1]) == status) 
                {
                    dates.add(date);
                }
            }
        } 
        catch (IOException | IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }

        return dates;
    }
    
    //METHODS TO READ CALENDAR FILE

    //RETURN MEDICAL_LEAVE DATES
    public List<LocalDate> getMedicalLeaveDates()
    {
        return getDatesByStatus(CalendarDayStatus.MEDICAL_LEAVE);
    }

    //RETURN ANNUAL_LEAVE DATES
    public List<LocalDate> getAnnualLeaveDates()
    {
        return getDatesByStatus(CalendarDayStatus.ANNUAL_LEAVE);
    }

    //RETURN MEETING DATES
    public List<LocalDate> getMeetingDates()
    {
        return getDatesByStatus(CalendarDayStatus.MEETING);
    }

    //RETURN TRAINING DATES
    public List<LocalDate> getTrainingDates()
    {
        return getDatesByStatus(CalendarDayStatus.TRAINING);
    }

    //RETURN AVAILABLE DATES
    public List<LocalDate> getAvailableDates()
    {
        return getDatesByStatus(CalendarDayStatus.AVAILABLE);
    }

    //RETURN OTHERS DATES
    public List<LocalDate> getOthersDates()
    {
        return getDatesByStatus(CalendarDayStatus.OTHERS);
    }

    //RETURN NA DATES
    public List<LocalDate> getNaDates()
    {
        return getDatesByStatus(CalendarDayStatus.NA);
    }
}
