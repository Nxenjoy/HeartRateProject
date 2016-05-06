package nl.s5630213023.healthcareproject.HeartRate.exercise;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import nl.s5630213023.healthcareproject.R;

public class Exercise2 extends Fragment {

    ListView listView;
    static ArrayList<Exer> arrayExercise = new ArrayList<Exer>();
    ExerciseAdapter adapter;

    //chart
    private BarChart mChart;
    ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
    ArrayList<String> xVals = new ArrayList<String>();


    public static Exercise2 newInstance() {
        return new Exercise2();
    }

    public Exercise2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exercise_fragment2, container, false);

        listView = (ListView) v.findViewById(R.id.listViewExercise);
        adapter = new ExerciseAdapter(this.getContext(), arrayExercise);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        btnShow();

        //chart
        mChart = (BarChart) v.findViewById(R.id.chartEx);

        mChart.setDescription("");

        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setDrawGridLines(false);

        mChart.animateY(2500);

        mChart.getLegend().setEnabled(false);

        setData(20, 30);

        return v;
    }

    private void setData(int count, float range) {

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Data Set");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);

            mChart.setData(data);
            mChart.invalidate();
        }
    }

    private void btnShow() {
        Uri u = Uri.parse("content://ExerCiseDB");
        Cursor c = getActivity().getContentResolver().query(u, null, null, null, null);
        while (c.moveToNext()) {
            arrayExercise.add(new Exer(c.getInt(0), c.getString(1).toString(), c.getString(2).toString(), c.getInt(3), c.getString(4).toString()));
            xVals.add(c.getString(2));
            yVals1.add(new BarEntry(c.getInt(3),c.getInt(0)));
        }
    }
}
