package prayhard.muck;

import android.content.ContextWrapper;
import android.util.Log;
import android.widget.Toast;

public class MuckError {

    protected static void quickDebug(ContextWrapper Activity, String Message){

        //prompt for debugging
        Toast.makeText(Activity, "DD:\n" + Message, Toast.LENGTH_LONG).show();
        System.out.println("DD: " + Message);
    }

    protected static void display(ContextWrapper Activity, String Tag, String Message){

        //log the error
        Log.e(Tag, Message);

        //prompt for debugging
        Toast.makeText(Activity, Tag + "-" + Message, Toast.LENGTH_LONG).show();
        System.out.println(Tag + "-" + Message);
    }

}
