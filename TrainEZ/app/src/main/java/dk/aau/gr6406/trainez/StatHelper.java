package dk.aau.gr6406.trainez;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ListView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import dk.aau.gr6406.trainez.listviewitems.ChartItem;
import dk.aau.gr6406.trainez.listviewitems.LineChartItem;

/**
 * Created by marti on 5/8/2017.
 */

public class StatHelper {


    private static final String TAG = "dk.aau.trainez";
    private Context fragmentContext;
    private List<Integer> selectedIndices;
    private ArrayList<Integer> nonZeroColumns;
    private ListView lv;
    DbHandler dbHandler;
    long unixtime;


    public StatHelper(Context fragmentContext, List<Integer> selectedIndicies, ListView lv, ArrayList<Integer> non) {
        this.fragmentContext = fragmentContext;
        this.selectedIndices = selectedIndicies;
        this.lv = lv;
        this.nonZeroColumns = non;
        createGraph();
        //lv.invalidate();
    }




    private String exerciseName;


    private void createGraph() {
        dbHandler = new DbHandler(fragmentContext, null, null, 1);

        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        TrainingProgramme tp = new TrainingProgramme(fragmentContext);


            for (Integer m : selectedIndices) {

                exerciseName = tp.getTrainingProgramme().get(nonZeroColumns.get(m)).getExcName();
                Log.i(TAG, "CreateGraph (getName) : " + exerciseName);
                list.add(new LineChartItem(createExerciseDataLine(getXExercisedataDb(),
                        getYExercisedataDb(nonZeroColumns.get(m))), fragmentContext));


            }


        ChartDataAdapter cda = new ChartDataAdapter(fragmentContext, list);
        lv.setAdapter(cda);
    }


    private ArrayList<String> getXExercisedataDb() {
        ArrayList<String> selectedColumns = new ArrayList<>();
        // det her er for at checke om der er værdier for de enkelte exercises
        for (ExerciseMeasurement dbColumn : dbHandler.databaseToStringExerciseArray()) {
            String date = dbColumn.get_date();
            selectedColumns.add(date);
            Log.i(TAG, "get xdataExerciseDb: " + String.valueOf(dbColumn.getExercies()));
        }
        return selectedColumns;
    }

    private ArrayList<Integer> getYExercisedataDb(int column) {
        ArrayList<Integer> selectedColumns = new ArrayList<>();

        for (ExerciseMeasurement dbColumn : dbHandler.databaseToStringExerciseArray()) {

            int repValue = dbColumn.getExercies().get(column);
            selectedColumns.add(repValue);
            Log.i(TAG, "Values: " + dbColumn.getExercies().get(column));
        }
        return selectedColumns;
    }


    private long getUnixTime(String time) {

        DateFormat dfm = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dfm.setTimeZone(TimeZone.getTimeZone("GMT+1"));//Specify your timezone
        try {
            unixtime = dfm.parse(time).getTime();
            unixtime = TimeUnit.MILLISECONDS.toHours(unixtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return unixtime;
    }

    private LineData createExerciseDataLine(ArrayList<String> xData, ArrayList<Integer> yData) {
        Log.i(TAG, String.valueOf(yData));
        ArrayList<Entry> entrySet = new ArrayList<Entry>();
        for (int i = 0; i < xData.size(); i++) {
            Log.i(TAG, "createDataLine "+ "x-dataLine" + getUnixTime(xData.get(i))  + " y-dataline" + String.valueOf(yData.get(i)));
            entrySet.add(new Entry(getUnixTime(xData.get(i)), yData.get(i)));
        }
        return LineSettings(entrySet);
    }





    private LineData LineSettings(ArrayList<Entry> e1) {
        LineDataSet d1 = new LineDataSet(e1, "Exercise " + exerciseName);
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        Random rnd = new Random();
        d1.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

       // ArrayList<Entry> e2 = new ArrayList<Entry>();
        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d1);
        LineData cd = new LineData(sets);
        return cd;
    }


}
