package com.neomer.everyprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.neomer.everyprice.api.models.Shop;

public class AddPriceActivity extends AppCompatActivity {

    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);

        shop = (Shop) getIntent().getParcelableExtra(Shop.class.getCanonicalName());
        if (shop == null) {
            finish();
        }

    }
}
