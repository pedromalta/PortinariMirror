package net.clubedocomputador.portinarimirror.features.faces;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaActionSound;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import net.clubedocomputador.portinarimirror.R;
import net.clubedocomputador.portinarimirror.features.faces.widget.CameraSourcePreview;
import net.clubedocomputador.portinarimirror.features.faces.widget.FaceGraphic;
import net.clubedocomputador.portinarimirror.features.faces.widget.GraphicOverlay;
import net.clubedocomputador.portinarimirror.util.AppLogger;
import net.clubedocomputador.portinarimirror.util.Util;

import java.io.IOException;


import butterknife.BindView;
import butterknife.ButterKnife;


public class FaceView extends FrameLayout {


    @BindView(R.id.shutter)
    FrameLayout shutter;

    @BindView(R.id.fade_camera)
    FrameLayout fadeCamera;

    @BindView(R.id.loading)
    ProgressBar progressBar;

    private CameraSource mCameraSource = null;

    @BindView(R.id.faceOverlay)
    GraphicOverlay mGraphicOverlay;

    @BindView(R.id.preview)
    CameraSourcePreview mPreview;

    private boolean loading = false;

    private OnPictureTakenListener onPictureTakenListener;

    public FaceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_face_detection, this);
        ButterKnife.bind(this);
        setupCameraPreview();
    }

    private void setupCameraPreview() {
        loading = false;
        fadeCamera.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        FaceDetector detector = new FaceDetector.Builder(getContext().getApplicationContext())
                .setProminentFaceOnly(true) // optimize for single, relatively large face
                .setTrackingEnabled(true) // enable face tracking
                .setClassificationType(FaceDetector.NO_CLASSIFICATIONS)
                .setMinFaceSize(0.7f)
                .setMode(FaceDetector.FAST_MODE) // for one face this is OK
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());


        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            AppLogger.e("Face detector dependencies are not yet available.");
            Util.DialogFactory.createGenericErrorDialog(getContext(), R.string.dialog_error_message_no_camera);
        }

        mCameraSource = new CameraSource.Builder(getContext().getApplicationContext(), detector)
                .setRequestedPreviewSize(1024, 768)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();

        startCameraSource();


    }

    private void startCameraSource() {
        if (mCameraSource != null) {
            try {

                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                //Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private void takePicture() {

        mCameraSource.takePicture(
                () -> {
                    shutter.setVisibility(View.VISIBLE);
                    MediaActionSound sound = new MediaActionSound();
                    sound.play(MediaActionSound.SHUTTER_CLICK);
                    //Handler handler = new Handler();
                    //Runnable runnable = () -> shutter.setVisibility(View.GONE);
                    //handler.postDelayed(runnable, 500);

                },
                (imageBytes) -> {
                    shutter.setVisibility(View.GONE);
                    if (null != onPictureTakenListener){
                        onPictureTakenListener.onPictureTaken(imageBytes);
                    }
                });

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startCameraSource();
    }

    @Override
    protected void onDetachedFromWindow() {
        mPreview.stop();
        mPreview.release();
        super.onDetachedFromWindow();
    }

    public void stop(){
        mPreview.stop();
    }

    public void start(){
        startCameraSource();
    }


    public void showLoading() {
        loading = true;
        fadeCamera.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mPreview.stop();
    }

    public void hideLoading() {
        //Hold new pictures being taken by 3s to avoid unnecessary taking pictures
        final Handler handler = new Handler();
        handler.postDelayed(() -> loading = false, 3000);
        fadeCamera.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        setupCameraPreview();
    }

    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;
        private int elapsedFrameCount = 0;
        private static final int frameCountForPicture = 10;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face);
            elapsedFrameCount++;
            if (!loading && elapsedFrameCount > frameCountForPicture) {
                elapsedFrameCount = 0;
                takePicture();
            }

        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }

    }

    public OnPictureTakenListener getOnPictureTakenListener() {
        return onPictureTakenListener;
    }

    public void setOnPictureTakenListener(OnPictureTakenListener onPictureTakenListener) {
        this.onPictureTakenListener = onPictureTakenListener;
    }

    public interface OnPictureTakenListener{

        void onPictureTaken(byte[] picture);
    }


}

