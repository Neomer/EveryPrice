package com.neomer.everyprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;

import java.util.List;

public class ShopDetailsActivity extends AppCompatActivity {

    private Shop shop;
    ShopDetailsRecyclerViewAdapter shopDetailsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        shop = (Shop) getIntent().getParcelableExtra(Shop.class.getCanonicalName());
        if (shop == null) {
            finish();
        }

        TextView tvName = findViewById(R.id.shopDetails_tvName);
        tvName.setText(shop.getName());

        setupRecyclerView();
        startLoadProducts();

    }

    private void startLoadProducts() {
        WebApiFacade.getInstance().GetShopProducts(shop, new WebApiCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                updateProductList(result);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ShopDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateProductList(List<Product> products) {
        if (shopDetailsRecyclerViewAdapter == null) {
            return;
        }
        shopDetailsRecyclerViewAdapter.setProductList(products);
        shopDetailsRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        shopDetailsRecyclerViewAdapter = new ShopDetailsRecyclerViewAdapter(null, this);

        RecyclerView recyclerView = findViewById(R.id.shopDetails_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopDetailsRecyclerViewAdapter);
    }
}
