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

import com.neomer.everyprice.api.models.Price;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.core.NumericHelper;

import java.util.List;

public class ShopDetailsRecyclerViewAdapter extends RecyclerView.Adapter<ShopDetailsRecyclerViewAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;
    private Shop mShop;

    public ShopDetailsRecyclerViewAdapter(Shop shop, List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        mShop = shop;
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
        final Product product = productList.get(i);
        if (product != null) {
            viewHolder.getTextViewName().setText(product.getName());
            Price price = product.getPrice();
            viewHolder.getTextViewPrice().setText(
                    price == null ?
                            viewHolder.getContext().getResources().getText(R.string.no_price) :
                            NumericHelper.getInstance().FormatToMoney(price.getValue()));

        }
        viewHolder.getConstraintLayoutRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetailsActivity(product);
            }
        });

    }

    private void openProductDetailsActivity(Product product) {
        Intent intent = new Intent(context, AddProductActivity.class);
        intent.putExtra(Shop.class.getCanonicalName(), mShop);
        intent.putExtra(Product.class.getCanonicalName(), product);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() :  0;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewPrice;
        private ConstraintLayout constraintLayoutRoot;
        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.shopDetails_RecyclerView_imageView);
            textViewName = itemView.findViewById(R.id.shopDetails_RecyclerView_tvName);
            textViewPrice = itemView.findViewById(R.id.shopDetails_RecyclerView_tvPrice);
            constraintLayoutRoot = itemView.findViewById(R.id.shopDetails_RecyclerView_rootLayout);
            context = itemView.getContext();
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

        public Context getContext() {
            return context;
        }
    }

}
