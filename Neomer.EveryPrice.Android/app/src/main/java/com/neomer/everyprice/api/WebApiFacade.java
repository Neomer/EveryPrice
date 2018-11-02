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
import com.neomer.everyprice.core.IConfigurationChangeListener;
import com.neomer.everyprice.core.helpers.ConfigurationProvider;

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
        createRetrofit();
        token = null;

        ConfigurationProvider.getInstance().registerConfigurationChangeListener(new IConfigurationChangeListener() {
            @Override
            public void onConfigurationChange() {
                createRetrofit();
            }
        });
    }

    private void createRetrofit() {
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

        String ip = ConfigurationProvider.getInstance().getServerIP();
        if (ip == null || ip.isEmpty()) {
            ip = "94.181.97.208";
        }

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + ip + ":8000/") //Базовая часть адреса
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

}
