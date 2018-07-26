package prayhard.muck;

import android.content.ContextWrapper;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class MishArrayAdapter<T> extends ArrayAdapter {

    //A little sloppy, but KISS

    private final Vector<Mish> mishes;
    private final ContextWrapper activity;

    public MishArrayAdapter(ContextWrapper activity, Vector<Mish> mishes, List<String> ids){
        super(activity, -1, ids);

        //apply
        this.activity = activity;
        this.mishes = mishes;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //todo add bel logo

        //layout
        LayoutInflater inflater;
        View rowView;
        ConstraintLayout constraintLayout;

        //widget
        TextView title;
        TextView due;
        TextView body;
        TextView created;
        View divider;

        //obj
        Mish display;


        //assign
        inflater = (LayoutInflater) activity.getSystemService(ContextWrapper.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.layout_mish, parent, false);
        constraintLayout = rowView.findViewById(R.id.main);

        title       = rowView.findViewById(R.id.textViewTitle);
        due         = rowView.findViewById(R.id.textViewDue);
        body        = rowView.findViewById(R.id.textViewBody);
        created     = rowView.findViewById(R.id.textViewCreated);
        divider     = rowView.findViewById(R.id.divider);

        display = mishes.elementAt(position);

        //assign values

        //determine visibility
        if (display.getTitle().equals("")) {

            title.setVisibility(View.INVISIBLE);
            title.setHeight(0);
        } else {

            title.setText(display.getTitle());
        }


        //determine visibility
        if (!display.getHasDate()) {

            due.setVisibility(View.INVISIBLE);
            due.setHeight(0);

        }
        else {

            due.setText(MuckWrite.countDown(display.getTimeToCompleteBy())+ " (" + MuckWrite.date(display.getTimeToCompleteBy()) + ")");
        }


        //determine visibility
        if (display.getBody().equals("")) {

            body.setVisibility(View.INVISIBLE);
            body.setHeight(0);

            divider.setVisibility(View.INVISIBLE);
        }
        else {

            body.setText(display.getBody());
        }



        //background
        if (display.getIsComplete()) {

            constraintLayout.setBackground(ContextCompat.getDrawable(activity, R.drawable.muck_complete));
        }
        else if (display.getTimeToCompleteBy().compareTo(Calendar.getInstance()) < 0
                && display.getHasDate()) {

            constraintLayout.setBackground(ContextCompat.getDrawable(activity, R.drawable.muck_late));
        }

        //always available
        created.setText("Created " + MuckWrite.date(display.getTimeCreated()));

        //listeners
        constraintLayout.setOnClickListener(new DoubleClickListener(){

            @Override
            public void onDoubleClick() {

                //constraintLayout.setBackground(ContextCompat.getDrawable(activity, R.drawable.muck_late));
                MuckError.quickDebug(activity, "FUCK");

            }

        });

        return rowView;
    }



    //todo better colour imp with more detail
   /* void setBackground(char c){

        if(c == "r")
            setBackgroundRed();
        if(c == "b")
            setBackgroundBlue();
        if(c == )

    }

    private void setBackgroundRed(){


    }*/

}
