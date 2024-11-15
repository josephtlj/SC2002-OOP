package Doctor;

public class DoctorScheduleManager 
{
    //ATTRIBUTES
    private DoctorScheduleDao doctorScheduleDao;
    
    //CONSTRUCTOR
    public DoctorScheduleManager(DoctorScheduleDao doctorScheduleDao)
    {
        this.doctorScheduleDao= doctorScheduleDao;
    }

    //METHODS
    public boolean applyAnnualLeave()
    {
        //RETURN TRUE IF SUCCESSFUL
        //CAP THE NUMBER OF DOCTORS WHO CAN GO ON LEAVE ON A CERTAIN DAY
    }

    public boolean applyMedicalLeave()
    {
        //RETURN TRUE IF SUCCESSFUL
    }
}
