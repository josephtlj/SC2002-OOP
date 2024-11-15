import java.util.List;

public class AppointmentService {
    public void viewAllAppointmentOutcomes() {
        List<AppointmentOutcomeRecordDAO> records = AppointmentOutcomeRecordDAO.loadAllRecords("Pharmacist_v2/Appointments.csv");
        for (AppointmentOutcomeRecordDAO record : records) {
            System.out.println(record);
        }
    }
}
