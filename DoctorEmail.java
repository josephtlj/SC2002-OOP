
public class DoctorEmail 
{
	public enum Subject {APPOINTMENT_REQUEST,OTHERS, LOGIN_ALERT, LOGOUT_ALERT};
	public enum Inbox {EMPTY,NEW_EMAIL};
	
	//attributes
	public Subject subject;
	public Inbox inbox;
	
	//constructor
	public DoctorEmail(Subject subject)
	{
		this.subject= subject;
		inbox= Inbox.NEW_EMAIL;
	}
	
	//instance methods
	public void sendAcceptance(Day day, DoctorSchedule schedule)
	{
		boolean outcome= schedule.addAppointment(day);
		if(outcome)
		{
			System.out.println("Appointment successfully accepted");
		}
		else
		{
			System.out.println("Error. Unable to accept appointment");
		}
	}
	
	public void sendDecline()
	{
		System.out.println("Appointment request has been rejected");
	}

}
