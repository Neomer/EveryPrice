package com.neomer.everyprice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.core.NumericHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddShopActivity extends AppCompatActivity implements ILocationUpdateEventListener {

    public final static int LOCATION_PERMISSION_REQUEST_CODE = 0;
    public final static int REQUEST_LOCATION_CODE = 0;


    private Location currentLocation = null;
    private Geocoder geocoder;

    @Override
    public void onLocationReceived(Location location) {
        if (location == null || (currentLocation != null && currentLocation.getAccuracy() < location.getAccuracy())) {
            return;
        }
        currentLocation = location;
        displayLocation();

        try {
            List<Address> addressList = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);

            if (addressList != null && addressList.size() > 0) {
                EditText txtAddress = findViewById(R.id.addshop_tvAddress);
                txtAddress.setText(addressList.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayLocation() {
        if (currentLocation == null) {
            return;
        }

        TextView tvLatitude = findViewById(R.id.tvLatitude);
        TextView tvLongitude = findViewById(R.id.tvLongitude);

        try {
            tvLatitude.setText(NumericHelper.getInstance().FormatLocation(currentLocation.getLatitude()));
            tvLongitude.setText(String.valueOf(NumericHelper.getInstance().FormatLocation(currentLocation.getLongitude())));
        }
        catch (NullPointerException ex) { }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        MyLocationListener.getInstance().registerEventListener(this);

        Button btnSave = findViewById(R.id.addshop_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveShop();
            }
        });

        Button btnOpenMap = findViewById(R.id.addshop_btnOpenMap);
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocationOnMap();
            }
        });

        geocoder = new Geocoder(this);

        setupLocationListener();
    }

    private void openLocationOnMap() {
        Intent intent = new Intent(this, SpecifyLocationOnMapActivity.class);
        startActivityForResult(intent, REQUEST_LOCATION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_LOCATION_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    LatLng latLng = data.getParcelableExtra("Location");
                    Location location = new Location("");
                    location.setLatitude(latLng.latitude);
                    location.setLongitude(latLng.longitude);
                    location.setAccuracy(0);

                    onLocationReceived(location);
                }
                catch (NullPointerException ex) { }
            }
        }
    }

    private void saveShop() {
        if (currentLocation == null) {
            Toast.makeText(this, getResources().getString(R.string.error_location_not_ready), Toast.LENGTH_SHORT).show();
            return;
        }

        EditText txtName = findViewById(R.id.addshop_tvName);
        EditText txtAddress = findViewById(R.id.addshop_tvAddress);

        Shop shop = new Shop(txtName.getText().toString(),
                txtAddress.getText().toString(),
                currentLocation.getLatitude(),
                currentLocation.getLongitude());

        WebApiFacade.getInstance().CreateShop(shop, new WebApiCallback<Shop>() {
            @Override
            public void onSuccess(Shop result) {
                moveToMainActivity();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(AddShopActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void moveToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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

        try{
            onLocationReceived(MyLocationListener.getInstance().getLastLocation());
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, MyLocationListener.getInstance());
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, MyLocationListener.getInstance());
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
