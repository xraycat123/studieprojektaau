package dk.aau.gr6406.trainez;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dk.aau.gr6406.trainez.listviewitems.ChartItem;
import dk.aau.gr6406.trainez.listviewitems.LineChartItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseStatFragment extends Fragment {

    View rootView;
    ListView lv;
    private static final String TAG = "dk.aau.trainez";

    public ExerciseStatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exercise_stat, container, false);
        addItemsToSpinner(nonZeroDbColumns());
        showGraph();
        Log.i(TAG, "nonzero" + String.valueOf(nonZeroDbColumns()));
        return rootView;
    }


    private void addItemsToSpinner(ArrayList<Integer> itemsToAdd ) {
        TrainingProgramme exerciseProgramme = new TrainingProgramme(getActivity().getApplicationContext());
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("Choose exercises");
        int editCounter = 0;
        for (Exercise m : exerciseProgramme.getTrainingProgramme()) {
            if (itemsToAdd.contains(editCounter)) {
                spinnerList.add(m.getExcName());
                Log.i(TAG, "Item added to spinner: " + m.getExcName());
            }
            editCounter++;
        }
        MultiSelectionSpinner spinner = (MultiSelectionSpinner) rootView.findViewById(R.id.input1);
        spinner.setItems(spinnerList);
        lv = (ListView) rootView.findViewById(R.id.listView1);
        spinner.setInfo(getActivity().getApplicationContext(), (ListView) lv, nonZeroDbColumns());
    }



    private ArrayList<Integer> nonZeroDbColumns(){
        ArrayList<Integer> nonZeroDbColumns = new ArrayList<>();
        // det her er for at checke om der er værdier for de enkelte exercises
        DbHandler dbHandler =  new DbHandler(rootView.getContext(), null, null, 1);
        for (ExerciseMeasurement dbColumn : dbHandler.databaseToStringExerciseArray()) {
            int item = 0;
            for (Integer dbRow : dbColumn.getExercies()) {
                // if there is more than +0 value in a column all the values from that column will be added
                if (dbRow > 0 && !nonZeroDbColumns.contains(item)) {
                    nonZeroDbColumns.add(item);
                    Log.i(TAG, "Non-zero items added :" + item);
                }
                item++;
            }
        }
        return nonZeroDbColumns;
    }



    void showGraph() {
        ArrayList<Integer> one = new ArrayList<>();
        one.add(0);
        StatHelper initStat = new StatHelper(rootView.getContext(),one,(ListView) lv,nonZeroDbColumns());
    }



}
