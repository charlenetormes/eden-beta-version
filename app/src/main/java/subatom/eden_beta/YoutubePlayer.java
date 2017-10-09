package subatom.eden_beta;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import subatom.eden_beta.Detector.*;


public class YoutubePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, Detector.ImageListener, CameraDetector.CameraEventListener {

    public static final String API_KEY = "AIzaSyAhSHZi4V8YvFqLWNJWsAkICj4l8Wkug_k";
    public String VIDEO_ID;

    private final static int CAMERA_PERMISSIONS_REQUEST_CODE = 0;
    private final static String[] CAMERA_PERMISSIONS_REQUEST = new String[]{android.Manifest.permission.CAMERA};
    private boolean handleCameraPermissionGrant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        VIDEO_ID = getIntent().getStringExtra("url");
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
    }



    @Override
    protected void onResume () {
        super.onResume();
//        if (handleCameraPermissionGrant) {
//            // a response to our camera permission request was received
//            if (CameraHelper.checkPermission(this)) {
//                startService(new Intent(this, FaceDetector.class));
//                Toast.makeText(this, "WAKAKAKAAKAKAAKAKA", Toast.LENGTH_SHORT).show();
//            }
//            handleCameraPermissionGrant = false;
//        }

    }

//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE) {
//            for (String permission : permissions) {
//                if (permission.equals(Manifest.permission.CAMERA)) {
//                    // next time through onResume, handle the grant result
//                    handleCameraPermissionGrant = true;
//                    break;
//                }
//            }
//        }
//    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setFullscreen(true);
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT); //no play, no meter

        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
        //canDetect = true;
        //startService(new Intent(this, FaceDetector.class));
    }

    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
            //Toast.makeText(YoutubePlayer.this, (arg0 == true ? "TRUE":"FALSE"), Toast.LENGTH_SHORT).show();
            Emotion.detect = false;

        }
        @Override
        public void onPaused() {
            //pause timer, pause detector
            //Toast.makeText(YoutubePlayer.this, "PAUSE LIKE A POSSE", Toast.LENGTH_SHORT).show();
            Emotion.detect = false;
        }
        @Override
        public void onPlaying() {
            //resume timer, resume detector
            //Toast.makeText(YoutubePlayer.this, "PLAY LIKE A PLAYBOY", Toast.LENGTH_SHORT).show();
            Emotion.detect = true;


        }
        @Override
        public void onSeekTo(int ms) {
            //overwrite list
        }
        @Override
        public void onStopped() {
            //Toast.makeText(YoutubePlayer.this, "STOP LIKE A CADILLAC", Toast.LENGTH_SHORT).show();
            Emotion.detect = false;
        }
    };
    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
            //Toast.makeText(YoutubePlayer.this, "FUCKING ADS", Toast.LENGTH_SHORT).show();
            Emotion.detect = false;
        }
        @Override
        public void onError(ErrorReason arg0) {
            //Toast.makeText(YoutubePlayer.this, arg0.name(), Toast.LENGTH_SHORT).show();
            stopService(new Intent(YoutubePlayer.this, DetectorService.class));
            Emotion.detect = false;
        }
        @Override
        public void onLoaded(String arg0) {
            //Toast.makeText(YoutubePlayer.this, "LOAD LIKE YOUR MOM IN HER MOUTH", Toast.LENGTH_SHORT).show();
            Emotion.detect = false;

        }
        @Override
        public void onLoading() {
            //Toast.makeText(YoutubePlayer.this, "LOADING LIKE YOUR MOM'S MOUTH RIGHT NOW", Toast.LENGTH_SHORT).show();
            Emotion.detect = false;
        }
        @Override
        public void onVideoEnded() {
            //Toast.makeText(YoutubePlayer.this, "ENDED", Toast.LENGTH_SHORT).show();
            stopService(new Intent(YoutubePlayer.this, DetectorService.class));
            Intent i = new Intent(YoutubePlayer.this, Statistics.class);
            //send json to statistics
            startActivity(i);
            Emotion.detect = false;
            //Toast.makeText(YoutubePlayer.this, Emotion.getBrowFurrow(0) + " ", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onVideoStarted() {
            // start emotion detector

//            if (!CameraHelper.checkPermission(getApplicationContext()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                ActivityCompat.requestPermissions(YoutubePlayer.this, CAMERA_PERMISSIONS_REQUEST, CAMERA_PERMISSIONS_REQUEST_CODE);
//            }

            if (!CameraHelper.checkPermission(YoutubePlayer.this)) {
                requestPermissions(CAMERA_PERMISSIONS_REQUEST, CAMERA_PERMISSIONS_REQUEST_CODE);
            } else {
                if (CameraHelper.checkPermission(getApplicationContext())) {
                    startService(new Intent(YoutubePlayer.this, DetectorService.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }


            if (handleCameraPermissionGrant) {
                // a response to our camera permission request was received
                if (CameraHelper.checkPermission(getApplicationContext())) {
                    startService(new Intent(getApplicationContext(), DetectorService.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                handleCameraPermissionGrant = false;
            }
            Emotion.detect = true;

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE) {
            for (String permission : permissions) {
                if (permission.equals(Manifest.permission.CAMERA)) {
                    // next time through onResume, handle the grant result
                    handleCameraPermissionGrant = true;
                    break;
                }
            }
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onCameraSizeSelected(int i, int i1, Frame.ROTATE rotate) {

    }

    @Override
    public void onImageResults(List<Face> list, Frame frame, float v) {

    }


}