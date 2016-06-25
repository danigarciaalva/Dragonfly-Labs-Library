package io.dflabs.lib.interfaces;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Daniel García Alvarado on 10/11/15.
 * Gastalon - danielgarcia
 */
public interface OnPhotoPhotoImport {
    void onPhotoImport(Bitmap fullBitmap, File file);

    void onStartImport();

    void onErrorImport(Exception exception);

    void onVideoImport(File file);
}
