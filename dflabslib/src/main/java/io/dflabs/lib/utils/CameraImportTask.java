package io.dflabs.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.io.InputStream;

import io.dflabs.lib.interfaces.OnPhotoPhotoImport;

/**
 * Created by Daniel García Alvarado on 10/11/15.
 * Gastalon - danielgarcia
 */
public class CameraImportTask extends AsyncTask<Void, CameraImportTask.FileBitmap, Boolean> {

    private final Uri[] uris;
    private OnPhotoPhotoImport onPhotoPhotoImport;
    private final Context context;
    private Exception exception;

    public CameraImportTask(Context context, Uri[] uris, OnPhotoPhotoImport onPhotoPhotoImport) {
        this.context = context;
        this.uris = uris;
        this.onPhotoPhotoImport = onPhotoPhotoImport;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPhotoPhotoImport.onStartImport();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        InputStream is;
        try {
            for (Uri uri : uris) {
                is = context.getContentResolver().openInputStream(uri);
                if (is != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    publishProgress(new FileBitmap(bitmap, new File(uri.getPath())));
                    bitmap.recycle();
                    is.close();
                }
            }
        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(FileBitmap... values) {
        super.onProgressUpdate(values);
        onPhotoPhotoImport.onPhotoImport(values[0].bitmap, values[0].file);

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        onPhotoPhotoImport.onErrorImport(exception);
    }

    class FileBitmap {
        Bitmap bitmap;
        File file;

        public FileBitmap(Bitmap bitmap, File file) {
            this.bitmap = bitmap;
            this.file = file;
        }
    }
}
