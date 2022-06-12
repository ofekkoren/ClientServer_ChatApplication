package com.example.chatapp.login;

import static com.example.chatapp.MyApp.getCookie;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.api.LogInAPI;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.models.ContactToJson;
import com.example.chatapp.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn {
    public static String cookie;
    Retrofit retrofit;
    Retrofit userRetrofit;
    LogInAPI logInAPI;
    UsersAPI usersAPI;
    boolean isValidInfo;

    public LogIn() {
        //Initializing retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        logInAPI = retrofit.create(LogInAPI.class);
        userRetrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usersAPI = userRetrofit.create(UsersAPI.class);

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
                MyApp.setCookie(cookie);
                if (results.username.equals("valid")) {
                    isValidInfo = true;
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

    public void setCurrentUser() {
        Call<User> user = usersAPI.getCurrentUser(getCookie());
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user1 = response.body();
                MyApp.setCurrentUser(user1);
                int x = 5;
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

}