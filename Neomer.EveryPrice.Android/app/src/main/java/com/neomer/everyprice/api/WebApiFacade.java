package com.neomer.everyprice.api;

import android.content.pm.SigningInfo;
import android.graphics.PorterDuff;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.Tag;
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

    private boolean checkErrorStatus(int code, ResponseBody errorBody, WebApiCallback callback) {
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

    public void SignIn(UserSignInModel signInModel, final WebApiCallback<Token> callback) {
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

    public void Registration(UserSignInModel signInModel, final WebApiCallback<Token> callback) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

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

    public void GetNearestShops(final Location location, final double distance, final WebApiCallback<List<Shop>> callback) {
        GetNearestShops(location, distance, null, callback, 0);
    }

    public void GetNearestShops(final Location location, final double distance, final UUID tagUid, final WebApiCallback<List<Shop>> callback) {
        GetNearestShops(location, distance, tagUid, callback, 0);
    }

    private void GetNearestShops(final Location location, final double distance, final UUID tagUid, final WebApiCallback<List<Shop>> callback, final int retry) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

        if (token == null) {
            callback.onFailure(new SignInNeededException());
        }

        Call<List<Shop>> call;
        if (tagUid == null) {
            call = securityApi.GetNearShops(token.getToken(), location.getLatitude(), location.getLongitude(), distance);
        } else {
            call = securityApi.GetNearShops(token.getToken(), location.getLatitude(), location.getLongitude(), distance, tagUid);
        }
        call.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                if (checkErrorStatus(response.code(), response.errorBody(), callback))
                {
                    if (retry < WebApiFacade.WEBAPI_RETRY_COUNT) {
                        GetNearestShops(location, distance, tagUid, callback, retry + 1);
                    }
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

    public  void GetShopProducts(Shop shop, final WebApiCallback<List<Product>> callback) {
        GetShopProducts(shop, callback, 0);
    }

    private void GetShopProducts(final Shop shop, final WebApiCallback<List<Product>> callback, final int retry) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

        if (token == null) {
            callback.onFailure(new SignInNeededException());
        }

        Call<List<Product>> call = securityApi.GetShopProducts(token.getToken(), shop.getUid());
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (checkErrorStatus(response.code(), response.errorBody(), callback))
                {
                    if (retry < WebApiFacade.WEBAPI_RETRY_COUNT) {
                        GetShopProducts(shop, callback, retry + 1);
                    }
                    return;
                }

                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }

    public void CreateShop(Shop shop, final WebApiCallback<Shop> callback) {
        CreateShop(shop, callback, 0);
    }

    private void CreateShop(final Shop shop, final WebApiCallback<Shop> callback, final int retry) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

        if (token == null) {
            callback.onFailure(new SignInNeededException());
        }

        Call<Shop> call = securityApi.CreateShop(token.getToken(), shop);
        call.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (checkErrorStatus(response.code(), response.errorBody(), callback))
                {
                    if (retry < WebApiFacade.WEBAPI_RETRY_COUNT) {
                        CreateShop(shop, callback, retry + 1);
                    }
                    return;
                }

                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }

    public void EditShop(Shop shop, final WebApiCallback<Shop> callback) {
        EditShop(shop, callback, 0);
    }

    private void EditShop(final Shop shop, final WebApiCallback<Shop> callback, final int retry) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

        if (shop.getUid() == null) {
            throw new IllegalArgumentException("Shop uid is null!");
        }

        if (token == null) {
            callback.onFailure(new SignInNeededException());
        }

        Call<Shop> call = securityApi.EditShop(token.getToken(), shop.getUid(), shop);
        call.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (checkErrorStatus(response.code(), response.errorBody(), callback))
                {
                    if (retry < WebApiFacade.WEBAPI_RETRY_COUNT) {
                        CreateShop(shop, callback, retry + 1);
                    }
                    return;
                }

                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }

    public void CreateProduct(final Shop shop, final Product product, WebApiCallback<Product> callback) {
        CreateProduct(shop, product, callback, 0);
    }

    private void CreateProduct(final Shop shop, final Product product, final WebApiCallback<Product> callback, final int retry) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

        if (token == null) {
            callback.onFailure(new SignInNeededException());
        }

        Call<Product> call = securityApi.CreateProduct(token.getToken(), shop.getUid(), product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (checkErrorStatus(response.code(), response.errorBody(), callback))
                {
                    if (retry < WebApiFacade.WEBAPI_RETRY_COUNT) {
                        CreateProduct(shop, product, callback, retry + 1);
                    }
                    return;
                }

                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }

    public void TagFastSearch(final String tagPart, WebApiCallback<TagFastSearchViewModel> callback) {
        TagFastSearch(tagPart, callback, 0);
    }

    private void TagFastSearch(final String tagPart, final WebApiCallback<TagFastSearchViewModel> callback, final int retry) {
        if (securityApi == null) {
            callback.onFailure(new Exception("Security API not initialized! Last Error: " + lastError));
            return;
        }

        if (token == null) {
            callback.onFailure(new SignInNeededException());
        }

        Call<TagFastSearchViewModel> call = securityApi.FindTags(token.getToken(), tagPart);
        call.enqueue(new Callback<TagFastSearchViewModel>() {
            @Override
            public void onResponse(Call<TagFastSearchViewModel> call, Response<TagFastSearchViewModel> response) {
                if (checkErrorStatus(response.code(), response.errorBody(), callback))
                {
                    if (retry < WebApiFacade.WEBAPI_RETRY_COUNT) {
                        TagFastSearch(tagPart, callback, retry + 1);
                    }
                    return;
                }

                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<TagFastSearchViewModel> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }


}
