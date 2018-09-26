package com.neomer.everyprice;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.neomer.everyprice.core.ILocationUpdateEventListener;

import java.util.ArrayList;
import java.util.List;

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
