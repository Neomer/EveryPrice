package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;

/**
 * Расширение для базового типа WebAPI комманд с проверкой наличия токена
 *
 * @param <TCallback> Тип возвращаемого значения
 */
public abstract class AbstractWebApiWithTokenCommand<TCallback> extends AbstractWebApiCommand<TCallback> {

    AbstractWebApiWithTokenCommand(@NonNull IWebApiCallback<TCallback> callback) throws NullPointerException {
        super(callback);
    }

    @Override
    protected boolean beforeExecute() {
        if (WebApiFacade.getInstance().getToken() == null || WebApiFacade.getInstance().getToken().getToken() == null) {
            return false;
        }
        return super.beforeExecute();
    }

}
