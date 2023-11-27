package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Try2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try2);
        Intent intent = getIntent();
        String s = intent.getExtras().get("txt").toString();
        TextView txt = findViewById(R.id.copy_txt);
        txt.setText(s);
    }
}