package com.neomer.everyprice;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.WebApiException;

import java.util.List;

public class ShopDetailsActivity extends AppCompatActivity {

    public final static int RESULT_FOR_ADD_PRICE = 0;

    private Shop shop;
    private ShopDetailsRecyclerViewAdapter shopDetailsRecyclerViewAdapter;
    private FloatingActionButton floatingActionButton;
    private boolean actionsShow;

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

        floatingActionButton = findViewById(R.id.shopDetails_floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActions();
            }
        });

        setupRecyclerView();
        startLoadProducts();

    }

    private void showActions() {
        actionsShow = !actionsShow;

        FloatingActionButton fabAddPrice = findViewById(R.id.shopDetails_addPrice);
        fabAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddProductActivity();
            }
        });

        FloatingActionButton fabEditInfo = findViewById(R.id.shopDetails_editShopInformation);
        fabEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToEditInfoActivity();
            }
        });

        if (actionsShow) {
            Animation show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.action_fab_show);

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fabAddPrice.getLayoutParams();
            layoutParams.rightMargin += (int) (floatingActionButton.getWidth());
            layoutParams.bottomMargin += (int) (floatingActionButton.getHeight());
            fabAddPrice.setLayoutParams(layoutParams);
            fabAddPrice.startAnimation(show_fab);
            fabAddPrice.setClickable(true);

            layoutParams = (ConstraintLayout.LayoutParams) fabEditInfo.getLayoutParams();
            layoutParams.rightMargin += (int) (1.33 * floatingActionButton.getWidth());
            layoutParams.bottomMargin -= (int) (0.12 * floatingActionButton.getHeight());
            fabEditInfo.setLayoutParams(layoutParams);
            fabEditInfo.startAnimation(show_fab);
            fabEditInfo.setClickable(true);

        } else {
            Animation hide_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.action_fab_hide);

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fabAddPrice.getLayoutParams();
            layoutParams.rightMargin -= (int) (floatingActionButton.getWidth());
            layoutParams.bottomMargin -= (int) (floatingActionButton.getHeight());
            fabAddPrice.setLayoutParams(layoutParams);
            fabAddPrice.startAnimation(hide_fab);
            fabAddPrice.setClickable(false);

            layoutParams = (ConstraintLayout.LayoutParams) fabEditInfo.getLayoutParams();
            layoutParams.rightMargin -= (int) (1.33 * floatingActionButton.getWidth());
            layoutParams.bottomMargin += (int) (0.12 * floatingActionButton.getHeight());
            fabEditInfo.setLayoutParams(layoutParams);
            fabEditInfo.startAnimation(hide_fab);
            fabEditInfo.setClickable(false);
        }
        
    }

    private void moveToEditInfoActivity() {
        Intent intent = new Intent(this, AddShopActivity.class);
        intent.putExtra(Shop.class.getCanonicalName(), shop);
        startActivityForResult(intent, RESULT_FOR_ADD_PRICE);
    }

    private void moveToAddProductActivity() {
        Intent intent = new Intent(this, AddProductActivity.class);
        intent.putExtra(Shop.class.getCanonicalName(), shop);
        startActivityForResult(intent, RESULT_FOR_ADD_PRICE);
    }

    private void startLoadProducts() {
        WebApiFacade.getInstance().GetShopProducts(shop, new WebApiCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                updateProductList(result);
            }

            @Override
            public void onFailure(Throwable t) {
                String msg = (t instanceof WebApiException) ?
                        ((WebApiException) t).getExceptionMessage() :
                        t.getMessage().isEmpty() ?
                                "TagFastSearch() exception" :
                                t.getMessage();
                Toast.makeText(ShopDetailsActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_FOR_ADD_PRICE) {
            if (resultCode == RESULT_OK) {
                startLoadProducts();
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateProductList(List<Product> products) {
        if (shopDetailsRecyclerViewAdapter == null) {
            return;
        }
        if (products == null || products.size() == 0) {
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
