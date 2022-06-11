package com.example.chatapp.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.api.LogInAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {
//    Retrofit retrofit;
//    LogInAPI logInAPI;

//    public LogInActivity() {
//        //Initializing retrofit
//        retrofit = new Retrofit.Builder()
//                .baseUrl(MyApp.context.getString(R.string.BaseUrl))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        logInAPI = retrofit.create(LogInAPI.class);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        LogIn logIn = new LogIn();

        Button logInButton = findViewById(R.id.LogInButton);
        EditText logInUsername = findViewById(R.id.LogInUsername);
        EditText logInPassword = findViewById(R.id.LogInPassword);
        TextView logInValidationMessage = findViewById(R.id.LogInValidationMessage);
        SignUpLink();

        logInButton.setOnClickListener(v -> {
            String username = logInUsername.getText().toString();
            String password = logInPassword.getText().toString();
            logInValidationMessage.setText("");
//            String LogInValidationMessage = logInValidationMessage.getText().toString();
            logIn.logInCheckValidation(username, password, logInValidationMessage);
            logInValidationMessage.setText("");
            // if(logInCheckValidation(username,password) == true) {
            //  Intent i = new Intent(this, ChatScreen.class);
//            startActivity(i);
//            }
        });
    }

    public void SignUpLink() {
        TextView LogInLinkToSignUp = (TextView) findViewById(R.id.LogInLinkToSignUp);
        LogInLinkToSignUp.setPaintFlags(LogInLinkToSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        LogInLinkToSignUp.setOnClickListener(v -> {
              Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }

}