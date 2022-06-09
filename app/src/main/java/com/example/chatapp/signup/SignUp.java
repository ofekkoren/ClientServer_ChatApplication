package com.example.chatapp.signup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chatapp.MyApp;
import com.example.chatapp.R;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
    Retrofit retrofit;
    SignUpAPI signUpAPI;
    public static String cookie;
    final int SELECT_IMAGE=1;
    Bitmap USER_IMAGE;

    public SignUp() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getContext().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        signUpAPI = retrofit.create(SignUpAPI.class);

        //USER_IMAGE = BitmapFactory.decodeResource(MyApp.getContext().getResources(), R.drawable.defaultimage);;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        ImageView image=findViewById(R.id.profileImageView);
        image.setImageResource(R.drawable.defaultimage);

        Button imageBtn=findViewById(R.id.profileImage);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent=new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                launchChooseImageActivity.launch(imageIntent);
            }
        });

        Button signUpBtn = findViewById(R.id.submitSignUp);
        signUpBtn.setOnClickListener(view -> {
            EditText username = findViewById(R.id.Username);
            EditText nickname = findViewById(R.id.Nickname);
            EditText password = findViewById(R.id.Password);
            EditText repeatPassword = findViewById(R.id.RepeatPassword);
            username.setOnFocusChangeListener((v, b) -> {
                username.setTextColor(getResources().getColor(R.color.black));
            });
            nickname.setOnFocusChangeListener((v, b) -> {
                nickname.setTextColor(getResources().getColor(R.color.black));
            });
            password.setOnFocusChangeListener((v, b) -> {
                password.setTextColor(getResources().getColor(R.color.black));
            });
            repeatPassword.setOnFocusChangeListener((v, b) -> {
                repeatPassword.setTextColor(getResources().getColor(R.color.black));
            });
            String usernameStr = username.getText().toString().trim();
            String nicknameStr = nickname.getText().toString().trim();
            String passwordStr = password.getText().toString();
            String repeatPasswordStr = repeatPassword.getText().toString();

            SignUpAPI.signUpParams params = new SignUpAPI.signUpParams(usernameStr, nicknameStr, passwordStr,
                    repeatPasswordStr);
            Call<SignUpAPI.signUpResults> call = signUpAPI.signUp(params);
            call.enqueue(new Callback<SignUpAPI.signUpResults>() {
                @Override
                public void onResponse(Call<SignUpAPI.signUpResults> call, Response<SignUpAPI.signUpResults> response) {
                    SignUpAPI.signUpResults res = response.body();
                    cookie = response.headers().get("Set-Cookie");
                    if (validateSignUP(res.usernameV, res.nicknameV, res.passwordV, res.repeatPasswordV)) {
                        Log.d("sadsa", "Sadsa");
                        /*var newUser = await getUser(newUserName.toString());
                        setUser(newUser);
                        navigate("../chatScreen");*/
                        Call<ArrayList<SignUpAPI.ContactToJson>> y = signUpAPI.getContacts(cookie);
                        y.enqueue(new Callback<ArrayList<SignUpAPI.ContactToJson>>() {
                            @Override
                            public void onResponse(Call<ArrayList<SignUpAPI.ContactToJson>> call, Response<ArrayList<SignUpAPI.ContactToJson>> response) {
                                ArrayList<SignUpAPI.ContactToJson> M = response.body();
                                Log.d("dfdf", "Dsfsdsf");
                            }

                            @Override
                            public void onFailure(Call<ArrayList<SignUpAPI.ContactToJson>> call, Throwable t) {
                                Log.d("hgj", "ghjjjj");

                            }
                        });
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

    ActivityResultLauncher<Intent> launchChooseImageActivity =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
}