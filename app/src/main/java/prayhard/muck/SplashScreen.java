package prayhard.muck;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //create the setup for an initial file
        //todo hard coded preference, fine 4 now
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(this, SplashScreen.class));
            Toast.makeText(this, "First Run", Toast.LENGTH_LONG)
                    .show();

            MuckFileExec.createDataBase(this);

        } else {

            Toast.makeText(this, "Not First Run", Toast.LENGTH_LONG)
                    .show();

        }


        //set as as false
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();

        //delay and run homeview
        splosh(1.5);

    }

    void firstTimeSetUp() {

    }

    void splosh(double seconds) {

        //var
        int time;


        time = (int) (1000 * seconds);

        //timer
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                //set the new Content of your activity
                startHomeView();
            }
        }.start();

    }

    void startHomeView() {

        //move on to main view
        startActivity(new Intent(this, HomeView.class));
    }

}




