import java.util.Date;
import java.util.UUID;

public class PharmacistMedicineLog 
{

    // Data members
    private UUID logId;     // Unique log identifier
    private Date date;       // Date of the log entry
    private String remark;   // Remarks related to the log entry

    // Default constructor
    public PharmacistMedicineLog() 
    {
        this.logId = UUID.randomUUID();  
        this.date = new Date();          
        this.remark = "";              
    }

    // Constructor with parameters 
    public PharmacistMedicineLog(UUID logId, Date date, String remark) 
    {
        this.logId = logId;
        this.date = date;
        this.remark = remark;
    }

    // Getter for logId
    public UUID getLogId() 
    {
        return logId;
    }

    // Setter for logId
    public void setLogId(UUID logId) 
    {
        this.logId = logId;
    }

    // Getter for date
    public Date getDate() 
    {
        return date;
    }

    // Setter for date
    public void setDate(Date date) 
    {
        this.date = date;
    }

    // Getter for remark
    public String getRemark() 
    {
        return remark;
    }

    // Setter for remark
    public void setRemark(String remark) 
    {
        this.remark = remark;
    }
}
