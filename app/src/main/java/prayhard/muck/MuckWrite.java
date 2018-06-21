package prayhard.muck;

import android.support.annotation.NonNull;

import java.util.Calendar;

public class MuckWrite {

    //public static char

    @NonNull
    public static String day(Calendar DayToWrite) {

        //var
        String Month;
        int Day;
        int Year;


        //assign
        switch (DayToWrite.get(Calendar.MONTH)) {

            default:
                Month = "";
                break;

            case 0:
                Month = "January";
                break;

            case 1:
                Month = "February";
                break;

            case 2:
                Month = "March";
                break;

            case 3:
                Month = "April";
                break;

            case 4:
                Month = "May";
                break;

            case 5:
                Month = "June";
                break;

            case 6:
                Month = "July";
                break;

            case 7:
                Month = "August";
                break;

            case 8:
                Month = "September";
                break;

            case 9:
                Month = "October";
                break;

            case 10:
                Month = "November";
                break;

            case 11:
                Month = "December";
                break;
        }

        //get date and year directly
        Day = DayToWrite.get(Calendar.DAY_OF_MONTH);
        Year = DayToWrite.get(Calendar.YEAR);


        //return
        return (Month + " " + Day + ", " + Year).trim();

    }

    @NonNull
    public static String time(Calendar DayToWrite) {

        //var
        String Meridiem;
        String MinuteFormat;
        int Hour;
        int Minute;


        //get date and year directly
        Hour = DayToWrite.get(Calendar.HOUR);
        Minute = DayToWrite.get(Calendar.MINUTE);

        //calculate AM or Pm
        switch (DayToWrite.get(Calendar.AM_PM)) {

            default:
                Meridiem = "";
                break;

            case 0:
                Meridiem = "AM";
                break;

            case 1:
                Meridiem = "PM";
                break;

        }

        //format for small minute numbers
        if (Minute < 10)
            MinuteFormat = "0";
        else
            MinuteFormat = "";

        //return
        return (Hour + ":" + MinuteFormat + Minute + " " + Meridiem).trim();

    }

    public static String calendar(Calendar ToWrite) {

        //var
        String Out;


        //assign
        Out = "";
        Out = Out.concat(ToWrite.get(Calendar.YEAR) + ".");
        Out = Out.concat(ToWrite.get(Calendar.MONTH) + ".");
        Out = Out.concat(ToWrite.get(Calendar.DAY_OF_MONTH) + ".");
        Out = Out.concat(ToWrite.get(Calendar.HOUR_OF_DAY) + ".");
        Out = Out.concat(ToWrite.get(Calendar.MINUTE) + "");

        return Out;
    }

    public static String date(Calendar toWrite){

        //var
        String out;


        //assign
        out = day(toWrite) + " at " + time(toWrite);

        return out;

    }

    public static String countDown(Calendar toWrite){

        //obj
        Calendar currentTime;

        //var
        String out;
        Long difference;


        //assign
        currentTime = Calendar.getInstance();

        difference = toWrite.getTimeInMillis() - currentTime.getTimeInMillis();


        //complex way to get in


        return "A hot Minute";


    }

}

