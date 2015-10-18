package io.dflabs.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import io.dflabs.lib.interfaces.OnPhotoPhotoImport;
import io.dflabs.lib.interfaces.OnPhotoTaken;
import io.dflabs.lib.utils.CameraUtils;

/**
 * Created by Daniel García Alvarado on 10/18/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class CameraFragment extends Fragment implements OnPhotoPhotoImport, OnPhotoTaken {
    private static final String TAG = CameraFragment.class.getSimpleName();
    private CameraUtils mCameraUtils;
    private ImageView imageView;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        mCameraUtils = new CameraUtils();
        v.findViewById(R.id.importButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraUtils.importPhoto(CameraFragment.this,
                        R.string.import_photo, CameraFragment.this, false);
            }
        });
        v.findViewById(R.id.takeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraUtils.takePhoto(CameraFragment.this, CameraFragment.this);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPhotoImport(Bitmap bitmap, File path) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onStartImport() {
        Log.d(TAG, "Start import");
    }

    @Override
    public void onErrorImport(Exception exception) {
        Log.d(TAG, "Error import");
    }

    @Override
    public void onPhotoSuccess(Bitmap fullBitmap, File file) {
        imageView.setImageBitmap(fullBitmap);
    }

    @Override
    public void onPhotoError(Exception exception) {
        Log.d(TAG, exception.getMessage());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCameraUtils.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
