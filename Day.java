
public class Day 
{
	public enum Month {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC};
	public enum Days {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
	
	// attributes
	public int date;
	public Month month;
	public Days day;
	public EventTag event;
	
	//constructor
	public Day()
	{
		event= new EventTag();
		event.setTag(4); //set tag to nill
	}
	
	// actions
	public void setMonth(int month)
	{
		if(month>= 0 && month< Month.values().length)
		{
			this.month= Month.values()[month];
		}
		else
			System.out.println("Enter valid month number!");
	}
	
	public void setDay(int day)
	{
		
		if(day>=0 && day< Days.values().length)
		{
			this.day= Days.values()[day];
		}
		else
			System.out.println("Enter valid day number!");
	}
	
	public void setDate(int date) // month is always set first
	{
		if(month== Month.FEB) // 28 days
		{
			if(date>=1 && date<29) //assume not leap year
				this.date= date;
			else
				this.date=0;
		}
		else if(month== Month.APR || month== Month.JUN || month== Month.SEP || month== Month.NOV) //30 days
		{
			if(date>=1 && date<31)
				this.date= date;
			else
				this.date=0;
		}
		else // 31 days
		{
			if(date>=1 && date<32)
				this.date= date;
			else
				this.date=0;
		}	
	}
	
	public void setTag(int tag)
	{
		event.setTag(tag);
	}
	

}
