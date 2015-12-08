package io.dflabs.lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Daniel García Alvarado on 10/13/14.
 * GnpAjuste - caprinet
 */
@SuppressWarnings({"unused", "deprecation"})
public abstract class ConnectionUtils {

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        } catch (Exception e) {
            ConnectivityManager CManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo NInfo = CManager.getActiveNetworkInfo();
            return NInfo != null && NInfo.isConnected() && NInfo.isAvailable();
        }
    }


}
