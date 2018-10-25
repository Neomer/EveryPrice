package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import retrofit2.Call;

/**
 * WebAPI запрос для авторизации в системе.
 * На вход передаются данные для авторизации
 * На выходе возвращается токен.
 */
public class SignInCommand extends AbstractWebApiWithDataCommand<UserSignInModel, Token> {

    public SignInCommand(UserSignInModel signInModel, @NonNull IWebApiCallback<Token> callback) throws NullPointerException {
        super(callback);
        setData(signInModel);
    }

    public SignInCommand(@NonNull IWebApiCallback<Token> callback) throws NullPointerException {
        super(callback);
    }

    @Override
    protected void beforeSuccessCallback(@Nullable Token token) {
        WebApiFacade.getInstance().setToken(token);
    }

    @Override
    protected boolean beforeExecute() {
        if (getData() == null) {
            return false;
        }
        if (getData().Username == null || getData().Username.length() < 4) {
            return false;
        }
        return super.beforeExecute();
    }

    @Override
    protected Call<Token> getCall() {
        if (getData() == null) {
            throw new NullPointerException("Data is null!");
        }
        return getSecurityApi().SignIn(getData());
    }

}
