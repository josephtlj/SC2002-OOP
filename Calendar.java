
public class Calendar
{
	//attributes
	public Day[][] calendar;
	
	//constructor
	public Calendar()
	{
		calendar= new Day[12][31];
		
		for(int i=0;i<12;i++)
		{
			for(int j=0;j<31;j++)
			{
				calendar[i][j]= new Day();
				calendar[i][j].setMonth(i); // month always set first
				calendar[i][j].setDay(j%7);
				calendar[i][j].setDate(j+1);
			}
		}
	}

}
/*
 * calendar is not accurate
 * ie. if 31 Jan is a Wednesday, 1 Feb restarts on Monday
 */
