package dk.aau.gr6406.trainez;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = "trainez" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.i(TAG,"starting onCreate");



        SharedPreferences sharedPreferences = getSharedPreferences("FirstTimeUse", Context.MODE_PRIVATE);
       //getSharedPreferences("FirstTimeUse", 0).edit().clear().commit();
        String valueFTU = sharedPreferences.getString("valueFTU", "");
        if (valueFTU.contains("FTUcomplete9")) {
          Log.i(TAG, "Entered mainactivity");
        }
        else {
            Intent intent = new Intent(this,FirstTimeActivity.class);
            startActivity(intent);
        }


    }

    /**
     * Method to start the training program activity.
     * @param view
     */
    public void composeProgrammeButtonClicked(View view) {
        Intent intent = new Intent(this, ComposeProgrammeActivity.class);
        startActivity(intent);
    }



    /**
     * Method to start the weight activity.
     * @param view
     */
    public void enterWeightButtonClicked(View view) {
        Intent intent = new Intent(this, EnterWeightActivity.class);
        startActivity(intent);
    }



    /**
     * Method that start program activity.
     * @param view
     */
    public void playProgrammeButtonClicked(View view) {
        Intent intent = new Intent(this, PlayProgrammeActivity.class);
        startActivity(intent);
    }

    /**
     * Method that start statistics activity.
     * @param view
     */
    public void viewStatisticsButtonClicked(View view) {
        Intent intent = new Intent(this, ViewStatisticsActivity.class);
        startActivity(intent);
    }



    public void helpClickedButton(View view) {
        //Popup when the user clicks help
        ViewDialog alert = new ViewDialog();
        alert.showDialog(MainMenuActivity.this, "Help yourself :)");
    }
}