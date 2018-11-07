package com.neomer.everyprice.activities.addshop;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.neomer.everyprice.MyLocationListener;
import com.neomer.everyprice.R;
import com.neomer.everyprice.SpecifyLocationOnMapActivity;
import com.neomer.everyprice.activities.camera.CameraActivity;
import com.neomer.everyprice.activities.security.SecurityActivity;
import com.neomer.everyprice.api.SignInNeededException;
import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.commands.CreateOrEditShopCommand;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.Tag;
import com.neomer.everyprice.api.models.WebApiException;
import com.neomer.everyprice.core.IBeforeExecutionListener;
import com.neomer.everyprice.core.ILocationUpdateEventListener;
import com.neomer.everyprice.core.NumericHelper;

import java.io.IOException;
import java.util.List;

public class AddShopActivity extends AppCompatActivity implements ILocationUpdateEventListener {

    public final static int LOCATION_PERMISSION_REQUEST_CODE = 0;
    public final static int CAMERA_PERMISSION_REQUEST_CODE = 1;

    public final static int REQUEST_LOCATION_CODE = 0;
    public final static int CAMERA_REQUEST = 1;


    public final static String LOCATION_PROVIDER_SAVED = "SavedLocation";

    private LocationManager locationManager;
    private Location currentLocation = null;
    private Geocoder geocoder;

    private Shop shop;

    private CreateOrEditShopCommand createOrEditShopCommand;

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

            EditText txtAddress = findViewById(R.id.addshop_tvAddress);
            if (addressList != null && addressList.size() > 0) {
                txtAddress.setText(addressList.get(0).getAddressLine(0));
            } else {
                txtAddress.setText("");
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
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        MyLocationListener.getInstance().registerEventListener(this);

        createCommands();

        Button btnOpenMap = findViewById(R.id.addshop_btnOpenMap);
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocationOnMap();
            }
        });

        geocoder = new Geocoder(this);

        shop = getIntent().getParcelableExtra(Shop.class.getCanonicalName());

        if (shop != null) {
            EditText txtName = findViewById(R.id.addshop_tvName);
            EditText txtAddress = findViewById(R.id.addshop_tvAddress);
            EditText txtTags = findViewById(R.id.addshop_txtTags);

            txtName.setText(shop.getName());
            txtAddress.setText(shop.getAddress());

            String sTags = "";
            if (shop.getTags() != null) {
                for (Tag t : shop.getTags()) {
                    sTags += t.getValue() + " ";
                }
            }
            txtTags.setText(sTags);

            Location location = new Location(LOCATION_PROVIDER_SAVED);
            location.setLongitude(shop.getLng());
            location.setLatitude(shop.getLat());
            location.setAccuracy(0);
            onLocationReceived(location);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_shop_menu, menu);

        MenuItem cameraItem = menu.findItem(R.id.addshopmenu_action_camera);
        if (cameraItem != null) {
            cameraItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    moveToCameraActivity();
                    return true;
                }
            });
        }

        MenuItem exitItem = menu.findItem(R.id.addshopmenu_action_save);
        if (exitItem != null) {
            exitItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    createOrEditShopCommand.execute();
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (permissions.length == 0) {
                return;
            }
            boolean permission = true;
            for (int res : grantResults) {
                if (res == PackageManager.PERMISSION_DENIED) {
                    permission = false;
                    break;
                }
            }
            if (permission) {
                moveToCameraActivity();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void moveToCameraActivity() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    private void createCommands() {
        createOrEditShopCommand = new CreateOrEditShopCommand(new IWebApiCallback<Shop>() {
            @Override
            public void onSuccess(Shop result) {
                setResult(RESULT_OK, null);
                moveToMainActivity();
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof SignInNeededException) {
                    moveToSecurityActivity();
                } else {
                    String msg = (t instanceof WebApiException) ?
                            ((WebApiException) t).getExceptionMessage() :
                            t.getMessage().isEmpty() ?
                                    "CreateOrEditShopCommand() exception" :
                                    t.getMessage();
                    Toast.makeText(AddShopActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
        createOrEditShopCommand.setOnBeforeExecuteListener(new IBeforeExecutionListener() {
            @Override
            public boolean OnBeforeExecute() {
                if (currentLocation == null) {
                    Toast.makeText(AddShopActivity.this, getResources().getString(R.string.error_location_not_ready), Toast.LENGTH_SHORT).show();
                    return false;
                }

                EditText txtName = findViewById(R.id.addshop_tvName);
                EditText txtAddress = findViewById(R.id.addshop_tvAddress);
                EditText txtTags = findViewById(R.id.addshop_txtTags);

                if (shop == null) {
                    shop = new Shop();
                }
                shop.setName(txtName.getText().toString());
                shop.setAddress(txtAddress.getText().toString());
                shop.setLat(currentLocation.getLatitude());
                shop.setLng(currentLocation.getLongitude());
                shop.setTags(txtTags.getText().toString());

                createOrEditShopCommand.setData(shop);

                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        stopLocationListen();

        super.onPause();
    }

    @Override
    protected void onResume() {
        setupLocationListener();

        super.onResume();
    }

    private void openLocationOnMap() {
        Intent intent = new Intent(this, SpecifyLocationOnMapActivity.class);
        startActivityForResult(intent, REQUEST_LOCATION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (data == null) {
                    return;
                }
                try {
                    LatLng latLng = data.getParcelableExtra("Location");
                    Location location = new Location("");
                    location.setLatitude(latLng.latitude);
                    location.setLongitude(latLng.longitude);
                    location.setAccuracy(0);

                    onLocationReceived(location);
                }
                catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                break;

            case CAMERA_REQUEST:
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                break;
        }
    }

    private void moveToSecurityActivity() {
        startActivity(new Intent(this, SecurityActivity.class));
        finish();
    }

    private void moveToMainActivity() {
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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Toast.makeText(this, getResources().getString(R.string.error_location_service_not_ready), Toast.LENGTH_SHORT).show();
            finish();
        }

        try{
            onLocationReceived(MyLocationListener.getInstance().getLastLocation());
        }
        catch (Exception ex)
        {
            String msg = ex.getMessage().isEmpty() ?
                    "MyLocationListener.getInstance().getLastLocation() exception" :
                    ex.getMessage();
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, MyLocationListener.getInstance());
        }
        catch (Exception ex) {
            String msg = ex.getMessage().isEmpty() ?
                    "requestLocationUpdates(LocationManager.GPS_PROVIDER exception" :
                    ex.getMessage();
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, MyLocationListener.getInstance());
        }
        catch (Exception ex) {
            String msg = ex.getMessage().isEmpty() ?
                    "requestLocationUpdates(LocationManager.NETWORK_PROVIDER exception" :
                    ex.getMessage();
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    private void stopLocationListen() {
        locationManager.removeUpdates(MyLocationListener.getInstance());
        locationManager = null;
    }


}
