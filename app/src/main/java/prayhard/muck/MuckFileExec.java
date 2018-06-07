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

public class MuckFileExec {

    //special characters
    static protected String TAIL    = "&";
    static protected String DATA    = "*";
    static protected String HEAD    = "#";
    static protected String START   = "$";
    static protected String END     = ">";
    static protected String DELIM   = ".";
    static protected String STR     = "\"";

    //file data
    static private String EXEC_PATH = "exec.db";
    static private String FORMAT    = "UTF-8";

    //pre made headers
    private static String HEADER    = DATA + "MUCK_EXEC_FILE" + START + END + TAIL;
    private static String CREATED   = DATA + "CREATED_DATA_BASE" + START;


    private static Mish mishFromString(ContextWrapper Activity, String BuildFrom){

        //obj
        Mish ToBuild;

        //var
        String[] Packets;
        int Count;


        //initialize
        ToBuild = new Mish();


        //parse lines
        Packets = BuildFrom.split("[" + HEAD + END + START + TAIL + "]");
        Count = 0;
        for(String Packet : Packets){

            //soften input
            if(Packet != null && Packet.length() >= 1){

                //determine type and handle it
                switch(Count++){

                    //universalId
                    case 0:

                        ToBuild.setUniversalID(wordFromString(Packet));
                        break;

                    //timeCreated
                    case 1:

                        ToBuild.setTimeCreated(calendarFromString(Packet));
                        break;

                    //timeLastEdited
                    case 2:

                        //no need to update
                        break;

                    //timeToCompleteBy
                    case 3:

                        ToBuild.setTimeToCompleteBy((calendarFromString(Packet)));
                        break;

                    //timeCompletedBy
                    case 4:

                        ToBuild.setTimeCompletedOn(calendarFromString(Packet));
                        break;

                    //createrID
                    case 5:

                        ToBuild.setCreatorID(wordFromString(Packet));
                        break;

                    //title
                    case 6:

                        ToBuild.setTitle(wordFromString(Packet));
                        break;

                    //body
                    case 7:

                        ToBuild.setBody(wordFromString(Packet));
                        break;

                    //hasDate
                    case 8:

                        ToBuild.setHasDate(booleanFromString(Packet));
                        break;

                    //isReminder
                    case 9:

                        ToBuild.setIsReminder(booleanFromString(Packet));
                        break;

                    //isComplete
                    case 10:

                        ToBuild.setIsComplete(booleanFromString(Packet));
                        break;
                }
            }
        }

        //return
        return ToBuild;
    }

    private static Calendar calendarFromString(String BuildFrom){

        //obj
        Calendar ToBuild;

        //var
        String[] Packets;
        int Count;


        //initialize
        ToBuild = Calendar.getInstance();


        //parse lines
        Packets = BuildFrom.split(DELIM);
        Count = 0;
        for(String Packet : Packets){

            //soften input
            if(Packet != null && Packet.length() >= 1){

                //determine type and handle it
                switch(Count++){

                    //year
                    case 0:

                        ToBuild.set(Calendar.YEAR, Integer.parseInt(Packet));
                        break;

                    //month
                    case 1:

                        ToBuild.set(Calendar.MONTH, Integer.parseInt(Packet));
                        break;

                    //day
                    case 2:

                        ToBuild.set(Calendar.DAY_OF_MONTH, Integer.parseInt(Packet));
                        break;

                    //hour
                    case 3:

                        ToBuild.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Packet));
                        break;

                    //minute
                    case 4:
                        ToBuild.set(Calendar.MINUTE, Integer.parseInt(Packet));
                        break;
                }
            }
        }

        //return
        return ToBuild;

    }

    private static boolean booleanFromString(String BuildFrom){

        if(BuildFrom.equalsIgnoreCase("true"))
            return true;
        else
            return false;
    }

    private static String wordFromString(String BuildFrom){

        //remove regrex
        BuildFrom.replaceAll("[\"]", "");

        return BuildFrom;

    }

    private static String writeMish(Mish ToWrite){

        //var
        String Out;


        //assign
        Out = "";
        Out = Out.concat(HEAD + START + ToWrite.getUniversalID() +                          END);
        Out = Out.concat(START +        MuckWrite.calendar(ToWrite.getTimeCreated()) +      END);
        Out = Out.concat(START +        MuckWrite.calendar(ToWrite.getTimeLastEdited()) +   END);
        Out = Out.concat(START +        MuckWrite.calendar(ToWrite.getTimeToCompleteBy()) + END);
        Out = Out.concat(START +        MuckWrite.calendar(ToWrite.getTimeCompletedOn()) +  END);
        Out = Out.concat(START + STR +  ToWrite.getCreatorID() +    STR + END);
        Out = Out.concat(START + STR +  ToWrite.getTitle() +        STR + END);
        Out = Out.concat(START + STR +  ToWrite.getBody() +         STR + END);
        Out = Out.concat(START +        ToWrite.getHasDate() +      STR + END);
        Out = Out.concat(START +        ToWrite.getIsReminder() +   STR + END);
        Out = Out.concat(START +        ToWrite.getIsComplete() +   STR + END);
        Out = Out.concat(TAIL);


        return Out;

    }

    private static void writeToDataBase(ContextWrapper Activity, String ToWrite) {

        //obj
        FileOutputStream Stream;
        File Exec;

        //link stream
        try {

            //build writer
            Stream = Activity.openFileOutput(EXEC_PATH, Context.MODE_APPEND);

            //write and flush buffer
            Stream.write(ToWrite.getBytes());
            Stream.flush();
            Stream.close();
        }
        catch (FileNotFoundException e) {

            MuckError.display(Activity, "Exception", "File Not Found");
        }
        catch (IOException e) {

            MuckError.display(Activity, "Exception", "IO Issue");
        }
    }

    public static void writeToDataBase(ContextWrapper Activity, Mish MishToWrite) {

        writeToDataBase(Activity, writeMish(MishToWrite));
    }

    public static void createDataBase(ContextWrapper Activity) {

        //create the file
        new File(Activity.getFilesDir(), EXEC_PATH);

        //print out header
        writeToDataBase(Activity, HEADER + CREATED + MuckWrite.calendar(Calendar.getInstance()) + END + TAIL);
    }

    public static void delete(ContextWrapper Activity){

        //var
        File Exec = new File(Activity.getFilesDir(), EXEC_PATH);


        //delete data
        try {

            PrintWriter writer = new PrintWriter(Exec);
            writer.print("");
            writer.close();
        }
        catch(FileNotFoundException e){

            MuckError.display(Activity, "Exception", "File Not Found");
        }

    }

    public static String read(ContextWrapper Activity) {

        //obj
        FileInputStream Exec;
        BufferedReader ExecBufferedReader;
        StringBuilder MishBuilder;

        //var
        String Line;


        //initialize
        MishBuilder = new StringBuilder();

        //read file
        try {
            //assign
            Exec = Activity.openFileInput(EXEC_PATH);
            ExecBufferedReader = new BufferedReader(new InputStreamReader(Exec, FORMAT));

            //parse
            while ((Line = ExecBufferedReader.readLine()) != null) {

                    MishBuilder.append(Line + "\n");
            }
        }
        catch (FileNotFoundException e) {

            MuckError.display(Activity, "Exception", "File Not Found");
        }
        catch (IOException e) {

            MuckError.display(Activity, "Exception", "IO Issue");
        }

        return MishBuilder.toString();
    }

    public static Vector<Mish> createVectorMish(ContextWrapper Activity, String BuildFrom){

        //obj
        Vector<Mish> Mishes;
        String[] Packets;

        //var
        char Flag;
        int Count;


        //initialize
        Mishes = new Vector<>();


        //parse lines
        Packets = BuildFrom.split(TAIL);
        Count = 0;
        for(String Packet : Packets){

            //soften input
            if(Packet != null && Packet.length() >= 1){

                //determine type and handle it
                Flag = Packet.charAt(0);
                switch(Flag){

                    default:
                        //handle errors
                        break;

                    case '*':
                        //handle meta data
                        break;

                    case '#':
                        Mish temp = mishFromString(Activity, Packet);
                        Mishes.add(temp);
                        //MuckError.quickDebug(Activity, writeMish(temp));
                        break;
                }
            }


            Count++;
        }


        //return vector
        return Mishes;

    }

}
