package com.neomer.everyprice.activities.shopdetails;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neomer.everyprice.R;
import com.neomer.everyprice.api.models.Price;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.core.widgets.recyclerview.AbstractRecyclerViewHolder;
import com.neomer.everyprice.core.NumericHelper;

public class ProductListRecyclerViewHolder extends AbstractRecyclerViewHolder<Product> {

    private static final String TAG = "ProductListRecyclerViewHolder";

    private ImageView imageView;
    private TextView textViewName;
    private TextView textViewPrice;
    private ConstraintLayout constraintLayoutRoot;

    public ProductListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void setupViews(@NonNull View itemView) {
        imageView = itemView.findViewById(R.id.shopDetails_RecyclerView_imageView);
        textViewName = itemView.findViewById(R.id.shopDetails_RecyclerView_tvName);
        textViewPrice = itemView.findViewById(R.id.shopDetails_RecyclerView_tvPrice);
        constraintLayoutRoot = itemView.findViewById(R.id.shopDetails_RecyclerView_rootLayout);
    }

    @Override
    protected View getRootContainerView() {
        return constraintLayoutRoot;
    }

    @Override
    protected void bind(Product product) {
        if (product != null) {
            textViewName.setText(product.getName());
            Price price = product.getPrice();
            textViewPrice.setText(
                    price == null ?
                            getContext().getResources().getText(R.string.no_price) :
                            NumericHelper.getInstance().FormatToMoney(price.getValue()));

        }
    }
}
