package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Shop;

import retrofit2.Call;

/**
 * WebAPI запрос для создания нового или редактирования существующего магазина.
 * На вход передаются данные о магазине.
 * Если все прошло штатно, то на выходе получаем ту же информацию о магазине только с Uid (для нового магазина).
 */
public class CreateOrEditShopCommand extends AbstractWebApiWithDataCommand<Shop, Shop> {

    public CreateOrEditShopCommand(@NonNull IWebApiCallback<Shop> callback) throws NullPointerException {
        super(callback);
    }

    @Override
    protected boolean beforeExecute() {
        if (getData() == null) {
            return false;
        }
        if (getData().getName() == null || getData().getName().length() < 2) {
            return false;
        }
        if (getData().getAddress() == null || getData().getAddress().length() < 5) {
            return false;
        }
        if (getData().getTags() == null || getData().getTags().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    protected Call<Shop> getCall() {
        if (getData().getUid() == null) {
            return getSecurityApi().CreateShop(
                    WebApiFacade.getInstance().getToken().getToken(),
                    getData()
            );
        } else {
            return getSecurityApi().EditShop(
                    WebApiFacade.getInstance().getToken().getToken(),
                    getData().getUid(),
                    getData()
            );
        }
    }
}
