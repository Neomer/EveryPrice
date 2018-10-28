package com.neomer.everyprice.api;

import com.neomer.everyprice.api.models.Product;
import com.neomer.everyprice.api.models.Shop;
import com.neomer.everyprice.api.models.TagFastSearchViewModel;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SecurityApi {

    // region User controller
    @POST("/api/user")
    Call<Token> SignIn(@Body UserSignInModel signInModel);

    @PUT("/api/user")
    Call<Token> Registration(@Body UserSignInModel signInModel);
    //endregion

    //region Shop controller
    @GET("/api/shop")
    Call<Shop> GetShopDetails(@Header("Token") UUID token, @Query("Id") UUID uid);

    @GET("/api/shop")
    Call<List<Shop>> GetNearShops(@Header("Token") UUID token, @Query("Lat") double latitude, @Query("Lng") double longtitude, @Query("Distance") double distance);

    @GET("/api/shop")
    Call<List<Shop>> GetNearShops(@Header("Token") UUID token, @Query("Lat") double latitude, @Query("Lng") double longtitude, @Query("Distance") double distance, @Query("TagUid") UUID tagUid);

    @PUT("/api/shop")
    Call<Shop> CreateShop(@Header("Token") UUID token, @Body Shop shopModel);

    @POST("/api/shop")
    Call<Shop> EditShop(@Header("Token") UUID token, @Query("Id") UUID id,  @Body Shop shopModel);
    //endregion Shop controller

    //region Product controller
    @GET("/api/product")
    Call<List<Product>> GetShopProducts(@Header("Token") UUID token, @Query("ShopUid") UUID shopUid);

    @PUT("/api/product")
    Call<Product> CreateProduct(@Header("Token") UUID token, @Query("ShopUid") UUID shopUid, @Body Product product);

    @POST("/api/product")
    Call<Product> EditProduct(@Header("Token") UUID token, @Query("Uid") UUID id,  @Body Product productModel);
    //endregion

    //region Tag controller
    @GET("/api/tag")
    Call<TagFastSearchViewModel> FindTags(@Header("Token") UUID token, @Query("part") String tagPart);
    //endregion

}
