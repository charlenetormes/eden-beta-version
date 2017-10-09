//package subatom.eden_beta.Detector;
//
//import android.Manifest;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import subatom.eden_beta.*;
//
///**
// * The launch Activity for the app.  Handles camera permission request on Marshmallow+ and
// * provides a button to launch the second Activity.
// *
// * See the README.md file at the root of the ServiceFrameDetectorDemo module for more info on this
// * sample app.
// */
//public class MainDetector{
//    private final static int CAMERA_PERMISSIONS_REQUEST_CODE = 0;
//    private final static String[] CAMERA_PERMISSIONS_REQUEST = new String[]{Manifest.permission.CAMERA};
//    private boolean handleCameraPermissionGrant;
//
//    @SuppressWarnings("ConstantConditions")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_youtube_player);
//
//        Toast.makeText(this, "DETECTOR", Toast.LENGTH_SHORT).show();
//
//        // on Marshmallow+, we have to ask for the camera permission the first time
//        if (!CameraHelper.checkPermission(this) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            requestPermissions(CAMERA_PERMISSIONS_REQUEST, CAMERA_PERMISSIONS_REQUEST_CODE);
//        }
//
//    }
//
//    @SuppressWarnings("ConstantConditions")
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (handleCameraPermissionGrant) {
//            // a response to our camera permission request was received
//            if (CameraHelper.checkPermission(this)) {
//                startService(new Intent(this, DetectorService.class));
//        } else {
//                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
//        }
//        handleCameraPermissionGrant = false;
//        }
//    }
//
//    @Override
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
//}
