package io.github.arnabmaji19.networkcomplaints.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
                    locationManager.removeUpdates(locationListener); //disconnect the location listener
                    //locationManager = null; //set location manager to null
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

            //attach the location listener to location manager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MINIMUM_LOCATION_REFRESH_TIME,
                    MINIMUM_LOCATION_REFRESH_DISTANCE,
                    locationListener);
        }
    }

    public Address getAddress(Location location) {
        //Returns address info for the current Location
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(),
                    1);
            return addressList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isGPSEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public interface OnSuccessListener {
        void onSuccess(Location location);
    }
}