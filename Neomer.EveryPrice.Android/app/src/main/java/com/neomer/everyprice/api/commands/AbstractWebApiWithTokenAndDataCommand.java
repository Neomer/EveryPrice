package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;

/**
 * Расширение для базового типа WebAPI комманд с проверкой наличия токена
 *
 * @param <TData> Тип передаваемых данных
 * @param <TCallbackResult> Тип возвращаемого значения
 */
public abstract class AbstractWebApiWithTokenAndDataCommand<TData, TCallbackResult> extends AbstractWebApiWithDataCommand<TData, TCallbackResult> {
    AbstractWebApiWithTokenAndDataCommand(@NonNull IWebApiCallback<TCallbackResult> callback) throws NullPointerException {
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
