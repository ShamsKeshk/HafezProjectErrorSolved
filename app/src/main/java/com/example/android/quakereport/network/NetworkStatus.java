package com.example.android.quakereport.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkStatus {

    public static boolean isConnected(Activity activity){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null ;
        if (connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
