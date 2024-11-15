package Doctor;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class CalendarView {

    private final Calendar calendar;

    public CalendarView(Calendar calendar) 
	{
        this.calendar = calendar;
    }

    public void displayMonth(int month) 
	{
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        boolean isCurrentMonth = today.getMonthValue() == month;

        // Start from today if the input month is the current month, otherwise start from the 1st.
        int startDay = isCurrentMonth ? today.getDayOfMonth() : 1;
        int daysInMonth = calendar.getDaysInMonth(month);

        System.out.println("Date       | Day       | Status");
        System.out.println("---------------------------------");

        for (int day = startDay; day <= daysInMonth; day++) 
		{
            LocalDate date = LocalDate.of(year, month, day);
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            Optional<CalendarDay> optionalDay = calendar.getDay(month, day);

            String status = optionalDay.map(CalendarDay::getDayStatus).map(Enum::name).orElse("N/A");

            System.out.printf("%02d/%02d/%d | %-9s: %s%n", day, month, year, dayOfWeek, status);
        }
    }
}
