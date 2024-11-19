package src.views;

import java.util.*;

import src.interfaces.InputForDateMonthTimeSlotViewInterface;
import src.models.AppointmentTimeSlot;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class InputForDateMonthTimeSlotView implements InputForDateMonthTimeSlotViewInterface
{

    //ATTRIBUTES
    Scanner scanner= new Scanner(System.in);

    //METHODS

    //CHOOSE TO VIEW DAY OR MONTH
    public int viewByDateOrMonth()
    {
        int choice=-1;
        do
        {
            try
            {
                System.out.println("Do you want to view by (1) Date or (2) Month? ");
                choice= scanner.nextInt();
                scanner.nextLine();

                switch(choice)
                {
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    default:
                        System.out.println("Please enter a valid choice!");
                        break;
                }
            }
            catch (InputMismatchException inputMismatchException) 
            {
                System.out.println("Please enter a valid integer only!\n");
                scanner.nextLine();
            }
        }while(choice!=3);
        return 0; //never reach here
    }


    //CHOOSE MONTH
    public LocalDate viewWhichDate() 
    {
        boolean validInput = false;
        LocalDate date = null;
    
        do {
            try {
                System.out.print("Enter the date (dd/MM/yyyy): ");
                String dateInput = scanner.nextLine().trim();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateInput, dateFormatter);
    
                // Check if the date is in the current year
                if (date.getYear() == LocalDate.now().getYear()) {
                    validInput = true;
                } else {
                    System.out.println("The date is not in the current year. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                validInput = false;
            }
        } while (!validInput);
    
        return date;
    }
    

    //CHOOSE MONTH
    public int viewWhichMonth()
    {
        boolean validInput= false;
        int month=-1;
        do
        {
            try
            {
                System.out.print("Enter the month(1 for Jan and 12 for Dec): ");
                month= scanner.nextInt();
                scanner.nextLine();
                validInput=true;
            }
            catch (Exception e) 
            {
                System.out.println("Invalid input for month.");
                validInput=false;
            }
        }while(!validInput);

        return month;
    }

    //CHOOSE TIME SLOT
    public AppointmentTimeSlot viewWhichTimeSlot()
    {
        String startTime = null;
        while (startTime == null) {
            System.out.print("Enter the time slot start time (HH:mm): ");
            String startTimeInput = scanner.nextLine().trim();
            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                startTime = timeFormatter.format(timeFormatter.parse(startTimeInput));
            } catch (Exception e) {
                System.out.println("❌ Invalid time format. Please use HH:mm.");
            }
        }
        final String StartTime=startTime;
    
        // Get and validate the end time
        String endTime = null;
        while (endTime == null) {
            System.out.print("Enter the time slot end time (HH:mm): ");
            String endTimeInput = scanner.nextLine().trim();
            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                endTime = timeFormatter.format(timeFormatter.parse(endTimeInput));
            } catch (Exception e) {
                System.out.println("❌ Invalid time format. Please use HH:mm.");
            }
        }
        final String EndTime=endTime;

        AppointmentTimeSlot timeSlot= new AppointmentTimeSlot(StartTime, EndTime);

        return timeSlot;
    }
}