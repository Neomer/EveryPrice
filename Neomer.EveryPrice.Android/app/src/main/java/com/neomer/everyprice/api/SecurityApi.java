package com.neomer.everyprice.api;

import com.neomer.everyprice.api.models.IUser;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SecurityApi {

    @POST("/api/user")
    Call<Token> GetToken(@Body UserSignInModel signInModel);

}
