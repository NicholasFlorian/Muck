package prayhard.muck;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Vector;
import java.util.regex.*;

public class MuckFileExec {

    //PARSING SPEC

    //special characters
    static protected final char P_DATA  = '*';
    static protected final char P_HEAD	= '(';
    static protected final char P_TAIL  = ')';
    static protected final char D_HEAD 	= '<';
    static protected final char D_TAIL  = '>';
    static protected final char DELIM	= '.'; //for NUM
    static protected final char STR   	= '\"';
    static protected final char NEW     = '\n';


    //regex string paterns
    static protected final String _VERIFY_FILE 	= "([" + P_DATA + "|" + P_HEAD + "]([" + D_HEAD + "][" + STR + "][^" + STR + "]*?[" + STR + "][" + D_TAIL + "]|[^" + P_DATA + P_TAIL + P_HEAD + "])+[" + P_TAIL + "])+";
    static protected final String _P_SPLIT 		=  "[" + P_DATA + "|" + P_HEAD + "]([" + D_HEAD + "][" + STR + "][^" + STR + "]*?[" + STR + "][" + D_TAIL + "]|[^" + P_HEAD + P_DATA + P_TAIL + "])+[" + P_TAIL + "]";

    static protected final String _D_SPLIT		= "[" + D_HEAD + "]([" + D_HEAD + "][" + STR +"][^" + STR +"]*?[" + STR + "][" + D_TAIL+ "]|[^" + D_TAIL + "])+[" + D_TAIL + "]";

    static protected final String _ID			= "[^" + D_HEAD + D_TAIL + "]+";
    static protected final String _STRING 		= "[" + STR + "][^" + STR + "]*?[" + STR +"]";
    static protected final String _BOOLEAN		= "(true|false)";
    static protected final String _NUM			= "([\\d]+)";


    //file data
    static private String EXEC_PATH = "exec.db";
    static private String FORMAT    = "UTF-8";

    //pre made headers
    private static String HEADER    = P_DATA + "MUCK_EXEC_FILE" + D_HEAD + D_TAIL+ P_TAIL + NEW;
    private static String CREATED   = P_DATA + "CREATED_DATA_BASE" + D_HEAD;


    //regex patterns
    private static final Pattern VERIFY_FILE;
    private static final Pattern PACKET_SPLIT;
    private static final Pattern DATA_SPLIT;
    private static final Pattern ID;
    private static final Pattern STRING;
    private static final Pattern BOOLEAN;
    private static final Pattern NUM;


    static {

        //build patterns at start of program
        VERIFY_FILE 	= Pattern.compile(_VERIFY_FILE);
        PACKET_SPLIT 	= Pattern.compile(_P_SPLIT);
        DATA_SPLIT		= Pattern.compile(_D_SPLIT);

        ID				= Pattern.compile(_ID);
        STRING 			= Pattern.compile(_STRING);
        BOOLEAN			= Pattern.compile(_BOOLEAN);
        NUM				= Pattern.compile(_NUM);

    }

    //MISH BUILDING
    public static Vector<Mish> parseMishes(ContextWrapper activity, String data){

        //obj
        Vector<Mish> mishes;
        Matcher matcher;

        //var
        char Flag;


        //initialize
        mishes = new Vector<>();

        //assign
        matcher = PACKET_SPLIT.matcher(data);


        //parse line
        while(matcher.find()){

            //obj
            String packet = matcher.group();

            //soften input
            if(packet.length() >= 1){

                //determine type and handle it
                Flag = packet.charAt(0);
                switch(Flag){

                    default:
                        //handle errors
                        break;

                    case P_DATA:
                        //handle meta data
                        break;

                    case P_HEAD:
                        mishes.add(parseMish(packet));
                        break;
                }
            }
        }

        //return vector
        return mishes;

    }

    private static Mish parseMish(String data) {

        //obj
        Mish toBuild;
        Matcher matcher;

        //var
        int count;


        //initialize
        toBuild = new Mish();

        //assign
        matcher = DATA_SPLIT.matcher(data);


        //parse line
        count = 0;
        while(matcher.find()){

            //obj
            String packet = matcher.group();

            switch(count++){

                //universalId
                case 0:

                    toBuild.setUniversalID(parseId(packet));
                    break;

                //timeCreated
                case 1:

                    toBuild.setTimeCreated(parseCalendar(packet));
                    break;

                //timeLastEdited
                case 2:

                    //no need to update
                    break;

                //timeToCompleteBy
                case 3:

                    toBuild.setTimeToCompleteBy(parseCalendar(packet));
                    break;

                //timeCompletedBy
                case 4:

                    toBuild.setTimeCompletedOn(parseCalendar(packet));
                    break;

                //createrID
                case 5:

                    toBuild.setCreatorID(parseString(packet));
                    break;

                //title
                case 6:

                    toBuild.setTitle(parseString(packet));
                    break;

                //body
                case 7:

                    toBuild.setBody(parseString(packet));
                    break;

                //hasDate
                case 8:

                    toBuild.setHasDate(parseBoolean(packet));
                    break;

                //isReminder
                case 9:

                    toBuild.setIsReminder(parseBoolean(packet));
                    break;

                //isComplete
                case 10:

                    toBuild.setIsComplete(parseBoolean(packet));
                    break;


            }

        }

        //return vector
        return toBuild;
    }

    private static String parseId(String data) {

        //obj
        Matcher matcher;


        //assign
        matcher = ID.matcher(data);

        if(matcher.find())
            return matcher.group();
        else
            return "";
    }

    private static String parseString(String data) {

        //obj
        Matcher matcher;


        //assign
        matcher = STRING.matcher(data);

        if(matcher.find())
            return matcher.group().replaceAll(STR + "", "");
        else
            return "";

    }

    private static boolean parseBoolean(String data) {

        //obj
        Matcher matcher;
        String temp;

        //assign
        matcher = BOOLEAN.matcher(data);

        if(matcher.find()) {

            temp = matcher.group();

            switch(temp) {

                default:

                case "false":
                    return false;

                case "true":
                    return true;
            }
        }
        else
            return true;

    }

    private static Calendar parseCalendar(String data){

        //obj
        Calendar toBuild;
        Matcher matcher;

        //var
        int count;
        int parse;
        boolean flag;

        //assign
        matcher = NUM.matcher(data);

        toBuild = Mish.getEmptyCalendar();

        //parse line
        count = 0;
        while(matcher.find()){

            //obj
            String packet = matcher.group();

            //soften input
            try{

                parse = Integer.parseInt(packet);
                flag = true;
            }
            catch(NumberFormatException e){

                flag = false;
                parse = 0;
            }

            //check data
            if(packet != null && packet.length() >= 1 && flag){

                //determine type and handle it
                switch(count++){

                    //year
                    case 0:

                        toBuild.set(Calendar.YEAR, parse);
                        break;

                    //month
                    case 1:
                        toBuild.set(Calendar.MONTH, parse);
                        break;

                    //day
                    case 2:

                        toBuild.set(Calendar.DAY_OF_MONTH, parse);
                        break;

                    //hour
                    case 3:

                        toBuild.set(Calendar.HOUR_OF_DAY, parse);
                        break;

                    //minute
                    case 4:
                        toBuild.set(Calendar.MINUTE, parse);
                        break;
                }
            }
        }

        //return
        return toBuild;

    }

    private static String writeMish(Mish toWrite){

        //var
        String Out;


        //assign

        //Java is having a bitch fit where it adds the chars as ints first see ""
        Out = "";
        Out = Out.concat(P_HEAD + "" + D_HEAD + toWrite.getUniversalID() +                             D_TAIL);
        Out = Out.concat(D_HEAD +               MuckWrite.calendar(toWrite.getTimeCreated()) +         D_TAIL);
        Out = Out.concat(D_HEAD +               MuckWrite.calendar(toWrite.getTimeLastEdited()) +      D_TAIL);
        Out = Out.concat(D_HEAD +               MuckWrite.calendar(toWrite.getTimeToCompleteBy()) +    D_TAIL);
        Out = Out.concat(D_HEAD +               MuckWrite.calendar(toWrite.getTimeCompletedOn()) +     D_TAIL);
        Out = Out.concat(D_HEAD + "" + STR +    toWrite.getCreatorID() +                               STR + D_TAIL);
        Out = Out.concat(D_HEAD + "" + STR +    toWrite.getTitle() +                                   STR + D_TAIL);
        Out = Out.concat(D_HEAD + "" + STR +    toWrite.getBody() +                                    STR + D_TAIL);
        Out = Out.concat("" + D_HEAD +          toWrite.getHasDate() +                                 D_TAIL); //"" solves java's little bitch fit
        Out = Out.concat("" + D_HEAD +          toWrite.getIsReminder() +                              D_TAIL);
        Out = Out.concat("" + D_HEAD +          toWrite.getIsComplete() +                              D_TAIL);
        Out = Out.concat(P_TAIL + "\n");

        return Out;
    }


    //DATABASE FUNCTIONS

    private static void writeToDataBase(ContextWrapper activity, String toWrite) {

        //obj
        FileOutputStream stream;
        File exec;

        //link stream
        try {

            //build writer
            stream = activity.openFileOutput(EXEC_PATH, Context.MODE_APPEND);

            //write and flush buffer
            stream.write(toWrite.getBytes());
            stream.flush();
            stream.close();
        }
        catch (FileNotFoundException e) {

            MuckError.display(activity, "Exception", "File Not Found");
        }
        catch (IOException e) {

            MuckError.display(activity, "Exception", "IO Issue");
        }
    }

    public static void writeToDataBase(ContextWrapper activity, Mish mishToWrite) {

        writeToDataBase(activity, writeMish(mishToWrite));
    }

    public static void createDataBase(ContextWrapper activity) {

        //create the file
        new File(activity.getFilesDir(), EXEC_PATH);

        //print out header
        writeToDataBase(activity, HEADER + CREATED + MuckWrite.calendar(Calendar.getInstance()) + D_TAIL + P_TAIL + "\n");
    }

    public static void delete(ContextWrapper activity){

        //var
        File exec = new File(activity.getFilesDir(), EXEC_PATH);


        //delete data
        try {

            PrintWriter writer = new PrintWriter(exec);
            writer.print("");
            writer.close();
        }
        catch(FileNotFoundException e){

            MuckError.display(activity, "Exception", "File Not Found");
        }

    }

    public static String read(ContextWrapper activity) {

        //obj
        FileInputStream exec;
        BufferedReader execBufferedReader;
        StringBuilder MishBuilder;

        //var
        String Line;


        //initialize
        MishBuilder = new StringBuilder();

        //read file
        try {
            //assign
            exec = activity.openFileInput(EXEC_PATH);
            execBufferedReader = new BufferedReader(new InputStreamReader(exec, FORMAT));

            //parse
            while ((Line = execBufferedReader.readLine()) != null) {

                    MishBuilder.append(Line + "\n");
            }
        }
        catch (FileNotFoundException e) {

            MuckError.display(activity, "Exception", "File Not Found");
        }
        catch (IOException e) {

            MuckError.display(activity, "Exception", "IO Issue");
        }

        return MishBuilder.toString();
    }

}
