class CalendarDay
{
    private CalendarDayStatus status;

    public CalendarDay()
    {
        this.status = CalendarDayStatus.FREE; // Default status is FREE
    }

    public CalendarDayStatus getStatus()
    {
        return status;
    }

    public void setStatus(CalendarDayStatus status)
    {
        this.status = status;
    }
}
