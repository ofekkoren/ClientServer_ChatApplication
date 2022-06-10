package com.example.chatapp.api;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent i = new Intent(this, LogInActivity.class);
//        startActivity(i);

//        LogInActivity logInTemp = new LogInActivity();
//        logInTemp.logInCheckValidation("Ofek Koren","123456K");
    }
}