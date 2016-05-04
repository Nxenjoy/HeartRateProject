package nl.s5630213023.healthcareproject.HeartRate.exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import nl.s5630213023.healthcareproject.R;


public class ExerciseAdapter extends ArrayAdapter<Exer> {

    public ExerciseAdapter(Context context, ArrayList<Exer> obj){ super(context, 0, obj); }
    public View getView(int position,View convertView,ViewGroup parent){
        Exer Exer = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_exercise_adapter, parent, false);
        }

        TextView txtType = (TextView)convertView.findViewById(R.id.txtType);
        TextView txtTimer = (TextView)convertView.findViewById(R.id.txtTimer);

        txtType.setText(Exer.getType());
        txtTimer.setText(Exer.getTimer() + " minute");
        return convertView;
    }
}
