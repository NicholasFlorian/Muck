package prayhard.muck;

import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
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

import java.util.Calendar;

public class Create extends AppCompatActivity {

    //class variables

    //This object being "Global" to the class allows for dynamic changing
    private Calendar DateToCompleteBy;      //Store day and time
    private boolean DateStatus;             //Store state of notification
    private boolean NotificationStatus;     //Store state of notification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //widgets
        Switch HasDate;
        Switch SetReminder;
        ConstraintLayout LayoutDay;
        ConstraintLayout LayoutTime;
        Button ButtonFin;


        //initialize
        DateToCompleteBy = Calendar.getInstance(); // set to current day and time as default
        DateStatus = false;
        NotificationStatus = false;


        //assign
        updateDay(  DateToCompleteBy.get(Calendar.YEAR), DateToCompleteBy.get(Calendar.MONTH), DateToCompleteBy.get(Calendar.DAY_OF_MONTH));
        updateTime( DateToCompleteBy.get(Calendar.HOUR_OF_DAY), DateToCompleteBy.get(Calendar.MINUTE));
        setVisibility(false);

        HasDate         = findViewById(R.id.switchHasDate);
        SetReminder     = findViewById(R.id.switchSetReminder);
        LayoutDay       = findViewById(R.id.LayoutDay);
        LayoutTime      = findViewById(R.id.LayoutTime);
        ButtonFin       = findViewById(R.id.buttonFin);


        //listener for done button
        ButtonFin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buildAndSaveMish();
            }
        });

        //day listener
        LayoutDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDaySelector();
            }
        });

        //time Listener
        LayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTimeSelector();
            }
        });

        //has date listener
        HasDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setVisibility(isChecked);
                DateStatus = isChecked;
            }
        });

        //is notification listener
        SetReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NotificationStatus = isChecked;
            }
        });

    }

    private void setVisibility(boolean IsSet){

        //widgets
        Switch SetReminder;
        ConstraintLayout LayoutDay;
        ConstraintLayout LayoutTime;

        //var
        int VisStatus;

        //assign
        SetReminder      = findViewById(R.id.switchSetReminder);
        LayoutDay       = findViewById(R.id.LayoutDay);
        LayoutTime      = findViewById(R.id.LayoutTime);

        if(IsSet)
            VisStatus = View.VISIBLE;
        else
            VisStatus = View.GONE;

        //make visible/invisible
        SetReminder.setVisibility(VisStatus);
        LayoutDay.setVisibility(VisStatus);
        LayoutTime.setVisibility(VisStatus);

    }

    private void updateDay(int Year, int Month, int Day){

        //widgets
        TextView TextViewDay;


        TextViewDay = findViewById(R.id.TextViewDay);

        //set the date
        DateToCompleteBy.set(Calendar.YEAR, Year);
        DateToCompleteBy.set(Calendar.MONTH, Month);
        DateToCompleteBy.set(Calendar.DAY_OF_MONTH, Day);

        TextViewDay.setText(MuckWrite.day(DateToCompleteBy));   //update the text field
    }

    private void updateTime(int Hour, int Minute){

        //widgets
        TextView TextViewTime;
        int Meridiem;


        //initialize
        TextViewTime = findViewById(R.id.TextViewTime);

        //set the time
        DateToCompleteBy.set(Calendar.HOUR_OF_DAY, Hour);
        DateToCompleteBy.set(Calendar.MINUTE, Minute);

        //determine Meridiem
        if(DateToCompleteBy.get(Calendar.HOUR_OF_DAY) > 12)
            Meridiem = 1;
        else
            Meridiem = 0;

        DateToCompleteBy.set(Calendar.AM_PM, Meridiem);

        TextViewTime.setText(MuckWrite.time(DateToCompleteBy));   //update the text field


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
                DateToCompleteBy.get(Calendar.YEAR),
                DateToCompleteBy.get(Calendar.MONTH),
                DateToCompleteBy.get(Calendar.DAY_OF_MONTH)).show();
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
                DateToCompleteBy.get(Calendar.HOUR_OF_DAY),
                DateToCompleteBy.get(Calendar.MINUTE),
                false).show();
    }

    private void buildAndSaveMish(){

        //obj
        Mish MishToWrite;


        //assign
        MishToWrite = createMishViaForm();

        //write file to database
        MuckFileExec.writeToDataBase(this, MishToWrite);

        //end Activity
        finish();
    }

    private Mish createMishViaForm(){

        //widget
        EditText EditTextTitle;
        EditText EditTextBody;

        //obj
        Mish MishToBuild = new Mish();
        Calendar TimeToCompleteBy;

        //var
        boolean HasDate;
        boolean IsReminder;
        String CreatorID;
        String Title;
        String Body;


        //assign
        EditTextTitle   = findViewById(R.id.editTextTitle);
        EditTextBody    = findViewById(R.id.editTextBody);


        //create mish

        //use global status instead of direct for GIGO
        HasDate     = DateStatus;
        IsReminder  = NotificationStatus;

        //control other fields if closed
        if(HasDate){

            TimeToCompleteBy = DateToCompleteBy;
        }
        else{

            //no date
            IsReminder = false;
            TimeToCompleteBy = Mish.getEmptyCalendar();
        }

        //Todo this in preferences later
        CreatorID = "Nick";

        //assign strings
        Title   = EditTextTitle.getText().toString();
        Body    = EditTextBody.getText().toString();


        //return new mish based on data
        MishToBuild  = new Mish(TimeToCompleteBy, CreatorID, Title, Body, HasDate, IsReminder);

        return MishToBuild;
    }

}

