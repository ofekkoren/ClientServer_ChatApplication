package com.example.chatapp.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


        logInButton.setOnClickListener(v -> {
            String username = logInUsername.getText().toString();
            String password = logInPassword.getText().toString();
            logInValidationMessage.setText("");
//            String LogInValidationMessage = logInValidationMessage.getText().toString();
            logIn.logInCheckValidation(username,password, logInValidationMessage);
            logInValidationMessage.setText("");

            // if(logInCheckValidation(username,password) == true) {
                //  Intent i = new Intent(this, SignUp.class);
//            startActivity(i);
//            }
        });
    }

//    //    // Checks validation
//    public void logInCheckValidation(String username, String password) {
//        LogInAPI.LogInParams params = new LogInAPI.LogInParams(username, password);
//        Call<LogInAPI.LogInResults> call = logInAPI.checkValidation(params);
//        int a=3;
//        call.enqueue(new Callback<LogInAPI.LogInResults>() {
//            @Override
//            public void onResponse(Call<LogInAPI.LogInResults> call, Response<LogInAPI.LogInResults> response) {
//                LogInAPI.LogInResults results = response.body();
//                int x=5;
//            }
//
//            @Override
//            public void onFailure(Call<LogInAPI.LogInResults> call, Throwable t) {
//            }
//        });
////        return true;
//    }

}