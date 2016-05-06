package nl.s5630213023.healthcareproject.HeartRate.bloodpressure;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import nl.s5630213023.healthcareproject.R;

import static com.google.android.gms.internal.zzir.runOnUiThread;

public class BloodPressure2 extends Fragment implements View.OnClickListener {
    private LineChart mChart;
    private FrameLayout frame;

    ListView listView;
    static ArrayList<BloodPressure> arrayPressure = new ArrayList<BloodPressure>();
    BloodPressureAdapter adapter;

    //chart

    public static BloodPressure2 newInstance() {
        return new BloodPressure2();
    }


    public BloodPressure2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bloodpressure_fragment2, container, false);

        listView = (ListView) v.findViewById(R.id.listViewPressure);
        adapter = new BloodPressureAdapter(this.getContext(), arrayPressure);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        btnShow();
        //chart

        frame =(FrameLayout) v.findViewById(R.id.chart);
        mChart = new LineChart(getActivity());
        frame.addView(mChart);
        mChart.setDescription("");
        mChart.setNoDataTextDescription("No data for the moment");

        mChart.setHighlightPerDragEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setTouchEnabled(true);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        mChart.setPinchZoom(true);

        mChart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        Legend l = mChart.getLegend();

        l.setForm(Legend.LegendForm.CIRCLE.LINE);
        l.setTextColor(Color.BLACK);

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);

        YAxis yl = mChart.getAxisLeft();
        yl.setTextColor(Color.WHITE);
        yl.setAxisMaxValue(120f);
        yl.setDrawGridLines(true);

        YAxis yl2 = mChart.getAxisRight();
        yl2.setEnabled(false);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i<100;i++){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();
    }

    private void addEntry(){
        LineData data = mChart.getData();

        if(data != null){
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
                if(set == null){

                    set = createSet();
                    data.addDataSet(set);
                }
            data.addXValue("");
            data.addEntry(
                    new Entry((float) (Math.random() * 75)+60f,set
                            .getEntryCount()),0);

            mChart.notifyDataSetChanged();

            mChart.setVisibleXRange(6,6);

            mChart.moveViewToX(data.getXValCount() - 7);
        }

    }

    private LineDataSet createSet(){

        LineDataSet set = new LineDataSet(null,"SPL Db");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 177, 177));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        return set;

    }
    @Override
    public void onClick(View v) {

    }

    private void btnShow() {
        Uri u = Uri.parse("content://PressureDB");
        Cursor c = getActivity().getContentResolver().query(u, null, null, null, null);
        while (c.moveToNext()) {
            arrayPressure.add(new BloodPressure(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3).toString(), c.getString(4).toString()));

        }

    }

}
