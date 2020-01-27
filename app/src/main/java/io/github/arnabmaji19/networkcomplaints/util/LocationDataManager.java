package io.github.arnabmaji19.networkcomplaints.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

public class LocationDataManager {

    private final static int MINIMUM_LOCATION_REFRESH_TIME = 1000; //In milliseconds
    private final static int MINIMUM_LOCATION_REFRESH_DISTANCE = 1; //in meters

    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public LocationDataManager(Context context, LocationManager locationManager) {
        this.context = context;
        this.locationManager = locationManager;
    }

    public void requestCurrentLocation(final OnSuccessListener onSuccessListener) {
        //check if permission is granted for Location Service
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) { //Only if permission is granted

            //Get location from last known location
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (currentLocation != null) {
                onSuccessListener.onSuccess(currentLocation);
                return;
            }

            //If last known location is null
            //then, get current location using location listener
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    onSuccessListener.onSuccess(location);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            //set up criteria for high accuracy
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            //request single location update
            locationManager.requestSingleUpdate(criteria, locationListener, null);
        }
    }

    public boolean isGPSEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public interface OnSuccessListener {
        void onSuccess(Location location);
    }
}