package nl.s5630213023.healthcareproject.HeartRate.HeartRate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import nl.s5630213023.healthcareproject.R;


public class HeartRateAdapter extends ArrayAdapter<Heart> {

    public HeartRateAdapter(Context context, ArrayList<Heart> obj){ super(context, 0, obj); }

    public View getView(int position,View convertView,ViewGroup parent){
        Heart Heart = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_heart_rate_adapter, parent, false);
        }

        TextView txtHeartRate = (TextView)convertView.findViewById(R.id.txtHHeartRate);
        TextView txtHTime = (TextView)convertView.findViewById(R.id.txtHTime);

        txtHeartRate.setText(Heart.getHeartrate()+" bpm");
        txtHTime.setText(Heart.getTime());
        return convertView;
    }

}
