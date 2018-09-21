package com.neomer.everyprice;

import android.Manifest;
import android.animation.FloatArrayEvaluator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neomer.everyprice.api.SecurityApi;
import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import java.io.IOException;
import java.security.Permission;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    final static int LOCATION_PERMISSION_REQUEST_CODE = 0;

    private RecyclerView recyclerView;
    private LocationManager locationManager;
    private Location currentLocation = null;
    private ShopRecyclerViewAdapter shopRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = (SearchView) findViewById(R.id.searchPrice);
        recyclerView = (RecyclerView) findViewById(R.id.rvNearShops);
        shopRecyclerViewAdapter = new ShopRecyclerViewAdapter(null, MainActivity.this);
        recyclerView.setAdapter(shopRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupFloatingButton();

        requestLocationPermission();
    }

    private void setupFloatingButton() {
        FloatingActionButton floatingActionButton = findViewById(R.id.mainActivity_floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActions();
            }
        });
    }

    private boolean actionsShow = false;

    private void openMainActions() {
        actionsShow = !actionsShow;

        FloatingActionButton fabAddShop = findViewById(R.id.mainActivity_fab_addShop);
        fabAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddShopActivity();
            }
        });

        if (actionsShow) {
            Animation show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.action_fab_show);

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fabAddShop.getLayoutParams();
            layoutParams.rightMargin += (int) (fabAddShop.getWidth() * 1.7);
            layoutParams.bottomMargin += (int) (fabAddShop.getHeight() * 0.25);
            fabAddShop.setLayoutParams(layoutParams);
            fabAddShop.startAnimation(show_fab);
            fabAddShop.setClickable(true);

        } else {
            Animation hide_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.action_fab_hide);

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fabAddShop.getLayoutParams();
            layoutParams.rightMargin -= (int) (fabAddShop.getWidth() * 1.7);
            layoutParams.bottomMargin -= (int) (fabAddShop.getHeight() * 0.25);
            fabAddShop.setLayoutParams(layoutParams);
            fabAddShop.startAnimation(hide_fab);
            fabAddShop.setClickable(true);
        }

    }

    private void moveToAddShopActivity() {
        startActivity(new Intent(this, AddShopActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            if (grantResults.length == 0) {
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
                setupLocationListener();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void loadListOfNearestShops() {
        if (currentLocation == null) {
            return;
        }
        WebApiFacade.getInstance().GetNearestShops(currentLocation, 1000, new WebApiCallback<List<Shop>>() {
            @Override
            public void onSuccess(List<Shop> result) {
                if (result.isEmpty()) {

                } else {
                    shopRecyclerViewAdapter.setShopList(result);
                    shopRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        setupLocationListener();
    }

    private void applyNewLocation(Location location) {
        if (location == null || (currentLocation != null && location.getAccuracy() >= currentLocation.getAccuracy())) {
            return;
        }
        currentLocation = location;
        loadListOfNearestShops();
    }

    private void setupLocationListener() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location.getAccuracy() < 500) {
                    locationManager.removeUpdates(this);
                }
                applyNewLocation(location);
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
        };

        try {
            applyNewLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            applyNewLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }

}
