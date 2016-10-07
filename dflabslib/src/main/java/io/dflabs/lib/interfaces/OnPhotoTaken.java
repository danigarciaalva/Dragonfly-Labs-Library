package io.dflabs.lib.interfaces;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Daniel García Alvarado on 10/11/15.
 * Gastalon - danielgarcia
 */
public interface OnPhotoTaken {

    void onPhotoSuccess(Bitmap fullBitmap, File file);
    void onPhotoError(Exception exception);
    void onPhotoCancelled();
}
