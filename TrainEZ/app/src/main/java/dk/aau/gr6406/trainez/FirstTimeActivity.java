package dk.aau.gr6406.trainez;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public class FirstTimeActivity extends AppCompatActivity {

    private static final String TAG = "dk.aau.trainez";
    private List<String> li;
    private boolean[] categoryToggle;
    private Exercise childelements[][];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        // Set the layout for the listView
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.topMargin = 300;

        Button show = (Button) findViewById(R.id.proceedbtn);
        final ListView list = new ListView(this);

        // Items to be added to the listView
        li = new ArrayList<String>();

        // Retrieve the categories
        TrainingProgramme trainingProgramme = new TrainingProgramme(this);
        for (String m : trainingProgramme.getCategories()) {
            li.add(m);
        }
        // Retrieve the exercises grouped by categories
        childelements = trainingProgramme.getGroupedExercises();

        categoryToggle = new boolean[trainingProgramme.getCategories().size()];

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_dropdown_item_1line, li);

        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        list.setAdapter(adp);
        list.setLayoutParams(params);
        rl.addView(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {

                TextView categoryText = (TextView) v.findViewById(android.R.id.text1);
                if (!categoryToggle[pos]) {
                    categoryText.setTextColor(Color.RED);
                    categoryToggle[pos] = true;
                    Log.i(TAG, String.valueOf( categoryToggle[pos] ) + " " + pos);

                } else {
                    categoryText.setTextColor(Color.BLACK);
                    categoryToggle[pos] = false;
                }
            }
        });
    }

    private void saveCategories() {

        int column = 0;
        for (Boolean m : categoryToggle) {
            if(m) {
               setDefaultVal(column);
            }
            column++;
        }

        SharedPreferences sharedPref = getSharedPreferences("RepetitionInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int entryInList = 1;
        for (int row = 0; row < childelements.length; row++) {
            for (int col = 0; col < childelements[row].length; col++) {
                editor.putInt("e" + String.valueOf(entryInList), childelements[row][col].getRepetitions());
                entryInList++;
            }
        }
        editor.apply();
    }

    public void proceedbtn(View view) {
       saveCategories();
        Intent intent = new Intent(this, EnterWeightActivity.class);
        startActivity(intent);
    }

    private void setDefaultVal(int groupPosition) {
        for (int i = 0; i < childelements[groupPosition].length; i++) {
            int defValue = childelements[groupPosition][i].getDefaultVal();
            childelements[groupPosition][i].setRepetitions(defValue);
        }
    }


}

