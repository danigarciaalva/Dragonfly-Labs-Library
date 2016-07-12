package io.dflabs.lib.interfaces;

/**
 * Created by danielgarcia on 6/28/16.
 * DFLabs
 */
public interface OnImport {

    void onStartImport();

    void onSuccessImport();

    void onErrorImport(Exception exception);

}
