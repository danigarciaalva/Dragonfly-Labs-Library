package io.dflabs.lib.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.dflabs.lib.interfaces.OnPhotoPhotoImport;
import io.dflabs.lib.interfaces.OnPhotoTaken;

/**
 * Created by Daniel García Alvarado on 7/5/15.
 * GnpAjuste - danielgarcia
 */
@SuppressWarnings("unused")
public class CameraUtils {

    public static final int REQUEST_TAKE_PHOTO = 0x999;
    public static final int REQUEST_IMPORT_PHOTO = 0x998;
    OnPhotoPhotoImport onPhotoPhotoImport;
    private File photoFile;
    private OnPhotoTaken onPhotoTaken;
    private Context context;

    public void takePhoto(AppCompatActivity context, OnPhotoTaken onPhotoTaken) {
        this.context = context;
        this.onPhotoTaken = onPhotoTaken;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                onPhotoTaken.onPhotoError(e);
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                context.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        } else {
            onPhotoTaken.onPhotoError(new IllegalStateException("Not camera app installed"));
        }
    }

    public void takePhoto(Fragment context, OnPhotoTaken onPhotoTaken) {
        this.context = context.getContext();
        this.onPhotoTaken = onPhotoTaken;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.context.getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                onPhotoTaken.onPhotoError(e);
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                context.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        } else {
            onPhotoTaken.onPhotoError(new IllegalStateException("Not camera app installed"));
        }
    }

    public void importPhoto(AppCompatActivity context, @StringRes int message,
                            OnPhotoPhotoImport callback, boolean allowMultiple) {
        this.context = context;
        this.onPhotoPhotoImport = callback;
        if (callback == null) {
            throw new NullPointerException("OnMultiplePhotoImport must not be null");
        }
        if (allowMultiple) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Intent intent = new Intent();
                intent.setType("image/video");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                context.startActivityForResult(Intent.createChooser(intent, context.getString(message)),
                        REQUEST_IMPORT_PHOTO);
            } else {
                throw new IllegalArgumentException("Multiple import require minimum API 18");
            }
        } else {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            context.startActivityForResult(i, REQUEST_IMPORT_PHOTO);
        }
    }

    public void importPhoto(Fragment context, @StringRes int message,
                            OnPhotoPhotoImport callback, boolean allowMultiple) {
        this.context = context.getContext();
        this.onPhotoPhotoImport = callback;
        if (callback == null) {
            throw new NullPointerException("OnMultiplePhotoImport must not be null");
        }
        if (allowMultiple) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Intent intent = new Intent();
                intent.setType("image/video");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                context.startActivityForResult(Intent.createChooser(intent, context.getString(message)),
                        REQUEST_IMPORT_PHOTO);
            } else {
                throw new IllegalArgumentException("Multiple import require minimum API 18");
            }
        } else {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            context.startActivityForResult(i, REQUEST_IMPORT_PHOTO);
        }
    }

    public void importMedia(Fragment context, @StringRes int message,
                       OnPhotoPhotoImport callback){
        this.context = context.getContext();
        this.onPhotoPhotoImport = callback;
        if (callback == null) {
            throw new NullPointerException("OnMultiplePhotoImport must not be null");
        }
        Intent intent = new Intent();
        intent.setType("image/video");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent, context.getString(message)),
                REQUEST_IMPORT_PHOTO);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraUtils.REQUEST_TAKE_PHOTO) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                onPhotoTaken.onPhotoSuccess(fullBitmap(), photoFile);
            }
        } else if (requestCode == REQUEST_IMPORT_PHOTO && resultCode == AppCompatActivity.RESULT_OK
                && null != data) {
            Uri selectedFile = data.getData();
            if (selectedFile != null) {
                ContentResolver contentResolver = context.getContentResolver();
                String mediaType = contentResolver.getType(selectedFile);
                if (mediaType != null) {
                    if (mediaType.contains("image")) {
                        CameraImportTask task = new CameraImportTask(context, new Uri[]{selectedFile},
                                onPhotoPhotoImport);
                        task.execute();
                    } else if (mediaType.contains("video")) {
                        File file = new File(CameraImportTask.getRealPathFromURI(context, selectedFile));
                        onPhotoPhotoImport.onVideoImport(file);
                    } else {
                        onPhotoPhotoImport.onErrorImport(new RuntimeException("Incompatible media type"));
                        Log.d("Error", "Incompatible media type");
                    }
                } else {
                    onPhotoPhotoImport.onErrorImport(new RuntimeException("Media type null"));
                    Log.d("Error", "Media type null");
                }
            } else {
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    Uri[] uris = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        uris[i] = clipData.getItemAt(i).getUri();
                    }
                    CameraImportTask task = new CameraImportTask(context, uris, onPhotoPhotoImport);
                    task.execute();
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private Bitmap fullBitmap() {
        try {

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / 300, photoH / 300);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

            return BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
