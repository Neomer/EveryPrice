package com.neomer.everyprice.api;

import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.common.internal.ResourceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neomer.everyprice.MainActivity;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class WebApiFacade {

    private static WebApiFacade instance;

    private Retrofit retrofit = null;
    private SecurityApi securityApi = null;

    private String lastError;

    private Token token;

    private WebApiFacade() {
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX") // "2018-09-16T13:08:12.7290948+04:00"
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
                    //.baseUrl("http://46.147.174.43:8000/") //Базовая часть адреса
                    .baseUrl("http://192.168.88.204:8000/") //Базовая часть адреса
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

    private boolean checkErrorStatus(int code, ResponseBody errorBody, WebApiCallback callback) {
        if (code != 200) {
            if (callback != null) {
                try {
                    callback.onFailure(new Exception(errorBody.string()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    public void SignIn(UserSignInModel signInModel, final WebApiCallback<Token> callback) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

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

    public void Registration(UserSignInModel signInModel, final WebApiCallback<Token> callback) {
        Call<Token> call = securityApi.Registration(signInModel);
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

    public  void GetNearestShops(Location location, double distance, final WebApiCallback<List<Shop>> callback) {
        Call<List<Shop>> call = securityApi.GetNearShops(location.getLatitude(), location.getLongitude(), distance);
        call.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                if (checkErrorStatus(response.code(), response.errorBody(), callback))
                {
                    return;
                }

                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }
}
