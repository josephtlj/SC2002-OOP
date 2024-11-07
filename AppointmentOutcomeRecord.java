
public class AppointmentOutcomeRecord 
{
	public enum ServiceType {CONSULTATION, xRAY, BLOOD_TEST, OTHERS};

	//attributes
	private Day day;
	private ServiceType service;
	private String consultNotes;
	// prescribed medication and status--> array
	
	//constructor
	public AppointmentOutcomeRecord(Day day, ServiceType service, String notes)
	{
		this.day= day;
		this.service= service;
		this.consultNotes= notes;
		
	}
	
	//get and set mthods
}
