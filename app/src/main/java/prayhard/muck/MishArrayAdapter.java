package prayhard.muck;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

public class MishArrayAdapter<T> extends ArrayAdapter {

    private final ContextWrapper activity;
    private final Vector<Mish> mishes;

    public MishArrayAdapter(ContextWrapper activity, Vector<Mish> mishes, List<String> ids){
        super(activity, -1, ids);

        //apply
        this.activity = activity;
        this.mishes = mishes;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //layout
        LayoutInflater inflater;
        View rowView;

        //widget
        TextView title;
        TextView due;
        TextView body;
        TextView created;
        TextView countDown;
        View divider;

        //obj
        Mish display;


        //assign
        inflater = (LayoutInflater) activity.getSystemService(ContextWrapper.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.layout_mish, parent, false);

        title       = rowView.findViewById(R.id.textViewTitle);
        due         = rowView.findViewById(R.id.textViewDue);
        body        = rowView.findViewById(R.id.textViewBody);
        created     = rowView.findViewById(R.id.textViewCreated);
        countDown   = rowView.findViewById(R.id.textViewCountDown);
        divider     = rowView.findViewById(R.id.divider);


        display = mishes.elementAt(position);

        //assign values
        title.setText(display.getTitle());
        due.setText("Due on: " + MuckWrite.date(display.getTimeToCompleteBy()));
        body.setText(display.getBody());
        created.setText("Created: " + MuckWrite.date(display.getTimeCreated()));
        countDown.setText("Due:" + MuckWrite.date(display.getTimeToCompleteBy()));


        return rowView;
    }
}
