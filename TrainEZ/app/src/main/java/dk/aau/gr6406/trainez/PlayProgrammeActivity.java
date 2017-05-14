package dk.aau.gr6406.trainez;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.security.AccessController.getContext;

/**
 * @author Group 6404, Aalborg University, Sundhedsteknologi, 6th semester
 * @version 1.0
 */

public class PlayProgrammeActivity extends AppCompatActivity {

    private static final String TAG = "dk.aau.trainez";
    private static final Integer CAMERA = 0x5;
    //private static Context contextOfApplication;

    private boolean windowSize = true;
    private VideoView videov;
    private FrameLayout camera_view;

    private TrainingProgramme trainingProgramme;

    //LinkedHashMap<String, Exercise> playProgrammeExercises;
    private Iterator iterator;
    private int nextExerciseInLine = 0;
    private Exercise getNextExercise = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_programme);
        // Receive the choses exercises in an arraylist (Created in menuactivity in OnResume())
        //contextOfApplication = getApplicationContext();
        getSharedPreferences("repInfo", 0).edit().clear().commit();
        // prepare the video layout
        videov = (VideoView) findViewById(R.id.videoView);
        //  mediaC = new MediaController(this);
        trainingProgramme = new TrainingProgramme(this);
        LinkedHashMap<String, Exercise> playProgrammeExercises = trainingProgramme.playProgrammeExercises();
        // Get a set of the entries
        Set set = playProgrammeExercises.entrySet();
        // Get an iterator
        iterator = set.iterator();

        videoplay(getNextExercise().getVideoPath());
        askForPermission(Manifest.permission.CAMERA, CAMERA);

    }

    public void backToMainMenu(View view) {
        finish();
    }

    public void nextButtonClicked(View view) {
        getCurrentExercise();
        Log.i(TAG, "nextButtonClicked");

    }

    private void getCurrentExercise(){
        Map.Entry me = (Map.Entry) iterator.next();
        Log.i(TAG, "next exercise clicked " + me.getKey().toString());
        Exercise exercise = (Exercise) me.getValue();
        showDialog(String.valueOf(exercise.getRepetitions()), (String) me.getKey());
    }

    private void showDialog(String msg, final String exID) {
        final int[] counter = {Integer.parseInt(msg)};
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.play_programme_dialog);

        final TextView text = (TextView) dialog.findViewById(R.id.playtraing_text_dialog);
        text.setText(msg);

        // plus button clicked
        ImageButton plusButtonClicked = (ImageButton) dialog.findViewById(R.id.playtraining_plus);
        plusButtonClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter[0]++;
                text.setText(String.valueOf(counter[0]));
            }
        });

        // Minus button clicked
        ImageButton minusButtonClicked = (ImageButton) dialog.findViewById(R.id.playtraining_minus);
        minusButtonClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter[0] > 0) {
                    counter[0]--;
                    text.setText(String.valueOf(counter[0]));
                }
            }
        });

        // Ok buttton clicked
        Button okButtonClicked = (Button) dialog.findViewById(R.id.playtraining_btn_dialog);
        okButtonClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs = getSharedPreferences("repInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt(exID, counter[0]);
                editor.commit();
                // PlayTrainingProgram.programFinished=true;
                Exercise currentExercise = getNextExercise();

                if (currentExercise != null) {
                    videoplay(currentExercise.getVideoPath());
                } else {
                    saveResult();
                    sessionScore();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private Exercise getNextExercise() {
        TrainingProgramme tProgramme = new TrainingProgramme(this);
        LinkedHashMap<String, Exercise> allExercises = tProgramme.playProgrammeExercises();

        Set nextExerciseSet = allExercises.entrySet();
        Iterator is = nextExerciseSet.iterator();
        Map.Entry mapEntry = null;

        for (int i = 0; i < nextExerciseInLine + 1; i++) {
            if (is.hasNext()) {
                mapEntry = (Map.Entry) is.next();
            } else {
                return null;
            }
        }
        nextExerciseInLine++;
        Log.i(TAG, "getNextExercise " + mapEntry.getKey().toString() + " " + nextExerciseInLine);
        return (Exercise) mapEntry.getValue();
    }


    private void saveResult() {
        ExerciseMeasurement exerciseMeasurement = new ExerciseMeasurement();
        SharedPreferences sharedPref = getSharedPreferences("repInfo", Context.MODE_PRIVATE);
        int programmeSize = trainingProgramme.getTrainingProgramme().size();
        ArrayList<Integer> repetitions = new ArrayList<>();
        for (int i = 1; i < programmeSize + 1; i++) {
            repetitions.add(sharedPref.getInt("e" + i, 0));
        }
        exerciseMeasurement.setExercises(repetitions);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String formattedDate = formatter.format(System.currentTimeMillis());

        exerciseMeasurement.set_date(formattedDate);
        Log.i(TAG, "Exercises in exerciseMeasurement (PlayProgrammeActivity) " + exerciseMeasurement.getExercies());
        DbHandler myDbHandler = new DbHandler(this, null, null, 1);
        myDbHandler.addExerciseMeasurement(exerciseMeasurement);
        String exercisesString = myDbHandler.databaseToStringExercise();
        Log.i(TAG, "Exercises in db " + exercisesString);
    }

    private void sessionScore() {
        SharedPreferences sharedPref = getSharedPreferences("repInfo", Context.MODE_PRIVATE);
        Log.i(TAG, "Entered sessionScore");

        TrainingProgramme programme = new TrainingProgramme(this);
        LinkedHashMap<String, Exercise> expectedRepetitions = programme.playProgrammeExercises();

        String scoreValues = "";

        Map<String, ?> keys = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            scoreValues += expectedRepetitions.get(entry.getKey()).getExcName() + ": " +
                    entry.getValue() + " out of " + expectedRepetitions.get(entry.getKey()).
                    getRepetitions() + "\n";
        }
        ViewDialog alert = new ViewDialog();
        alert.showDialog(PlayProgrammeActivity.this, scoreValues);
    }

    /**
     * This method is used to get persmission for acceseing the camera.
     *
     * @param permission
     * @param requestCode Is the rqust code declared in the start of the class
     */
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(PlayProgrammeActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PlayProgrammeActivity.this, permission)) {
                //This is called if user has denied the permission before
                //In this case we just asking the permission again
                ActivityCompat.requestPermissions(PlayProgrammeActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(PlayProgrammeActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            // if the permission is already granted the camera starts
            startCamera();
        }
    }

    /**
     * This method is responsible for starting the camera
     */
    private void startCamera() {
        Camera mCamera = null;
        try {
            //you can use open(int) to use different cameras. 1 is front camera
            mCamera = Camera.open(1);
        } catch (Exception e) {
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }
        //create a SurfaceView to show camera data. Is mCamera returns an object the camera will start
        if (mCamera != null) {
            CameraView mCameraView = new CameraView(this, mCamera);
            // CameraSurfaceView mCameraView = new CameraSurfaceView(this);

            camera_view = (FrameLayout) findViewById(R.id.camera_view);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }
    }

    /**
     * House keeping stuff for  getting permission to the camera. In the middle of this method
     * the startCamera method is called (if there is a valid permission key!)
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            // The camera is started here.
            startCamera();
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method plays the videos for the exercises.
     */
    public void videoplay(String videoCurrentlyPlaying) {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + videoCurrentlyPlaying);
        MediaController mediaC = new MediaController(this);
        videov.setVideoURI(uri);
        videov.setMediaController(mediaC);
         mediaC.setAnchorView(videov);
        videov.start();
        videov.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Loop the video
                videov.start();
            }
        });
    }



    /**
     * This method is called when the button in top left corner has been clicked
     *
     * @param view
     */
    public void videoFrameClicked(View view) {
        resizeVideo();
    }

    private void resizeVideo() {

        // Converting dips to pixels
        float dips = 256.0f;
        float scale = getResources().getDisplayMetrics().density;
        int pixels = Math.round(dips * scale);
        float dips2 = 168.0f;
        int pixels2 = Math.round(dips * scale);


        if (windowSize == true) {
            videov.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
            videov.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
            windowSize = false;
        } else {
            videov.getLayoutParams().height = (int) pixels;
            videov.getLayoutParams().width = (int) pixels2;
            windowSize = true;
        }

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) videov.getLayoutParams();
        params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        videov.setLayoutParams(params);
        camera_view.setVisibility(View.GONE);
        ((View) videov.getParent()).invalidate();
        ((View) videov.getParent()).requestLayout();
        camera_view.setVisibility(View.VISIBLE);
    }
}
