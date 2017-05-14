package dk.aau.gr6406.trainez;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightStatFragment extends Fragment {

    private static final String TAG = "dk.aau.trainez";
    Typeface mTfLight;
    private LineChart mChart;
    private TextView tvX;
    View rootView;
    long unixtime;

    public WeightStatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_weight_stat, container, false);
        chartSettings();
        showGraph();
        return rootView;
    }

        private void showGraph(){
    //    StatHelper newStat = new StatHelper(rootView.getContext());
        DbHandler myDbHandler = new DbHandler(rootView.getContext(), null, null, 1);
        ArrayList<WeightMeasurement> measurements = myDbHandler.databaseToStringWeightDate();
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (WeightMeasurement m : measurements) {
            Log.i(TAG, "date: " + m.get_date());
            Log.i(TAG, "weight: " + m.get_kilo());
            values.add(new Entry(getUnixTime(m.get_date()), m.get_kilo())); // add one entry per hour
        }
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "My weight data");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(true);
            set1.setCircleRadius(4.5f);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        // create a data object with the datasets
        LineData data = new LineData(set1);
        // create a dataset and give it a type
        mChart = (LineChart) rootView.findViewById(R.id.chart1);
        mChart.setData(data);

    }

    private long getUnixTime(String time) {
        DateFormat dfm = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            unixtime = dfm.parse(time).getTime();
            unixtime = TimeUnit.MILLISECONDS.toHours(unixtime);
            //long unixTime2 = TimeUnit.MILLISECONDS.toHours(unixTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixtime;
    }

    private void chartSettings() {
        mChart = (LineChart) rootView.findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawBorders(true);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);
        mChart.invalidate();
        Legend l = mChart.getLegend();
        l.setEnabled(true);
        // create a custom MarkerView (extend MarkerView) and specify the layout
        MyMarkerView mv = new MyMarkerView(rootView.getContext(), R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(0.1f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM");
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setTextSize(11);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(30f);
        leftAxis.setAxisMaximum(120f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    }





