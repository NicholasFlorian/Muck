package prayhard.muck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Editor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //fin button
        final Button button = findViewById(R.id.buttonFin);

        //code for fin button onclick
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (VerifyForm())
                    ;


            }
        });
    }

    private boolean VerifyForm(){

        //widgets
        final EditText EditTextTitle = findViewById(R.id.editTextTitle);

        if (EditTextTitle.getText().equals(""))
            return false;
        else
            return true;
    }

    private Mish CreateGoalFromForm(){

        //widgets
        final EditText EditTextTitle = findViewById(R.id.editTextTitle);
        final EditText EditTextBody = findViewById(R.id.editTextBody);

        //handling these as booleans alone as the data is just so simple!
        final Boolean IsReminder = findViewById(R.id.switchSetReminder).isActivated();

        //obj
        Mish mGoal = new Mish();





        return new Mish();

    }

}
