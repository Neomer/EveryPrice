package com.neomer.everyprice;

import com.neomer.everyprice.api.models.Token;

public final class AppContext {

    private static AppContext instance;

    private AppContext() {
        token = null;
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
