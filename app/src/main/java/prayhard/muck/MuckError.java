package prayhard.muck;

import android.content.ContextWrapper;
import android.util.Log;
import android.widget.Toast;

public class MuckError {

    protected static void quickDebug(ContextWrapper activity, String message){

        //prompt for debugging
        Toast.makeText(activity, "DD:\n" + message, Toast.LENGTH_LONG).show();
        System.out.println("DD: " + message);
    }

    protected static void display(ContextWrapper activity, String tag, String message){

        //log the error
        Log.e(tag, message);

        //prompt for debugging
        Toast.makeText(activity, tag + "-" + message, Toast.LENGTH_LONG).show();
        System.out.println(tag + "-" + message);
    }

}
