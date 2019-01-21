package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.network.FetchDataHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
    private static final String LOG_TAG = QueryUtils.class.getName();
    private QueryUtils() {
        //this Constructor Should Be EMpty
    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl){
        Log.i(LOG_TAG,"TEST:fetchEarthquakeData() called");

        URL url = FetchDataHttpConnection.createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse=  FetchDataHttpConnection.makeHttpRequest(url);
            Log.e(LOG_TAG,"the json responce is "+ jsonResponse);
        }catch (IOException e){
            Log.e(LOG_TAG,"ERROR IN INPUT STREAM",e);
        }
        return  extractFeatureFromJson(jsonResponse);
    }




    private static List<Earthquake> extractFeatureFromJson(String earthquakeJson){
        //if json string is null or empty, then return early.
        if (TextUtils.isEmpty(earthquakeJson)){
            return null;
        }
        Log.e(LOG_TAG,earthquakeJson);
        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        double mMagnitude = 0.0;
        String mLocation = "";
        String mUrl = "";
        long mTimeInMilliseconds = 0;
        try{
            JSONObject baseJsonResponse = new JSONObject(earthquakeJson);
            JSONArray featureArray = baseJsonResponse.getJSONArray("features");
            for (int i=0;i<featureArray.length();i++){
                JSONObject firstFeature = featureArray.getJSONObject(i);
                JSONObject properties = firstFeature.getJSONObject("properties");

                if (properties.has("magnitude")) {
                    mMagnitude = properties.getDouble("magnitude");
                }
                if (properties.has("location")) {
                    mLocation = properties.getString("location");
                }
                mUrl = properties.getString("url");
                if (properties.has("timeInMilliseconds"))
                mTimeInMilliseconds = properties.getLong("timeInMilliseconds");

               // Earthquake  earthquak =
                earthquakes.add(new Earthquake(mMagnitude, mLocation, mTimeInMilliseconds, mUrl));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */



    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    /**
    public static ArrayList<Earthquake> extractEarthquakes() {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject baseJosnResponse = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray earthquakeArray = baseJosnResponse.getJSONArray("features");

            for ( int i=0; i<earthquakeArray.length();i++){
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double magnitude =properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                Earthquake earthquake = new Earthquake(magnitude,location,time,url);
                //take elements from root and put it in arry list
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
     */

}