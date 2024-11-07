import java.util.*;


public class DoctorSchedule extends Calendar
{
	public enum Tag {AVAILABLE, APPOINTMENT, OTHERS, NILL};
	
	//attribute
	public Tag tag;
	
	// constructor
	public DoctorSchedule()
	{
		super();
		this.tag= Tag.NILL;
	}
	
	// instance methods
	private void viewSchedule()
	{
		Scanner sc= new Scanner(System.in);
		System.out.println("Which month do you want to view?");
		System.out.printf("(1) Jan\n(2) Feb\n(3) Mar\n(4) Apr\n(5) May\n(6) Jun\n(7) Jul\n(8) Aug\n(9) Sep\n(10) Oct\n(11) Nov\n(12) Dec\n");
		int month= sc.nextInt();
		sc.nextLine(); //read \n
		
		System.out.println("Doctor's Schedule:");
		System.out.printf("%-6s %-10s %-10s \n", "Date", "Day", "Event");
        System.out.println("---------------------------------------------");
        
		for (int i=0;i<31;i++)
		{
			Day day= this.calendar[month-1][i]; 
			if(day.date !=0)
				System.out.printf("%-6d %-10s %-10s \n", day.date, day.day, day.event.toString());
		}
	}
	
	private void setAvailability(int month, int date)
	{
		Day day= this.calendar[month-1][date];
		
		if(day.date==0)
			System.out.println("Enter valid date!");
		
		day.setTag(1);
	}
	
	public boolean addAppointment(Day day)
	{
		
		if(day.date==0) 
		{
			System.out.println("Enter valid date!");
			return false;
		}
		
		day.setTag(2);
		return true;
		
	}
	
	private void viewUpcomingAppointments()
	{
		System.out.println("Upcoming Appointments:");
		System.out.printf("%-10s %-6s %-10s \n", "Month", "Date", "Day");
        System.out.println("---------------------------------------------");
        
        for(int month=1;month<=12;month++)
        {
        	for(int date=1;date<=31;date++)
        	{
        		Day day= this.calendar[month-1][date-1];
        		if(day.event.toString().equals("Appointment"))
        		{
        			System.out.printf("%-10s %-6s %-10s \n", day.month, day.date, day.day);
        		}
        	}
        }
	}
	
	/* 
	 * TO DO:
	 * 1. exception handling in add appointment method
	 */
		
	
	
	
	
	
	public static void main(String[] args) 
	{
        DoctorSchedule ds = new DoctorSchedule();
        ds.viewSchedule(); 
        ds.viewUpcomingAppointments();
        
	}
	

}
