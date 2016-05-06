package nl.s5630213023.healthcareproject.HeartRate.HeartRate;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import nl.s5630213023.healthcareproject.R;
public class HeartRate2 extends Fragment implements View.OnClickListener ,OnChartGestureListener, OnChartValueSelectedListener {

    ListView listViews;
    static ArrayList<Heart> arrayHeartRate = new ArrayList<Heart>();
    HeartRateAdapter adapterHR;
    static int lowHeartRate,highHeartRate;
    //Chart
    private LineChart mChart;
    static int val;
    ArrayList<String> xVals = new ArrayList<String>();
    ArrayList<Entry> yVals = new ArrayList<Entry>();
    public static HeartRate2 newInstance() {
        return new HeartRate2();
    }

    public HeartRate2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.heartrate_fragment2, container, false);

        listViews = (ListView) v.findViewById(R.id.listViewHeartRate);
        adapterHR = new HeartRateAdapter(this.getContext(),arrayHeartRate);
        listViews.setAdapter(adapterHR);
        registerForContextMenu(listViews);
        showUser();
        btnShow();

        //Chart
        mChart =(LineChart) v.findViewById(R.id.chartHR);
        mChart.setOnClickListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setPinchZoom(true);

        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();


        LimitLine ll1 = new LimitLine(highHeartRate, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);


        LimitLine ll2 = new LimitLine(lowHeartRate, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaxValue(230f);
        leftAxis.setAxisMinValue(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);


        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        setData(45, 100);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend l = mChart.getLegend();

        l.setForm(Legend.LegendForm.LINE);

        return v;
    }

    @Override
    public void onClick(View v) {

    }

    private void btnShow() {
        Uri u = Uri.parse("content://HeartRateDB");
        Cursor c = getActivity().getContentResolver().query(u, null, null, null, null);
        while (c.moveToNext()) {
           arrayHeartRate.add(new Heart(c.getInt(0), c.getInt(1), c.getString(2).toString(), c.getString(3).toString(), c.getString(4).toString() ,c.getString(5).toString() ,c.getString(6).toString()));

            xVals.add((c.getString(3)));
            yVals.add(new Entry(c.getInt(1),c.getInt(0)));
        }
    }


    private void setData(int count, float range) {


        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
           /* set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
           set1.setYVals(yVals);
           mChart.getData().setXVals(xVals);

            mChart.notifyDataSetChanged();*/
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "Heart Rate");

            // set1.setFillAlpha(110);
            // set1.setFillColor(Color.RED);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

            // set data
            mChart.setData(data);
        }
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture chartGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {
        Log.i("Gesture", "END, lastGesture: " + chartGesture);
        if(chartGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)

    }

    @Override
    public void onChartLongPressed(MotionEvent motionEvent) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent motionEvent) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent motionEvent) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent motionEvent,float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent motionEvent, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        Log.i("Entry selected", entry.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


    private void showUser() {
        Uri u = Uri.parse("content://UserDBs");
        String projs[] = {"lowHeartRate", "highHeartRate"};
        String selection = "user_id = ?";
        String selectArg[] = {"1"};
        Cursor c = getActivity().getContentResolver().query(u, projs, selection, selectArg, null);
        if (c.getCount() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
        } else {
            c.moveToNext();

            lowHeartRate = c.getInt(0);
            highHeartRate = c.getInt(1);

        }
    }
}
