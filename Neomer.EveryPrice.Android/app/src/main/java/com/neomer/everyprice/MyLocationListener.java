package com.neomer.everyprice;

import android.app.ActivityManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public final class MyLocationListener implements LocationListener {

    private static MyLocationListener instance;
    private Location lastLocation = null;

    public static MyLocationListener getInstance() {
        if (instance == null) {
            instance = new MyLocationListener();
        }
        return instance;
    }

    private List<ILocationUpdateEventListener> eventListenerList;

    public void registerEventListener(ILocationUpdateEventListener listener) {
        eventListenerList.add(listener);
    }

    public void unregisterEventListener(ILocationUpdateEventListener listener) {
        try {
            eventListenerList.remove(listener);
        }
        catch (Exception ex) {}
    }

    private MyLocationListener() {
        eventListenerList = new ArrayList<ILocationUpdateEventListener>();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        for (ILocationUpdateEventListener l: eventListenerList) {
            l.onLocationReceived(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public Location getLastLocation() {
        return lastLocation;
    }
}
