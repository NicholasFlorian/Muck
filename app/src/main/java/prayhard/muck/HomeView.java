package prayhard.muck;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class HomeView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home_view);

        //widget
        FloatingActionButton Fab;


        //assign
        Fab = findViewById(R.id.fab);


        //start of activity functions
        setList();


        //Fab listener
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runCreate();

            }
        });
    }

    //resume update
    @Override
    public void onResume(){
        super.onResume();

        setList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_view, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings){

            startActivity(new Intent(this, Settings.class));
        }
        else if(id == R.id.action_delete){

            //todo more verbose deletion settings
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            deleteAll();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            //build the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Delete all Mishes, are you sure?");
            builder.setPositiveButton("Yes", dialogClickListener);
            builder.setNegativeButton("No", dialogClickListener).show();

        }
        else if(id == R.id.action_debug){

            startActivity(new Intent(this, debugView.class));
        }

        return super.onOptionsItemSelected(item);
    }


    public void runCreate(){

        startActivity(new Intent(this, Create.class));
    }


    public void setList(){

        //widget
        ListView ListViewMishes;

        //obj
        MishArrayAdapter<String> MishArray;
        Vector<Mish> Mishes;
        List<String> ids;


        //initialize
        ids = new Vector<>();


        //assign
        Mishes = MuckFileExec.parseMishes(
               this, MuckFileExec.read(this));

        for(int i = 0; i < Mishes.size(); i++)
            ids.add(Mishes.get(i).getUniversalID());

        //assign to layouts
        MishArray = new MishArrayAdapter<>(
                this,
                Mishes,
                ids);

        //assign to app
        ListViewMishes = findViewById(R.id.listViewMishes);
        ListViewMishes.setAdapter(MishArray);

        MishArray.notifyDataSetChanged();
    }

    private void deleteAll(){

        MuckFileExec.delete(this);
        MuckFileExec.createDataBase(this);
        this.setList();
    }

}
