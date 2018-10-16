package com.neomer.everyprice.api;

import android.support.annotation.Nullable;

public interface IWebApiCallback<T> {

    void onSuccess(@Nullable T result);

    void onFailure(Throwable t);

}
