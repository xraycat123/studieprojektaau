package dk.aau.gr6406.trainez;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

public class ComposeProgrammeActivity extends AppCompatActivity {

    private static final String TAG = "dk.aau.trainez";
    ExpandableListView expListView;
    ExpAdapter adapter;
    String[] categories;
    Exercise groupedExercises[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_programme);
        Log.i(TAG, "Starting the adapter - In onCreate");
        startAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"in onResume");
    }

    private void startAdapter() {
        TrainingProgramme trainingProgramme = new TrainingProgramme(this);

        categories = trainingProgramme.getCategoriesArray();
        groupedExercises = trainingProgramme.getGroupedExercises();

        adapter = new ExpAdapter(this, categories, groupedExercises);
        //Linking expandable list view to this activity
        expListView = (ExpandableListView) findViewById(R.id.elv);
        expListView.setAdapter(adapter);
        expandListview();

    }


    public void saveButtonClicked(View view) {
        saveRepetitions();
        finish();
    }


    public void goBackButtonClicked(View view) {
        finish();
    }


    private void saveRepetitions() {

        SharedPreferences sharedPref = getSharedPreferences("RepetitionInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int entryInList = 1;
        for (int row = 0; row < ExpAdapter.childelements.length; row++) {
            for (int col = 0; col < ExpAdapter.childelements[row].length; col++) {
                editor.putInt("e" + String.valueOf(entryInList), ExpAdapter.childelements[row][col].getRepetitions());
                entryInList++;
            }
        }
        editor.apply();
    }


    private void expandListview(){
        for (int i = 0; i < categories.length; i++) {
            expListView.expandGroup(i);
        }
    }


}
