package com.example.chatapp.api;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.chatapp.ContactsList.contactsList;
import com.example.chatapp.MyApp;
import com.example.chatapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn {
    public static String cookie;
    Retrofit retrofit;
    LogInAPI logInAPI;
    boolean isValidInfo;

    public LogIn() {
        //Initializing retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        logInAPI = retrofit.create(LogInAPI.class);
        isValidInfo = false;
    }

    // Checks validation
    public boolean logInCheckValidation(String username, String password, TextView textField) {
        LogInAPI.LogInParams params = new LogInAPI.LogInParams(username, password);
        Call<LogInAPI.LogInResults> call = logInAPI.checkValidation(params);
        call.enqueue(new Callback<LogInAPI.LogInResults>() {
            @Override
            public void onResponse(Call<LogInAPI.LogInResults> call, Response<LogInAPI.LogInResults> response) {
                LogInAPI.LogInResults results = response.body();
                cookie = response.headers().get("Set-Cookie");
                if (results.username.equals("valid")) {
                    isValidInfo = true;
//                    Intent i = new Intent(this, contactsList.class);
//                    startActivity(i);
                } else if (results.username.equals("empty")) {
                    textField.setText("All fields must be filled!");
                    textField.setVisibility(View.VISIBLE);
                    textField.setTextColor(Color.parseColor("red"));
                } else if (results.username.equals("invalid")) {
                    textField.setText("Wrong username or password!");
                    textField.setVisibility(View.VISIBLE);
                    textField.setTextColor(Color.parseColor("red"));
                }
            }

            @Override
            public void onFailure(Call<LogInAPI.LogInResults> call, Throwable t) {
            }
        });
        return isValidInfo;
    }

}