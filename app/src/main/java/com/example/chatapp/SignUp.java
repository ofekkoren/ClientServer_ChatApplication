package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
    Retrofit retrofit;
    SignUpAPI signUpAPI;

    public SignUp() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getContext().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        signUpAPI = retrofit.create(SignUpAPI.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Button signUpBtn = findViewById(R.id.submitSignUp);
        signUpBtn.setOnClickListener(view -> {
            EditText username = findViewById(R.id.Username);
            EditText nickname = findViewById(R.id.Nickname);
            EditText password = findViewById(R.id.Password);
            EditText repeatpassword = findViewById(R.id.RepeatPassword);
            username.setOnFocusChangeListener((v,b)-> {
                username.setTextColor(getResources().getColor(R.color.black));
            });
            nickname.setOnFocusChangeListener((v,b)-> {
                nickname.setTextColor(getResources().getColor(R.color.black));
            });
            password.setOnFocusChangeListener((v,b)-> {
                password.setTextColor(getResources().getColor(R.color.black));
            });
            repeatpassword.setOnFocusChangeListener((v,b)-> {
                repeatpassword.setTextColor(getResources().getColor(R.color.black));
            });
            SignUpAPI.signUpParams params = new SignUpAPI.signUpParams(username.getText().toString(), nickname.getText().toString(),
                    password.getText().toString(), repeatpassword.getText().toString());
            Call<SignUpAPI.signUpResults> call = signUpAPI.signUp(params);
            call.enqueue(new Callback<SignUpAPI.signUpResults>() {
                @Override
                public void onResponse(Call<SignUpAPI.signUpResults> call, Response<SignUpAPI.signUpResults> response) {
                    SignUpAPI.signUpResults res = response.body();
                    if (validateSignUP(res.usernameV, res.nicknameV, res.passwordV, res.repeatPasswordV)) {
                        Log.d("sadsa","Sadsa");
                        /*var newUser = await getUser(newUserName.toString());
                        setUser(newUser);
                        navigate("../chatScreen");*/
                        /*Call <ArrayList<SignUpAPI.ContactToJson>> y= signUpAPI.getContacts(response.headers().get("Set-Cookie"));
                        y.enqueue(new Callback<ArrayList<SignUpAPI.ContactToJson>>() {
                            @Override
                            public void onResponse(Call<ArrayList<SignUpAPI.ContactToJson>> call, Response<ArrayList<SignUpAPI.ContactToJson>> response) {
                                ArrayList<SignUpAPI.ContactToJson> M= response.body();
                                Log.d("dfdf","Dsfsdsf");
                            }

                            @Override
                            public void onFailure(Call<ArrayList<SignUpAPI.ContactToJson>> call, Throwable t) {
                                Log.d("hgj","ghjjjj");

                            }
                        });*/
                    }
                }

                @Override
                public void onFailure(Call<SignUpAPI.signUpResults> call, Throwable t) {
                }
            });
        });
    }

    private boolean validateSignUP(String usernameV, String nicknameV, String passwordV, String repeatPasswordV) {
        final String valid = "valid";
        boolean isValid = true;
        EditText usernameField = findViewById(R.id.Username);
        EditText nicknameField = findViewById(R.id.Nickname);
        EditText passwordField = findViewById(R.id.Password);
        EditText repeatPasswordField = findViewById(R.id.RepeatPassword);
        if (usernameV.equals(valid))
            usernameField.setTextColor(getResources().getColor(R.color.green));
        else {
            usernameField.setTextColor(getResources().getColor(R.color.red));
            usernameField.setError(usernameV);
            isValid = false;
        }
        if (nicknameV.equals(valid))
            nicknameField.setTextColor(getResources().getColor(R.color.green));
        else {
            nicknameField.setTextColor(getResources().getColor(R.color.red));
            nicknameField.setError(nicknameV);
            isValid = false;
        }
        if (passwordV.equals(valid))
            passwordField.setTextColor(getResources().getColor(R.color.green));
        else {
            passwordField.setTextColor(getResources().getColor(R.color.red));
            passwordField.setError(passwordV);
            isValid = false;
        }
        if (repeatPasswordV.equals(valid))
            repeatPasswordField.setTextColor(getResources().getColor(R.color.green));
        else {
            repeatPasswordField.setTextColor(getResources().getColor(R.color.red));
            repeatPasswordField.setError(repeatPasswordV);
            isValid = false;
        }
        return isValid;
    }
}