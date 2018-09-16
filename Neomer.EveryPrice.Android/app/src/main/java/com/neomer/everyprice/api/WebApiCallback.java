package com.neomer.everyprice.api;

public interface WebApiCallback {

    void onSuccess();

    void onFailure(Throwable t);

}
