package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;

import retrofit2.Call;

/**
 * WebAPI запрос для редактирования продукта
 * На вход передаются данные о продукте.
 * Если все прошло штатно, то на выходе получаем обновленную информацию о продукте
 */
public class EditProductCommand extends AbstractWebApiWithTokenAndDataCommand<Product, Product> {

    public EditProductCommand(@NonNull IWebApiCallback<Product> callback) throws NullPointerException {
        super(callback);
    }

    @Override
    protected boolean beforeExecute() {
        if (getData() == null) {
            return false;
        }
        if (getData().getUid() == null) {
            return false;
        }
        if (getData().getName() == null || getData().getName().length() < 2) {
            return false;
        }
        return super.beforeExecute();
    }

    @Override
    protected Call<Product> getCall() {
        return getSecurityApi().EditProduct(
                WebApiFacade.getInstance().getToken().getToken(),
                getData().getUid(),
                getData()
        );
    }
}
