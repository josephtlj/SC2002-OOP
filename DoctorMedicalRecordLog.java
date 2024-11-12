import java.time.LocalDate;
import java.util.UUID;

public class DoctorMedicalRecordLog 
{
    // Attributes
    private String logId;
    private LocalDate date; 
    private String remark;

    // Constructor
    public DoctorMedicalRecordLog(LocalDate date) 
    {
        this.logId = generateLogId();
        this.date = date; 
    }

    // Methods
    public String generateLogId() 
    {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    public void setRemark(String remark) 
    {
        this.remark = remark;
    }

    public String getRemark() 
    {
        return remark;
    }

    public String getLogId() 
    {
        return logId;
    }

    public LocalDate getDate()
    {
        return date;
    }
}
