package io.dflabs.sample.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import io.dflabs.lib.interfaces.OnPhotoPhotoImport;
import io.dflabs.lib.mvp.BaseActivity;
import io.dflabs.lib.mvp.BasePresenter;
import io.dflabs.lib.utils.CameraUtils;
import io.dflabs.sample.views.fragments.FruitListFragment;
import io.dflabs.sample.R;
import io.realm.processor.Utils;

/**
 * Created by Daniel García Alvarado on 10/18/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class ToolbarActivity extends BaseActivity implements OnPhotoPhotoImport {

    private Button mMediaButton;
    private CameraUtils cameraUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.act_toolbar_content, FruitListFragment.newInstance())
                .commit();

        mMediaButton = (Button) findViewById(R.id.act_toolbar_button);
        mMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraUtils = new CameraUtils();
                cameraUtils.importPhoto(ToolbarActivity.this, R.string.photo , ToolbarActivity.this, true);
            }
        });
    }

    @Override
    protected BasePresenter[] getPresenters() {
        return null;
    }

    @Override
    public void onPhotoImport(Bitmap fullBitmap, File file) {
        Toast.makeText(this, file.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartImport() {

    }

    @Override
    public void onErrorImport(Exception exception) {

    }

    @Override
    public void onVideoImport(File file) {
        Toast.makeText(this, file.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraUtils.onActivityResult(requestCode, resultCode, data);

    }
}
