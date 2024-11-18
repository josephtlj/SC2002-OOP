package Administrator.backup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AdministratorMedicineLog{
    private final UUID logID;   // Unique identifier for the log entry (immutable)
    private final LocalDateTime date;    // Date when the action was logged (immutable)
    private String remark; // Remark or description of the action

    // Constructor to initialize the log entry
    public AdministratorMedicineLog(String remark) {
        this.logID = UUID.randomUUID();  // Generates a unique ID for the log entry
        this.date = LocalDateTime.now(); // Sets the current date and time for the log
        this.remark = remark;            // Sets the remark describing the action
    }

    // Getter for logID
    public UUID getLogID() {
        return logID;
    }

    // Getter for date
    public LocalDateTime getDate() {
        return date;
    }

    // Getter for remark
    public String getRemark() {
        return remark;
    }

    // Setter for remark
    public void setRemark(String remark) {
        this.remark = remark;
    }

    // Optional: Override the toString method for easier logging/printing
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "AdministratorMedicineLog{" +
                "logID=" + logID +
                ", date=" + date.format(formatter) +
                ", remark='" + remark + '\'' +
                '}';
    }
}
