package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;

import retrofit2.Call;

/**
 * WebAPI запрос для создания нового или редактирования существующего товара.
 * На вход передаются данные о магазине и продуктею
 * Если все прошло штатно, то на выходе получаем ту же информацию о продукте только с Uid.
 */
public class CreateOrEditProductCommand extends AbstractWebApiWithTokenAndDataCommand<Product, Product> {

    public CreateOrEditProductCommand(@NonNull IWebApiCallback<Product> callback) throws NullPointerException {
        super(callback);
    }

    private Shop shop;

    @Override
    protected boolean beforeExecute() {
        if (getData() == null || getShop() == null) {
            return false;
        }
        if (getData().getName() == null || getData().getName().length() < 2) {
            return false;
        }
        return super.beforeExecute();
    }

    @Override
    protected Call<Product> getCall() {
        if (getData().getUid() == null) {
            return getSecurityApi().CreateProduct(
                    WebApiFacade.getInstance().getToken().getToken(),
                    getShop().getUid(),
                    getData()
            );
        }
        return null;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
