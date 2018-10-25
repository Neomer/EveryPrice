package com.neomer.everyprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.SignInNeededException;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.commands.CreateOrEditProductCommand;
import com.neomer.everyprice.api.commands.CreateOrEditShopCommand;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.WebApiException;
import com.neomer.everyprice.core.IBeforeExecuteListener;

public class AddProductActivity extends AppCompatActivity {

    private Shop shop;

    //region UI
    private EditText txtName;
    private EditText txtPrice;
    private CheckBox chkIsDiscount;
    private CreateOrEditProductCommand createOrEditProductCommand;
    //endregion

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        shop = (Shop) getIntent().getParcelableExtra(Shop.class.getCanonicalName());
        if (shop == null) {
            finish();
        }

        product = (Product) getIntent().getParcelableExtra(Product.class.getCanonicalName());
        if (product != null) {
            txtName.setText(product.getName());
        }

        createCommands();

        txtName = findViewById(R.id.addproduct_txtProductName);
        txtPrice = findViewById(R.id.addproduct_txtPrice);
        chkIsDiscount = findViewById(R.id.addproduct_chkIsDiscount);


    }

    private void createCommands() {
        createOrEditProductCommand = new CreateOrEditProductCommand(new IWebApiCallback<Product>() {
            @Override
            public void onSuccess(Product result) {
                creationReady(result);
            }

            @Override
            public void onFailure(Throwable t) {
                String msg = (t instanceof WebApiException) ?
                        ((WebApiException) t).getExceptionMessage() :
                        t.getMessage().isEmpty() ?
                                "CreateOrEditProductCommand() exception" :
                                t.getMessage();
                Toast.makeText(AddProductActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
        createOrEditProductCommand.setOnBeforeExecuteListener(new IBeforeExecuteListener() {
            @Override
            public boolean OnBeforeExecute() {

                if (product == null) {
                    product = new Product(txtName.getText().toString(), Double.valueOf(txtPrice.getText().toString()));
                }
                createOrEditProductCommand.setData(product);

                return true;
            }
        });
        createOrEditProductCommand.applyToViewClick(findViewById(R.id.addproduct_btnSave));
        createOrEditProductCommand.setShop(shop);
    }

    private void creationReady(Product product) {
        setResult(RESULT_OK);
        finish();
    }
}
