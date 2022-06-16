package com.example.chatapp.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chatapp.MyApp;
import com.example.chatapp.R;

public class SettingsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        setBackBtnListener();
        setSaveBtnListener();
        EditText serverNameText = findViewById(R.id.settingsServerText);
        serverNameText.setText(MyApp.getBaseUrl());
    }

    private void setBackBtnListener() {
        ImageButton backBtn = findViewById(R.id.settingsBackBtn);
        backBtn.setOnClickListener(view -> {
            this.finish();
            //TODO set current cur to null?
        });
    }

    private void setSaveBtnListener() {
        Button saveBtn = findViewById(R.id.settingsSaveChangesBtn);
        saveBtn.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_LONG).show();
            EditText serverNameText = findViewById(R.id.settingsServerText);
            MyApp.setBaseUrl(serverNameText.getText().toString());
        });

    }
}