package com.neomer.everyprice;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.core.IRecyclerAdapterOnBottomReachListener;
import com.neomer.everyprice.core.NumericHelper;

import java.util.List;

public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "ShopRecyclerViewAdapter";

    private IRecyclerAdapterOnBottomReachListener onBottomReachListener = null;
    private List<Shop> shopList;
    private Context context;

    public ShopRecyclerViewAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_listitem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Shop shop = shopList.get(i);
        if (shop != null) {
            viewHolder.getTextViewName().setText(shop.getName());

            String sDistance = "?";
            Location location = MyLocationListener.getInstance().getLastLocation();
            if (location != null) {
                try {
                    Location shopLocation = new Location("");
                    shopLocation.setLatitude(shop.getLat());
                    shopLocation.setLongitude(shop.getLng());

                    double distance = location.distanceTo(shopLocation);
                    sDistance = NumericHelper.getInstance().FormatDistance(distance, viewHolder.getContext().getResources());
                }
                catch (NullPointerException ex) {
                    Toast.makeText(viewHolder.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(viewHolder.getContext(), viewHolder.getContext().getResources().getString(R.string.error_location_not_ready), Toast.LENGTH_SHORT).show();
            }
            viewHolder.getTextViewDistance().setText(sDistance);
        }

        viewHolder.getConstraintLayoutRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShopDetailsActivity(shop);
            }
        });

        if (i == shopList.size() - 1) {
            if (onBottomReachListener != null) {
                onBottomReachListener.OnRecyclerBottomReached(i);
            }
        }
    }

    private void openShopDetailsActivity(Shop shop) {
        Intent intent = new Intent(context, ShopDetailsActivity.class);
        intent.putExtra(Shop.class.getCanonicalName(), shop);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return (shopList != null) ? shopList.size() : 0;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public IRecyclerAdapterOnBottomReachListener getOnBottomReachListener() {
        return onBottomReachListener;
    }

    public void setOnBottomReachListener(IRecyclerAdapterOnBottomReachListener onBottomReachListener) {
        this.onBottomReachListener = onBottomReachListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewDescription;
        private TextView textViewDistance;
        private ConstraintLayout constraintLayoutRoot;
        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.shoprecyclerview_image);
            textViewName = itemView.findViewById(R.id.shoprecyclerview_tvName);
            textViewDescription = itemView.findViewById(R.id.shoprecyclerview_tvDescription);
            textViewDistance = itemView.findViewById(R.id.shoprecyclerview_tvDistance);
            constraintLayoutRoot = itemView.findViewById(R.id.shoprecyclerview_root_layout);
            context = itemView.getContext();
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewDescription() {
            return textViewDescription;
        }

        public ConstraintLayout getConstraintLayoutRoot() {
            return constraintLayoutRoot;
        }

        public TextView getTextViewDistance() {
            return textViewDistance;
        }

        public Context getContext() {
            return context;
        }
    }

}
