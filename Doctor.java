
public class Doctor 
{
	// instance variables
	private DoctorSchedule schedule;
	private DoctorEmail email;
	
	// constructor
	public Doctor()
	{
		schedule= new DoctorSchedule();
		email= new DoctorEmail(DoctorEmail.Subject.LOGIN_ALERT);
	}
	
	//instance methods
	public void printMenu()
	{
		System.out.println("Doctor Menu:");
	    System.out.println("-------------------------------------------");
	    System.out.println("\u2022 View Patient Medical Records");
	    System.out.println("\u2022 Update Patient Medical Records");
	    System.out.println("\u2022 View Personal Schedule");
	    System.out.println("\u2022 Set Availability for Appointments");
	    System.out.println("\u2022 Accept or Decline Appointment Requests");
	    System.out.println("\u2022 View Upcoming Appointments");
	    System.out.println("\u2022 Record Appointment Outcome");
	    System.out.println("\u2022 Logout");
	}
	
	public void Logout()
	{
		email.subject= DoctorEmail.Subject.LOGOUT_ALERT;
	}
	
	
	
	

}
