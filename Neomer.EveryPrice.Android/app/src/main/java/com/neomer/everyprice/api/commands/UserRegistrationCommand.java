package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import retrofit2.Call;

/**
 * WebAPI запрос для регистрации в системе.
 * На вход передаются данные для регистрации
 * На выходе возвращается токен.
 */
public class UserRegistrationCommand extends AbstractWebApiWithDataCommand<UserSignInModel, Token> {


    public UserRegistrationCommand(@NonNull IWebApiCallback<Token> callback) throws NullPointerException {
        super(callback);
    }

    @Override
    protected boolean beforeExecute() {
        if (getData() == null) {
            return false;
        }
        if (getData().Username == null || getData().Username.length() < 4) {
            return false;
        }
        return true;
    }

    @Override
    protected Call<Token> getCall() {
        if (getData() == null) {
            throw new NullPointerException("Data is null!");
        }
        return getSecurityApi().Registration(getData());
    }
}
