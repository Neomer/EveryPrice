package com.neomer.everyprice;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AddShopActivity extends AppCompatActivity implements ILocationUpdateEventListener {
    private Location currentLocation = null;

    @Override
    public void onLocationReceived(Location location) {
        if (location == null || (currentLocation != null && currentLocation.getAccuracy() < location.getAccuracy())) {
            return;
        }
        currentLocation = location;
        displayLocation();
    }

    private void displayLocation() {
        if (currentLocation == null) {
            return;
        }

        TextView tvLatitude = findViewById(R.id.tvLatitude);
        TextView tvLongitude = findViewById(R.id.tvLongitude);

        try {
            tvLatitude.setText(String.valueOf(currentLocation.getLatitude()));
            tvLongitude.setText(String.valueOf(currentLocation.getLongitude()));
        }
        catch (NullPointerException ex) { }
    }

    final static int LOCATION_PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        MyLocationListener.getInstance().registerEventListener(this);

        setupLocationListener();
    }

    @Override
    protected void onDestroy() {
        MyLocationListener.getInstance().unregisterEventListener(this);

        super.onDestroy();
    }

    private void setupLocationListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Toast.makeText(this, getResources().getString(R.string.error_location_service_not_ready), Toast.LENGTH_SHORT).show();
            finish();
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, MyLocationListener.getInstance());
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, MyLocationListener.getInstance());
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
