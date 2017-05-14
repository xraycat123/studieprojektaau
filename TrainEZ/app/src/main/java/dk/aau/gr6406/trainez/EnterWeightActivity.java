package dk.aau.gr6406.trainez;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EnterWeightActivity extends AppCompatActivity {

    private static final String TAG = "dk.aau.trainez";
    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_weight);
        dbHandler = new DbHandler(this, null, null, 1);
    }

    /*
    * This method is called when the save button has been clicked
    */
    public void saveButtonClicked(View view) {
        setFirstTimePref();

        EditText weightText = (EditText) findViewById(R.id.enter_weight_edit_text);

        float weight = Float.parseFloat(weightText.getText().toString());

        if (weight>20 && weight<150) {
            float enteredWeight = Float.parseFloat(weightText.getText().toString());
            WeightMeasurement weightMeasurement = new WeightMeasurement(enteredWeight);
            weightMeasurement.set_date(getCurrentDate());

            dbHandler.addWeightMeasurement(weightMeasurement);

            Log.i(TAG, dbHandler.databaseToStringWeight());

            Toast.makeText(EnterWeightActivity.this, "Weight registered", Toast.LENGTH_SHORT).show();
            makeTimeFramedNotification(3); // pareameter = seconds before notification
            Intent intent = new Intent(this,MainMenuActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(EnterWeightActivity.this, "Invalid value!", Toast.LENGTH_SHORT).show();
        }

    }

    private void setFirstTimePref() {
        SharedPreferences sharedPreferences = getSharedPreferences("FirstTimeUse", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("valueFTU", "FTUcomplete9");
        editor.apply();
    }

    /**
     * This method brings you to the menu.
     *
     * @param view
     */
    public void goBackButtonClicked(View view) {
        finish();
    }

    /**
     * This method is used to set to alert (notification)
     */
    private void makeTimeFramedNotification(int seconds) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Long alertTime = calendar.getTimeInMillis() + 1000 * seconds;

        Intent alertIntent = new Intent(this, dk.aau.gr6406.trainez.Alarm.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this,
                1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    private String getCurrentDate() {
        long unixTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return formatter.format(unixTime);
    }

}






