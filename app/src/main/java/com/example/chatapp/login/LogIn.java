package com.example.chatapp.login;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.api.LogInAPI;
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
    LogInAPI logInAPI;
    boolean isValidInfo;

    public LogIn() {
        //Initializing retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getContext().getString(R.string.BaseUrl))
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
        Call<>

//        Call<ArrayList<ContactToJson>> y = logInAPI.getContacts(MyApp.getCookie());
//        y.enqueue(new Callback<ArrayList<ContactToJson>>() {
//            @Override
//            public void onResponse(Call<ArrayList<ContactToJson>> call, Response<ArrayList<ContactToJson>> response) {
//                ArrayList<ContactToJson> M = response.body();
//                Log.d("dfdf", "Dsfsdsf");
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<ContactToJson>> call, Throwable t) {
//                Log.d("hgj", "ghjjjj");
//
//            }
//        });
    }

}