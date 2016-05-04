package nl.s5630213023.healthcareproject.HeartRate.bloodpressure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import nl.s5630213023.healthcareproject.R;


public class BloodPressureAdapter extends ArrayAdapter<BloodPressure>{

    public BloodPressureAdapter(Context context, ArrayList<BloodPressure> obj){ super(context, 0, obj); }
    public View getView(int position,View convertView,ViewGroup parent){
        BloodPressure BloodPressure = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_blood_pressure_adapter, parent, false);
        }

        TextView txtPressure = (TextView)convertView.findViewById(R.id.txtpressure);
        TextView txtTime = (TextView)convertView.findViewById(R.id.txtTime);

        txtPressure.setText(BloodPressure.getSystolic()+"/"+BloodPressure.getDiastolic()+"mmHg");
        txtTime.setText(BloodPressure.getTime());
        return convertView;
    }




}
