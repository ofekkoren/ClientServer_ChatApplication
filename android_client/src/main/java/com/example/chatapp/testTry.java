package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class testTry extends AppCompatActivity {
    final String[] ls = {"Ofek", "Roy", "Avi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_try);
        ListView list=findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_expandable_list_item_1,ls);
        list.setAdapter(adapter);

        list.getOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<String> parent, View view, int position, long id) {
                String s = ls[position].toString();

            }
        });
    }
}