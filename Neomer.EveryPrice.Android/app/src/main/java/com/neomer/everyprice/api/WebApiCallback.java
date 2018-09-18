package com.neomer.everyprice.api;

public interface WebApiCallback<T> {

    void onSuccess(T result);

    void onFailure(Throwable t);

}
