package prayhard.muck;

import android.support.annotation.NonNull;

import java.util.Calendar;

public class MuckWrite {

    //public static char

    @NonNull
    public static String day(Calendar dayToWrite) {

        //var
        String month;
        int day;
        int year;


        //assign
        switch (dayToWrite.get(Calendar.MONTH)) {

            default:
                month = "";
                break;

            case 0:
                month = "January";
                break;

            case 1:
                month = "February";
                break;

            case 2:
                month = "March";
                break;

            case 3:
                month = "April";
                break;

            case 4:
                month = "May";
                break;

            case 5:
                month = "June";
                break;

            case 6:
                month = "July";
                break;

            case 7:
                month = "August";
                break;

            case 8:
                month = "September";
                break;

            case 9:
                month = "October";
                break;

            case 10:
                month = "November";
                break;

            case 11:
                month = "December";
                break;
        }

        //get date and year directly
        day = dayToWrite.get(Calendar.DAY_OF_MONTH);
        year = dayToWrite.get(Calendar.YEAR);


        //return
        return (month + " " + day + ", " + year).trim();

    }

    @NonNull
    public static String time(Calendar dayToWrite) {

        //var
        String meridiem;
        String minuteFormat;
        int hour;
        int minute;


        //get date and year directly
        hour = dayToWrite.get(Calendar.HOUR);
        minute = dayToWrite.get(Calendar.MINUTE);

        //calculate AM or Pm
        switch (dayToWrite.get(Calendar.AM_PM)) {

            default:
                meridiem = "";
                break;

            case 0:
                meridiem = "AM";
                break;

            case 1:
                meridiem = "PM";
                break;

        }

        //format for small minute numbers
        if (minute < 10)
            minuteFormat = "0";
        else
            minuteFormat = "";

        //return
        return (hour + ":" + minuteFormat + minute + " " + meridiem).trim();

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

