package com.neomer.everyprice;

import android.app.ActivityManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MyLocationListener implements LocationListener {

    private Marker personalMarker;
    private GoogleMap googleMap;

    public MyLocationListener() {
        personalMarker = null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (personalMarker == null) {
            return;
        }
        Location loc_old = new Location("point");
        loc_old.setLatitude(personalMarker.getPosition().latitude);
        loc_old.setLongitude(personalMarker.getPosition().longitude);

        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

        if (location.distanceTo(loc_old) > 1000 && googleMap != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        }

        personalMarker.setPosition(position);
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

    public void setMarker(Marker marker) {
        this.personalMarker = marker;
    }

    public Marker getMarker() {
        return personalMarker;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
