package com.neomer.everyprice.activities.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.neomer.everyprice.AddShopActivity;
import com.neomer.everyprice.MyLocationListener;
import com.neomer.everyprice.R;
import com.neomer.everyprice.SearchViewTagSuggestionAdapter;
import com.neomer.everyprice.SecurityActivity;
import com.neomer.everyprice.activities.settings.ApplicationSettingsActivity;
import com.neomer.everyprice.activities.shopdetails.ShopDetailsActivity;
import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.SignInNeededException;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.commands.GetNearShopsCommand;
import com.neomer.everyprice.api.commands.TagsSuggestionsCommand;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.Tag;
import com.neomer.everyprice.api.models.TagFastSearchViewModel;
import com.neomer.everyprice.api.models.TagViewModel;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.core.AbstractRecycleViewAdatper;
import com.neomer.everyprice.core.BaseRecyclerViewAdapter;
import com.neomer.everyprice.core.GeoLocation;
import com.neomer.everyprice.core.IBeforeExecutionListener;
import com.neomer.everyprice.core.ILocationUpdateEventListener;
import com.neomer.everyprice.core.IRecyclerAdapterOnBottomReachListener;
import com.neomer.everyprice.core.IRecyclerViewElementClickListener;
import com.neomer.everyprice.core.helpers.ConfigurationProvider;
import com.neomer.everyprice.core.helpers.SecurityHelper;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ILocationUpdateEventListener, IRecyclerAdapterOnBottomReachListener {

    public final static String TAG = "MainActivity";

    private final static int RESULT_FOR_ADD_SHOP_ACTION = 0;
    private final static int RESULT_FOR_APPLICATION_SETTINGS_ACTIVITY = 1;

    final static int LOCATION_PERMISSION_REQUEST_CODE = 0;
    private SearchViewTagSuggestionAdapter searchViewTagSuggestionAdapter;
    private SearchView searchView;

    @Override
    public void onLocationReceived(Location location) {
        if (location == null) {
            return;
        }
        currentLocation = location;
        loadListOfNearestShops();
    }

    private ShopRecyclerView recyclerView;

    private LocationManager locationManager;
    private Location currentLocation = null;
    private FloatingActionButton floatingActionButton;
    private Tag selectedTag;

    /**
     * Команда для получения списка ближайших магазинов
     */
    private GetNearShopsCommand nearShopsCommand;
    /**
     * Команда для получения списка тэгов
     */
    private TagsSuggestionsCommand tagsSuggestionsCommand;

    //region Activity overridden methods
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!loadSavedToken()) {
            return;
        }

        setupRecyclerView();
        setupFloatingButton();

        createCommands();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        MenuItem itemSearch = menu.findItem(R.id.mainmenu_action_search);
        if (itemSearch != null) {
            searchView = (SearchView) itemSearch.getActionView();
            setupFastSearch();
        }

        MenuItem itemSettings = menu.findItem(R.id.mainMenu_Setting);
        if (itemSettings != null) {
            itemSettings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    moveToSettingsActivity();
                    return true;
                }
            });
        }

        MenuItem itemLogout = menu.findItem(R.id.mainMenu_Logout);
        if (itemLogout != null) {
            itemLogout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    SecurityHelper.ClearSavedToken(MainActivity.this);
                    moveToSecurityActivity();
                    return true;
                }
            });
        }
        return true;
    }

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case RESULT_FOR_ADD_SHOP_ACTION:
                if (resultCode == RESULT_OK) {
                    loadListOfNearestShops();
                }
                return;

            case RESULT_FOR_APPLICATION_SETTINGS_ACTIVITY:
                ConfigurationProvider.getInstance().Load(MainActivity.this);
                break;
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


    //endregion

    private boolean loadSavedToken() {
        SharedPreferences preferences = getSharedPreferences(SecurityHelper.APP_PREFERENCES, Context.MODE_PRIVATE);
        String sToken = preferences.getString(SecurityHelper.APP_PREFERENCES_TOKEN, null);
        if (sToken == null || sToken.isEmpty()) {
            moveToSecurityActivity();
            return false;
        }
        Token token = new Token();
        token.setToken(UUID.fromString(sToken));
        WebApiFacade.getInstance().setToken(token);
        return true;
    }

    private void createCommands() {

        //region GetNearShopsCommand - Команда для получения списка ближайших магазинов
        final GetNearShopsCommand nearShopsCommand = new GetNearShopsCommand(new IWebApiCallback<List<Shop>>() {
            @Override
            public void onSuccess(List<Shop> result) {
                if (result == null) {
                    return;
                }

                if (!result.isEmpty()) {
                    if (recyclerView == null || recyclerView.getAdapter() == null) {
                        return;
                    }
                    recyclerView.getAdapter().setModel(result);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof SignInNeededException) {
                    moveToSecurityActivity();
                } else {
                    String msg = t.getMessage().isEmpty() ?
                                    "GetNearShopsCommand() exception" :
                                    t.getMessage();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, t.getLocalizedMessage());
                }
            }
        });
        nearShopsCommand.setDistance(1000);
        nearShopsCommand.setOnBeforeExecuteListener(new IBeforeExecutionListener() {
            @Override
            public boolean OnBeforeExecute() {
                nearShopsCommand.setLocation(new GeoLocation(currentLocation));
                return true;
            }
        });

        recyclerView.setUpdateCommand(nearShopsCommand);
        findViewById(R.id.MainActivity_btnRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.update();
            }
        });
        //endregion

        //region TagsSuggestionsCommand - Команда для получения списка тэгов
        tagsSuggestionsCommand = new TagsSuggestionsCommand(new IWebApiCallback<TagFastSearchViewModel>() {
            @Override
            public void onSuccess(TagFastSearchViewModel result) {
                if (result != null) {
                    searchViewTagSuggestionAdapter.applySuggestions(result.getTags());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof SignInNeededException) {
                    moveToSecurityActivity();
                } else {
                    String msg = t.getMessage().isEmpty() ?
                                    "tagsSuggestionsCommand() exception" :
                                    t.getMessage();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, t.getLocalizedMessage());
                }
            }
        });

        //endregion
    }

    private void setupFastSearch() {
        if (searchView == null) {
            return;
        }

        searchViewTagSuggestionAdapter = new SearchViewTagSuggestionAdapter(
                this,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        //searchView.setSuggestionsAdapter(searchViewTagSuggestionAdapter);
        searchView.setSuggestionsAdapter(searchViewTagSuggestionAdapter);
        searchView.setIconified(false);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                TagViewModel tag = searchViewTagSuggestionAdapter.getTags().get(position);
                if (tag != null) {
                    searchView.setQuery(tag.getValue(), false);
                    selectedTag = tag.toTag();
                    loadListOfNearestShops();
                }
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (newText == null) {
                    return false;
                }
                selectedTag = null;

                if (newText.isEmpty()) {
                    loadListOfNearestShops();
                } else {
                    tagsSuggestionsCommand.setTagPart(newText);
                    tagsSuggestionsCommand.execute();
                }

                return false;
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.rvNearShops);

        AbstractRecycleViewAdatper<Shop> shopRecyclerViewAdapter = new BaseRecyclerViewAdapter<>(ShopsListViewHolder.class, R.layout.mainactivity_recyclerview_listitem);
        shopRecyclerViewAdapter.setOnElementClickListener(new IRecyclerViewElementClickListener<Shop>() {
            @Override
            public void OnClick(@Nullable Shop shop) {
                moveToShopDetailsActivity(shop);
            }
        });
        recyclerView.setAdapter(shopRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void moveToShopDetailsActivity(Shop shop) {
        Intent intent = new Intent(MainActivity.this, ShopDetailsActivity.class);
        intent.putExtra(Shop.class.getCanonicalName(), shop);
        startActivity(intent);
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

    private void moveToSettingsActivity() {
        startActivityForResult(new Intent(MainActivity.this, ApplicationSettingsActivity.class), RESULT_FOR_APPLICATION_SETTINGS_ACTIVITY);
    }

    private void moveToAddShopActivity() {
        startActivityForResult(new Intent(this, AddShopActivity.class), RESULT_FOR_ADD_SHOP_ACTION);
    }

    private void loadListOfNearestShops() {
        ((GetNearShopsCommand)recyclerView.getUpdateCommand()).setTag(selectedTag);
        recyclerView.update();
    }

    private void moveToSecurityActivity() {
        startActivity(new Intent(this, SecurityActivity.class));
        finish();
    }

    private void requestLocationPermission() {
        setupLocationListener();
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        MyLocationListener.getInstance().registerEventListener(this);

        try {
            MyLocationListener.getInstance().onLocationChanged(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }
        catch (Exception ex) {
            String msg = ex.getMessage().isEmpty() ?
                            "getLastKnownLocation(LocationManager.NETWORK_PROVIDER) exception" :
                            ex.getMessage();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        }

        try {
            MyLocationListener.getInstance().onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
        catch (Exception ex) {
            String msg = ex.getMessage().isEmpty() ?
                    "getLastKnownLocation(LocationManager.GPS_PROVIDER) exception" :
                    ex.getMessage();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, MyLocationListener.getInstance());

    }

    private void stopListenLocation() {
        MyLocationListener.getInstance().unregisterEventListener(this);

        if (locationManager != null) {
            locationManager.removeUpdates(MyLocationListener.getInstance());
            locationManager = null;
        }
    }


    @Override
    public void OnRecyclerBottomReached(int position) {

    }
}
