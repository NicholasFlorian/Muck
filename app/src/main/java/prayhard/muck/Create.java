package prayhard.muck;

import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

//todo auto update late tags, hard swipe down to reload

public class Create extends AppCompatActivity {

    //class variables

    //This object being "Global" to the class allows for dynamic changing
    private Calendar dateToCompleteBy;      //Store day and time
    private boolean dateStatus;             //Store state of notification
    private boolean notificationStatus;     //Store state of notification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        //widgets
        Switch hasDate;
        Switch setReminder;
        ConstraintLayout layoutDay;
        ConstraintLayout layoutTime;
        Button buttonFin;


        //initialize
        dateToCompleteBy = Calendar.getInstance(); // set to current day and time as default
        dateStatus = false;
        notificationStatus = false;

        //assign
        updateDay(  dateToCompleteBy.get(Calendar.YEAR), dateToCompleteBy.get(Calendar.MONTH), dateToCompleteBy.get(Calendar.DAY_OF_MONTH));
        updateTime( dateToCompleteBy.get(Calendar.HOUR_OF_DAY), dateToCompleteBy.get(Calendar.MINUTE));
        setVisibility(false);

        hasDate         = findViewById(R.id.switchHasDate);
        setReminder     = findViewById(R.id.switchSetReminder);
        layoutDay       = findViewById(R.id.LayoutDay);
        layoutTime      = findViewById(R.id.LayoutTime);
        buttonFin       = findViewById(R.id.buttonFin);


        //listener for done button
        buttonFin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buildAndSaveMish();
            }
        });

        //day listener
        layoutDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDaySelector();
            }
        });

        //time Listener
        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTimeSelector();
            }
        });

        //has date listener
        hasDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setVisibility(isChecked);
                dateStatus = isChecked;
            }
        });

        //is notification listener
        setReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notificationStatus = isChecked;
            }
        });

    }

    private void setVisibility(boolean IsSet){

        //widgets
        Switch setReminder;
        ConstraintLayout layoutDay;
        ConstraintLayout layoutTime;

        //var
        int VisStatus;

        //assign
        setReminder      = findViewById(R.id.switchSetReminder);
        layoutDay       = findViewById(R.id.LayoutDay);
        layoutTime      = findViewById(R.id.LayoutTime);

        if(IsSet)
            VisStatus = View.VISIBLE;
        else
            VisStatus = View.GONE;

        //make visible/invisible
        setReminder.setVisibility(VisStatus);
        layoutDay.setVisibility(VisStatus);
        layoutTime.setVisibility(VisStatus);

    }

    private void updateDay(int Year, int Month, int Day){

        //widgets
        TextView TextViewDay;


        TextViewDay = findViewById(R.id.TextViewDay);

        //set the date
        dateToCompleteBy.set(Calendar.YEAR, Year);
        dateToCompleteBy.set(Calendar.MONTH, Month);
        dateToCompleteBy.set(Calendar.DAY_OF_MONTH, Day);

        TextViewDay.setText(MuckWrite.day(dateToCompleteBy));   //update the text field
    }

    private void updateTime(int Hour, int Minute){

        //widgets
        TextView TextViewTime;
        int Meridiem;


        //initialize
        TextViewTime = findViewById(R.id.TextViewTime);

        //set the time
        dateToCompleteBy.set(Calendar.HOUR_OF_DAY, Hour);
        dateToCompleteBy.set(Calendar.MINUTE, Minute);

        //determine Meridiem
        if(dateToCompleteBy.get(Calendar.HOUR_OF_DAY) > 12)
            Meridiem = 1;
        else
            Meridiem = 0;

        dateToCompleteBy.set(Calendar.AM_PM, Meridiem);

        TextViewTime.setText(MuckWrite.time(dateToCompleteBy));   //update the text field
    }

    private void createDaySelector(){

        //listener for day selector that gets built
        //on setting date, create the day, doesn't change + allows it main listener use
        DatePickerDialog.OnDateSetListener OnDateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker v, int Year, int Month, int Day) {
                updateDay(Year, Month, Day);
            }
        };

        //create the day selector
        new DatePickerDialog(
                this,
                OnDateSet,
                dateToCompleteBy.get(Calendar.YEAR),
                dateToCompleteBy.get(Calendar.MONTH),
                dateToCompleteBy.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void createTimeSelector(){

        //on setting time,
        TimePickerDialog.OnTimeSetListener OnTimeSet = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker v, int Year, int Month) {
                updateTime(Year, Month);
            }
        };

        //create the time selector
        new TimePickerDialog(
                this,
                OnTimeSet,
                dateToCompleteBy.get(Calendar.HOUR_OF_DAY),
                dateToCompleteBy.get(Calendar.MINUTE),
                false).show();
    }

    private void buildAndSaveMish(){

        //obj
        Mish mishToWrite;

        //var
        boolean flag;

        //initialize
        mishToWrite = new Mish();

        //assign
        try {
            flag = true;
            mishToWrite = createMishViaForm();
        }
        catch(FormException f){

            flag = false;
            Toast.makeText(this, f.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


        //write file to database, create notification
        if (flag) {

            MuckFileExec.writeToDataBase(this, mishToWrite);

            //only create notification if it needs to be sent in the future
            if(mishToWrite.getIsReminder())
                MuckNotificationExec.createNotification(this, mishToWrite);

            //end Activity
            finish();
        }


    }

    private Mish createMishViaForm() throws FormException{

        //widget
        EditText editTextTitle;
        EditText editTextBody;

        //obj
        SharedPreferences settings;
        Calendar timeToCompleteBy;

        //var
        boolean hasDate;
        boolean isReminder;
        String creatorID;
        String title;
        String body;


        //assign
        editTextTitle   = findViewById(R.id.editTextTitle);
        editTextBody    = findViewById(R.id.editTextBody);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
                //getSharedPreferences("pref_main.xml", MODE_PRIVATE);

        //create mish

        //use global status instead of direct for GIGO
        hasDate     = dateStatus;
        isReminder  = notificationStatus;

        //control other fields if closed
        if(hasDate){

            timeToCompleteBy = dateToCompleteBy;
        }
        else{

            //no date
            isReminder = false;
            timeToCompleteBy = Mish.getEmptyCalendar();
        }

        //get id from user preference
        creatorID = settings.getString("user_name", "/");

        //assign strings
        title   = editTextTitle.getText().toString();
        body    = editTextBody.getText().toString();


        //verify information
        if(title.equals("") && body.equals(""))
            throw new FormException();
        else if(title.equals("")){

            title = body;
            body = "";
        }

        //return new mish based on data
        return new Mish(timeToCompleteBy, creatorID, title, body, hasDate, isReminder);
    }

}

