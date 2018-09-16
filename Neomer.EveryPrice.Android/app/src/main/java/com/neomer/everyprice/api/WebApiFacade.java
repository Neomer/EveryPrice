package com.neomer.everyprice.api;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neomer.everyprice.MainActivity;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class WebApiFacade {

    private static WebApiFacade instance;

    private Retrofit retrofit;
    private SecurityApi securityApi;

    private Token token;

    private WebApiFacade() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX") // "2018-09-16T13:08:12.7290948+04:00"
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.88.204:8000/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        securityApi = retrofit.create(SecurityApi.class); //Создаем объект, при помощи которого будем выполнять запросы

        token = null;
    }

    public static WebApiFacade getInstance() {
        if (instance == null) {
            instance = new WebApiFacade();
        }
        return instance;
    }

    public void SignIn(UserSignInModel signInModel, final WebApiCallback callback) {

        Call<Token> call = securityApi.SignIn(signInModel);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                token = response.body();
                if (callback != null) {
                    callback.onSuccess();
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

    public void Registration(UserSignInModel signInModel, final WebApiCallback callback) {
        Call<Token> call = securityApi.Registration(signInModel);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                token = response.body();
                if (callback != null) {
                    callback.onSuccess();
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
