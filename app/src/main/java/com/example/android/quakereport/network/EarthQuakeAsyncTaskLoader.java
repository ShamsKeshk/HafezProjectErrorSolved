package com.example.android.quakereport.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.quakereport.QueryUtils;
import com.example.android.quakereport.model.Earthquake;

import java.util.List;

public class EarthQuakeAsyncTaskLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String LOG_TAG = EarthQuakeAsyncTaskLoader.class.getName();
    private String mUrl;

    private List<Earthquake> earthquakeList = null;

    public EarthQuakeAsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG,"TEST:onStartLoading() called");
        if (earthquakeList != null){
            deliverResult(earthquakeList);
        }else {
            forceLoad();
        }
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.e(LOG_TAG,"TEST:loadinBackground() called");
        if (mUrl == null) {
            return null;
        }
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        Log.e(LOG_TAG,"Testing if AsyncTaskLoader not retriving data  --> " + earthquakes);
        return earthquakes;
    }
//
    @Override
    public void deliverResult(List<Earthquake> data) {
        earthquakeList = data;
        super.deliverResult(data);
    }
}
