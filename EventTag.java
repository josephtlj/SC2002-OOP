
public class EventTag 
{
	public enum Tag {AVAILABLE, APPOINTMENT, OTHERS, NILL};
	
	//attribute
	public Tag tag;
	
	//constructor
	public EventTag()
	{
		tag= Tag.NILL; //default
	}
	
	//instance methods
	public void setTag(int tag)
	{
		tag--;
		if(tag>=0 && tag<Tag.values().length)
		{
			this.tag= Tag.values()[tag];
		}
		else
			System.out.println("Enter valid tag number!");
	}
	
	public String toString()
	{
		switch(this.tag)
		{
		case AVAILABLE:
			return "Available";
		case APPOINTMENT:
			return "Appointment";
		case OTHERS:
			return "Others";
		case NILL:
			return "Nill";
		default:
			return "Nill";
		}
	}

}
