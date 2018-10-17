package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;

/**
 * Расширение для базового типа WebAPI комманд, для возможности передачи данных в виде модели
 * @param <TData> Тип передаваемых данных
 * @param <TCallbackResult>
 */
public abstract class AbstractWebApiWithDataCommand<TData, TCallbackResult> extends AbstractWebApiCommand<TCallbackResult> {

    private TData data;

    AbstractWebApiWithDataCommand(@NonNull IWebApiCallback<TCallbackResult> callback) throws NullPointerException {
        super(callback);
    }

    public final TData getData() {
        return data;
    }

    public final void setData(TData data) {
        this.data = data;
    }
}
