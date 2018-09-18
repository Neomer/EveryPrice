package com.neomer.everyprice;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "ShopRecyclerViewAdapter";

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
        }

        viewHolder.getConstraintLayoutRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShopDetailsActivity(shop);
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewDescription;
        private ConstraintLayout constraintLayoutRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.shoprecyclerview_image);
            textViewName = itemView.findViewById(R.id.shoprecyclerview_tvName);
            textViewDescription = itemView.findViewById(R.id.shoprecyclerview_tvDescription);
            constraintLayoutRoot = itemView.findViewById(R.id.shoprecyclerview_root_layout);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public void setTextViewName(TextView textViewName) {
            this.textViewName = textViewName;
        }

        public TextView getTextViewDescription() {
            return textViewDescription;
        }

        public void setTextViewDescription(TextView textViewDescription) {
            this.textViewDescription = textViewDescription;
        }

        public ConstraintLayout getConstraintLayoutRoot() {
            return constraintLayoutRoot;
        }

        public void setConstraintLayoutRoot(ConstraintLayout constraintLayoutRoot) {
            this.constraintLayoutRoot = constraintLayoutRoot;
        }
    }

}
