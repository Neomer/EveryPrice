package com.neomer.everyprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neomer.everyprice.api.SecurityApi;
import com.neomer.everyprice.api.models.Token;
import com.neomer.everyprice.api.models.UserSignInModel;

import java.io.IOException;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private SecurityApi securityApi;

    private Button btnSignIn;
    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX") // "2018-09-16T13:08:12.7290948+04:00"
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.88.204:8000/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        securityApi = retrofit.create(SecurityApi.class); //Создаем объект, при помощи которого будем выполнять запросы


        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        tvResponse = (TextView) findViewById(R.id.tvResponse);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveNewToken();
            }
        });
    }

    private void receiveNewToken() {
        Call<Token> call = securityApi.GetToken(new UserSignInModel("Admin"));
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token responseBody = response.body();

                tvResponse.setText("Token: " + responseBody.getToken().toString() + "\nToken expiration date: " + responseBody.getTokenExpirationDate().toString());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                tvResponse.setText("Error: " + t.toString());
            }
        });
    }
}
