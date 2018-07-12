package prayhard.muck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class debugView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_view);

        setText();

    }

    //resume update
    @Override
    public void onResume(){
        super.onResume();

        setText();
    }

    private void setText(){

        //widgets
        TextView text;


        //assign
        text = findViewById(R.id.textView);
        text.setText(MuckFileExec.read(this));
    }
}
