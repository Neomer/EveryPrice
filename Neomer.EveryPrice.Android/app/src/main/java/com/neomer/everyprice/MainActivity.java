package com.neomer.everyprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.neomer.everyprice.api.SecurityApi;
import com.neomer.everyprice.api.models.UserSignInModel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private SecurityApi securityApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        securityApi = retrofit.create(SecurityApi.class); //Создаем объект, при помощи которого будем выполнять запросы

        securityApi.GetToken(new UserSignInModel("Admin"));
    }
}
