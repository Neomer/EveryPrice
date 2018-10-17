package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;

import java.util.List;

import retrofit2.Call;

public class GetShopProductsCommand extends AbstractWebApiCommand<List<Product>> {

    private Shop shop;

    public GetShopProductsCommand(@NonNull IWebApiCallback<List<Product>> callback) throws NullPointerException {
        super(callback);
    }

    @Override
    protected boolean beforeExecute() {
        if (shop == null || shop.getUid() == null) {
            return false;
        }
        return true;
    }

    @Override
    protected Call<List<Product>> getCall() {
        return getSecurityApi().GetShopProducts(
                WebApiFacade.getInstance().getToken().getToken(),
                getShop().getUid());
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
