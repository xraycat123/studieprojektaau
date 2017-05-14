package dk.aau.gr6406.trainez;


import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * @author Group 6404, Aalborg University, Sundhedsteknologi, 6th semester
 * @version 1.0
 */
public class VideoPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        // Recieve the object
        Bundle exerciseData = getIntent().getExtras();
        // Get parcelable. The key is the argument
        Exercise recievedExerciseData = (Exercise) exerciseData.getParcelable("position_key");
        // Play the video
        playExerciseVideo(recievedExerciseData);
    }

    /**
     * This method set up the videoView and mediaController for the selected video.
     *
     * @param excercise This is the Training excercise object. The object contains the video path.
     */
    void playExerciseVideo(Exercise excercise) {
        VideoView videoView = (VideoView) findViewById(R.id.videoExcPlayer);
        // Start the mediacontrols(play, stop etc..)
        MediaController mediaC = new MediaController(VideoPlayer.this);
        // The path to the video
        videoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + excercise.getVideoPath()));
        videoView.setMediaController(mediaC);
        mediaC.setAnchorView(videoView);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

    }
}
