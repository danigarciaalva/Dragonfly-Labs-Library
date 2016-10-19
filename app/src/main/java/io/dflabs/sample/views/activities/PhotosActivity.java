package io.dflabs.sample.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import io.dflabs.lib.interfaces.OnPhotoImport;
import io.dflabs.lib.mvp.BaseActivity;
import io.dflabs.lib.utils.CameraUtils;
import io.dflabs.lib.validators.DateValidator;
import io.dflabs.lib.validators.FormValidator;
import io.dflabs.sample.R;
import io.dflabs.sample.views.adapters.PhotosAdapter;

/**
 * Created by Daniel García Alvarado on 10/18/15.
 * DragonflyLabs Library Sample - danielgarcia
 */
public class PhotosActivity extends BaseActivity implements OnPhotoImport {

    private CameraUtils mCameraUtils;
    private PhotosAdapter mPhotosAdapter;
    private ArrayList<Bitmap> mPhotos;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        Button mediaButton = (Button) findViewById(R.id.act_toolbar_button);
        mediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraUtils = new CameraUtils();
                mCameraUtils.importPhoto(PhotosActivity.this, R.string.import_photo, PhotosActivity.this, true);
            }
        });
        mPhotos = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.act_photos_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mPhotosAdapter = new PhotosAdapter());

        FormValidator formValidator = new FormValidator(this, true);
        formValidator.addValidators( new DateValidator("22/10/2015", "23/10/2015", DateValidator.NOT_EQUAL_DATE, R.string.test_date));

        if (formValidator.isValid()){
            Toast.makeText(this, "Date correct", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPhotoImport(Bitmap thumbnail, File file) {
        mPhotos.add(thumbnail);
    }

    @Override
    public void onStartImport() {
        mProgressDialog = ProgressDialog.show(this, null,
                getString(R.string.dialog_loading_import_photos), true, false);
        mProgressDialog.show();
    }

    @Override
    public void onSuccessImport() {
        mPhotosAdapter.update(mPhotos);
        mPhotos.clear();
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

    @Override
    public void onErrorImport(Exception exception) {
        if (mProgressDialog != null) mProgressDialog.dismiss();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCameraUtils.onActivityResult(requestCode, resultCode, data);
    }
}
