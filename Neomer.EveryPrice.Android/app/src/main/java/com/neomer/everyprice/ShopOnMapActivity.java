package com.neomer.everyprice;

import android.location.Location;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.core.ILocationUpdateEventListener;
import com.neomer.everyprice.core.helpers.ImageHelper;

public class ShopOnMapActivity extends AppCompatActivity implements OnMapReadyCallback, ILocationUpdateEventListener {

    private GoogleMap googleMap;
    private Marker markerPosition;

    private Shop[] shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_on_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.ShopOnMapActivity_map);
        mapFragment.getMapAsync(this);


        Parcelable[] parcelable = getIntent().getParcelableArrayExtra(Shop.class.getCanonicalName());
        if (parcelable != null) {
            shops = new Shop[parcelable.length];
            int i = 0;
            for (Parcelable p : parcelable) {
                shops[i++] = (Shop) p;
            }
        }
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
                markerOptions.draggable(false);
            }
            catch (NullPointerException ex) {}
            markerPosition = googleMap.addMarker(markerOptions);


        }
        catch (Exception ex) { }

        if (shops != null && shops.length > 0) {
            for(Shop s : shops) {
                MarkerOptions markerOptions = new MarkerOptions();
                try {
                    markerOptions.position(new LatLng(s.getLat(), s.getLng()));
                    markerOptions.draggable(false);
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ImageHelper.VectorToBitmap(ShopOnMapActivity.this, R.drawable.ic_shopping_cart_white_24dp)));
                    googleMap.addMarker(markerOptions);
                }
                catch (NullPointerException e) {}
            }
        }

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
