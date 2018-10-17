package com.neomer.everyprice.api;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.TagFastSearchViewModel;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;
import com.neomer.everyprice.api.models.WebApiException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class WebApiFacade {

    public static int WEBAPI_RETRY_COUNT = 10;

    private static WebApiFacade instance;

    private Retrofit retrofit = null;
    private SecurityApi securityApi = null;
    private UserSignInModel signInModel = null;

    private String lastError;

    private Token token;

    private WebApiFacade() {
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX") // "2018-09-16T13:08:12.7290948+04:00"
                    .create();

        }
        catch (NullPointerException ex) {
            lastError = ex.getMessage();
        }
        catch (Exception ex) {
            lastError = ex.getMessage();
        }

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://46.147.157.189:8000/") //Базовая часть адреса
                    //.baseUrl("http://192.168.88.204:8000/") //Базовая часть адреса
                    //.baseUrl("http://192.168.18.48:51479/") //Базовая часть адреса
                    .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                    .build();
        }
        catch (NullPointerException ex) {
            lastError = ex.getMessage();
        }
        catch (Exception ex) {
            lastError = ex.getMessage();
        }

        if (retrofit != null) {
            securityApi = retrofit.create(SecurityApi.class); //Создаем объект, при помощи которого будем выполнять запросы
        }
        token = null;
    }

    public static WebApiFacade getInstance() {
        if (instance == null) {
            instance = new WebApiFacade();
        }
        return instance;
    }

    public SecurityApi getSecurityApi() {
        return securityApi;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    private boolean checkErrorStatus(int code, ResponseBody errorBody, IWebApiCallback callback) {
        if (code != 200) {
            if (callback != null) {
                try {
                    Gson gson = new GsonBuilder().create();
                    try {
                        WebApiException exception = gson.fromJson(errorBody.string(), WebApiException.class);
                        if (exception.is("InvalidTokenException") && signInModel != null) {
                            SignIn(signInModel, null);
                        } else {
                            callback.onFailure(exception);
                        }
                    }
                    catch (Exception ex) {
                        callback.onFailure(new Exception(errorBody.string()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    public void SignIn(UserSignInModel signInModel, final IWebApiCallback<Token> callback) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

        this.signInModel = signInModel;

        Call<Token> call = securityApi.SignIn(signInModel);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (checkErrorStatus(response.code(), response.errorBody(), callback))
                {
                    return;
                }
                token = response.body();
                if (callback != null) {
                    callback.onSuccess(token);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }



}
