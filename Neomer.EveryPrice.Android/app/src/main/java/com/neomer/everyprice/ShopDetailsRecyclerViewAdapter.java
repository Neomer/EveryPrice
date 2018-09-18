package com.neomer.everyprice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neomer.everyprice.api.models.Price;

import java.util.List;

public class ShopDetailsRecyclerViewAdapter extends RecyclerView.Adapter<ShopDetailsRecyclerViewAdapter.ViewHolder> {

    private List<Price> priceList;
    private Context context;

    public ShopDetailsRecyclerViewAdapter(List<Price> priceList, Context context) {
        this.priceList = priceList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopdetails_recyclerview_listitem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return priceList != null ? priceList.size() :  0;
    }

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewPrice;
        private ConstraintLayout constraintLayoutRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.shopDetails_RecyclerView_imageView);
            textViewName = itemView.findViewById(R.id.shopDetails_RecyclerView_tvName);
            textViewPrice = itemView.findViewById(R.id.shopDetails_RecyclerView_tvPrice);
            constraintLayoutRoot = itemView.findViewById(R.id.shopDetails_RecyclerView_rootLayout);
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

        public TextView getTextViewPrice() {
            return textViewPrice;
        }

        public void setTextViewPrice(TextView textViewPrice) {
            this.textViewPrice = textViewPrice;
        }

        public ConstraintLayout getConstraintLayoutRoot() {
            return constraintLayoutRoot;
        }

        public void setConstraintLayoutRoot(ConstraintLayout constraintLayoutRoot) {
            this.constraintLayoutRoot = constraintLayoutRoot;
        }
    }

}
