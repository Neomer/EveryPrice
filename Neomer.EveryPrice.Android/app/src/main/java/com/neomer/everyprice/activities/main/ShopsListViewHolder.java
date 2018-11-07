package com.neomer.everyprice.activities.main;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neomer.everyprice.MyLocationListener;
import com.neomer.everyprice.R;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.core.widgets.recyclerview.AbstractRecyclerViewHolder;
import com.neomer.everyprice.core.NumericHelper;

public class ShopsListViewHolder extends AbstractRecyclerViewHolder<Shop> {

    private static final String TAG = "ShopsListViewHolder";

    private ImageView imageView;
    private TextView textViewName;
    private TextView textViewDescription;
    private TextView textViewDistance;
    private ConstraintLayout constraintLayoutRoot;

    public ShopsListViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void setupViews(@NonNull View itemView) {

        imageView = itemView.findViewById(R.id.shoprecyclerview_image);
        textViewName = itemView.findViewById(R.id.shoprecyclerview_tvName);
        textViewDescription = itemView.findViewById(R.id.shoprecyclerview_tvDescription);
        textViewDistance = itemView.findViewById(R.id.shoprecyclerview_tvDistance);
        constraintLayoutRoot = itemView.findViewById(R.id.shoprecyclerview_root_layout);
    }

    @Override
    protected View getRootContainerView() {
        return constraintLayoutRoot;
    }

    @Override
    protected void bind(Shop shop) {
        if (shop != null) {
            textViewName.setText(shop.getName());

            String sDistance = "?";
            Location location = MyLocationListener.getInstance().getLastLocation();
            if (location != null) {
                try {
                    Location shopLocation = new Location("");
                    shopLocation.setLatitude(shop.getLat());
                    shopLocation.setLongitude(shop.getLng());

                    double distance = location.distanceTo(shopLocation);
                    sDistance = NumericHelper.getInstance().FormatDistance(distance, getContext().getResources());
                } catch (NullPointerException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            } else {
                Log.d(TAG, getContext().getResources().getString(R.string.error_location_not_ready));
            }
            textViewDistance.setText(sDistance);
        }
    }
}
