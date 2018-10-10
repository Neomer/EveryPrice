package com.neomer.everyprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.neomer.everyprice.api.WebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.WebApiException;

public class AddProductActivity extends AppCompatActivity {

    private Shop shop;

    //region UI
    private EditText txtName;
    private EditText txtPrice;
    private CheckBox chkIsDiscount;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        shop = (Shop) getIntent().getParcelableExtra(Shop.class.getCanonicalName());
        if (shop == null) {
            finish();
        }

        txtName = findViewById(R.id.addproduct_txtProductName);
        txtPrice = findViewById(R.id.addproduct_txtPrice);
        chkIsDiscount = findViewById(R.id.addproduct_chkIsDiscount);

        Button btnSave = findViewById(R.id.addproduct_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
    }

    private void saveProduct() {
        Product product = new Product(txtName.getText().toString(), Double.valueOf(txtPrice.getText().toString()));

        WebApiFacade.getInstance().CreateProduct(shop, product, new WebApiCallback<Product>() {
            @Override
            public void onSuccess(Product result) {
                creationReady(result);
            }

            @Override
            public void onFailure(Throwable t) {
                String msg = (t instanceof WebApiException) ?
                        ((WebApiException) t).getExceptionMessage() :
                        t.getMessage().isEmpty() ?
                                "TagFastSearch() exception" :
                                t.getMessage();
                Toast.makeText(AddProductActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void creationReady(Product product) {
        setResult(RESULT_OK);
        finish();
    }
}
