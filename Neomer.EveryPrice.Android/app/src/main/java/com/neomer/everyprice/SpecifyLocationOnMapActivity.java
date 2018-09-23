package com.neomer.everyprice;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SpecifyLocationOnMapActivity extends AppCompatActivity implements OnMapReadyCallback, ILocationUpdateEventListener {

    private GoogleMap googleMap;
    private Marker markerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_location_on_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.SpecifyLocationOnMapActivity_map);
        mapFragment.getMapAsync(this);

        Button btnApply = findViewById(R.id.SpecifyLocationOnMapActivity_btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyLocation();
            }
        });
    }

    private void applyLocation() {
        Intent data = new Intent();
        data.putExtra("Location", markerPosition.getPosition());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (googleMap == null) {
            return;
        }
        this.googleMap = googleMap;

        onLocationReceived(MyLocationListener.getInstance().getLastLocation());

        try {
            MarkerOptions markerOptions = new MarkerOptions();
            try {
                markerOptions.position(googleMap.getCameraPosition().target);
            }
            catch (NullPointerException ex) {}
            markerPosition = googleMap.addMarker(markerOptions);


        }
        catch (Exception ex) { }
        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                try {
                    markerPosition.setPosition(googleMap.getCameraPosition().target);
                }
                catch (NullPointerException ex) { }
                catch (Exception ex) { }
            }
        });
    }

    @Override
    public void onLocationReceived(Location location) {
        if (location == null) {
            return;
        }

        if (googleMap != null) {
            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

            googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            position,
                            15));

            if (markerPosition != null) {
                markerPosition.setPosition(position);
            }
        }
    }
}
