package com.neomer.everyprice.api;

import com.neomer.everyprice.api.models.IUser;
import com.neomer.everyprice.api.models.Price;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SecurityApi {

    @POST("/api/user")
    Call<Token> SignIn(@Body UserSignInModel signInModel);

    @PUT("/api/user")
    Call<Token> Registration(@Body UserSignInModel signInModel);

    @GET("/api/shop")
    Call<Shop> GetShopDetails(@Query("Id") UUID uid);

    @GET("/api/shop")
    Call<List<Shop>> GetNearShops(@Query("Lat") double latitude, @Query("Lng") double longtitude, @Query("Distance") double distance);

    @GET("/api/product")
    Call<List<Price>> GetShopProducts(@Query("ShopUid") UUID shopUid);

}
