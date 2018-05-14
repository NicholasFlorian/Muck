package prayhard.muck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class Create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //widgets
        final Button button = findViewById(R.id.buttonFin);



        //code for fin button onclick
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //todo proper implementation of new decice
                //obj
                Mish mMish;

                mMish = CreateMishViaForm();

                Toast.makeText(getApplicationContext(), "NEW MISH\n" + mMish.toString(),
                        Toast.LENGTH_LONG).show();






            }
        });
    }

    private Mish CreateMishViaForm(){

        //widget
        final EditText EditTextTitle    = findViewById(R.id.editTextTitle);
        final EditText EditTextBody     = findViewById(R.id.editTextBody);

        //handling this as a boolean
        boolean HasDate     = findViewById(R.id.switchHasDate).isActivated();
        Boolean IsReminder  = findViewById(R.id.switchSetReminder).isActivated();


        //obj
        Mish mGoal = new Mish();

        //var
        Date TimeToCompleteBy;
        String CreatorID;
        String Title;
        String Body;


        //assign
        if(HasDate){

            //TODO for dynamic pop up ->on Create
            IsReminder = false;
            TimeToCompleteBy = new Date(0);
        }
        else{

            //no date
            IsReminder = false;
            TimeToCompleteBy = new Date(0);
        }

        CreatorID = new String("Nick");

        Title   = EditTextTitle.getText().toString();
        Body    = EditTextBody.getText().toString();


        //return new mish based on data
        mGoal = new Mish(TimeToCompleteBy, CreatorID, Title, Body, HasDate, IsReminder);

        return mGoal;
    }


    private void WriteMishToStorage(Mish MishToWrite){

        File file = new File(getFilesDir(), "Mishes.db");

        try{

            FileOutputStream mStream = new FileOutputStream(file);


        }
        catch(FileNotFoundException e){

            return;

        }




    }

}
