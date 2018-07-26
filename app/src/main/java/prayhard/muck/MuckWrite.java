package prayhard.muck;

import android.support.annotation.NonNull;

import java.util.Calendar;

public class MuckWrite {

    static final long few    = 25000L;
    static final long minute = 60000L;
    static final long hour   = 3600000L;
    static final long day    = 86400000L;
    static final long month  = 2592000000L;
    static final long year   = 31557600000L;


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

    public static String twoDig(int toFix){

        String out;

        out = "" + toFix;

        if(out.length() == 1)
            return 0 + out;
        else
            return out;
    }

    public static String countDown(Calendar toWrite) {

        //obj
        Calendar currentTime;

        //var
        String out;
        String prefix;
        String suffix;

        Long difference;


        //assign
        currentTime = Calendar.getInstance();

        //determine past/present
        difference = toWrite.getTimeInMillis() - currentTime.getTimeInMillis();
        if (difference < 0) {

            difference = difference * -1;
            prefix = "Was due ";
            suffix = " ago.";
        } else {

            prefix = "Due in ";
            suffix = ".";
        }


        //find the amount of time
        if(difference < few)
            out = "in a few seconds";
        else if(difference < minute)
            out = "a minute";
        else if(difference < minute * 5)
            out = "5 minutes";
        else if(difference < minute * 10)
            out = "10 minutes";
        else if(difference < minute * 30)
            out = "half an hour";
        else if(difference < hour)
            out = "an hour";
        else if(difference < hour * 5)
            out = "5 hours";
        else if(difference < day)
            out = "a week";
        else if(difference < month)
            out = "a month";
        else if(difference < month * 11)
            out = (int)Math.ceil(difference/(double)month) + " months";
        else if(difference < year)
            out = "a year";
        else if(difference < 10 * year)
            out = (int)Math.ceil(difference/(double)year) + " years";
        else
            out = "a long time";


        //return count down
        return prefix + out + suffix;
    }

}

