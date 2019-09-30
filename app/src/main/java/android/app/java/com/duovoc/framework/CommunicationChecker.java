package android.app.java.com.duovoc.framework;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

final public class CommunicationChecker {

    private CommunicationChecker() {

    }

    public static boolean isOnline(final Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWifiConnected(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return false;
        }

        return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
