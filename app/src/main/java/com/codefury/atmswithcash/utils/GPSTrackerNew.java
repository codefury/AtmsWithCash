package com.codefury.atmswithcash.utils;

/**
 * Created by apoorv on 15-11-2016.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.codefury.atmswithcash.Prefs;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GPSTrackerNew extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //GPS Tracker


    private final String TAG = "atmwithcash";
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Prefs mPrefs;

    public GPSTrackerNew(Context context) {
        this.mContext = context;
        mPrefs = Prefs.with(context);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        //mPrefs=Prefs.with(context);
    }

    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("From service:", "Location received: " + location.toString());
        try {
            mPrefs.save(Constants.WASHERLAT, String.valueOf(location.getLatitude()));
            mPrefs.save(Constants.WASHERLONGI, String.valueOf(location.getLongitude()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

