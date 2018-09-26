package com.neomer.everyprice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.neomer.everyprice.api.SignInNeededException;
import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.core.ILocationUpdateEventListener;
import com.neomer.everyprice.core.IRecyclerAdapterOnBottomReachListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ILocationUpdateEventListener, IRecyclerAdapterOnBottomReachListener {

    final static int LOCATION_PERMISSION_REQUEST_CODE = 0;
    private final static int RESULT_FOR_ADD_SHOP_ACTION = 0;

    @Override
    protected void onPause() {
        stopListenLocation();

        super.onPause();
    }

    @Override
    protected void onResume() {
        requestLocationPermission();

        super.onResume();
    }

    @Override
    public void onLocationReceived(Location location) {
        if (location == null || (currentLocation != null && currentLocation.getAccuracy() < location.getAccuracy())) {
            return;
        }
        currentLocation = location;
        loadListOfNearestShops();
    }

    private RecyclerView recyclerView;
    private LocationManager locationManager;
    private Location currentLocation = null;
    private ShopRecyclerViewAdapter shopRecyclerViewAdapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onDestroy() {
        MyLocationListener.getInstance().unregisterEventListener(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.searchPrice);

        MyLocationListener.getInstance().registerEventListener(this);

        setupRecyclerView();
        setupFloatingButton();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.rvNearShops);

        shopRecyclerViewAdapter = new ShopRecyclerViewAdapter(null, MainActivity.this);
        shopRecyclerViewAdapter.setOnBottomReachListener(this);
        recyclerView.setAdapter(shopRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupFloatingButton() {
        floatingActionButton = findViewById(R.id.mainActivity_floatingActionButton);
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
            layoutParams.rightMargin += floatingActionButton.getWidth();
            layoutParams.bottomMargin += floatingActionButton.getHeight();
            fabAddShop.setLayoutParams(layoutParams);
            fabAddShop.startAnimation(show_fab);
            fabAddShop.setClickable(true);

        } else {
            Animation hide_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.action_fab_hide);

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fabAddShop.getLayoutParams();
            layoutParams.rightMargin -= floatingActionButton.getWidth();
            layoutParams.bottomMargin -= floatingActionButton.getHeight();
            fabAddShop.setLayoutParams(layoutParams);
            fabAddShop.startAnimation(hide_fab);
            fabAddShop.setClickable(true);
        }

    }

    private void moveToAddShopActivity() {
        startActivityForResult(new Intent(this, AddShopActivity.class), RESULT_FOR_ADD_SHOP_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_FOR_ADD_SHOP_ACTION) {
            if (resultCode == RESULT_OK) {
                loadListOfNearestShops();
            }
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
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
        recyclerView.setAdapter(new RecyclerViewUpdateAdapter());

        WebApiFacade.getInstance().GetNearestShops(currentLocation, 1000, new WebApiCallback<List<Shop>>() {
            @Override
            public void onSuccess(List<Shop> result) {
                if (result.isEmpty()) {

                } else {
                    shopRecyclerViewAdapter.setShopList(result);
                    recyclerView.setAdapter(shopRecyclerViewAdapter);
                    shopRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof SignInNeededException) {
                    moveToSecurityActivity();
                } else {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void moveToSecurityActivity() {
        startActivity(new Intent(this, SecurityActivity.class));
        finish();
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

    private void setupLocationListener() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            MyLocationListener.getInstance().onLocationChanged(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            MyLocationListener.getInstance().onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, MyLocationListener.getInstance());
    }

    private void stopListenLocation() {
        locationManager.removeUpdates(MyLocationListener.getInstance());
        locationManager = null;
    }


    @Override
    public void OnRecyclerBottomReached(int position) {
        //loadListOfNearestShops();
    }
}
