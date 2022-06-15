package com.example.chatapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatapp.ContactsList.contactsList;
import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.MainActivity;
import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.api.LogInAPI;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.models.User;
import com.example.chatapp.signup.SignUp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {

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
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyApp.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LogInAPI logInAPI = retrofit.create(LogInAPI.class);
            LogInAPI.LogInParams params = new LogInAPI.LogInParams(username, password);
            Call<LogInAPI.LogInResults> call = logInAPI.checkValidation(params);
            call.enqueue(new Callback<LogInAPI.LogInResults>() {
                @Override
                public void onResponse(Call<LogInAPI.LogInResults> call, Response<LogInAPI.LogInResults> response) {
                    LogInAPI.LogInResults results = response.body();
                    String cookie = response.headers().get("Set-Cookie");
                    MyApp.setCookie(cookie);
                    boolean isValidInfo = false;
                    if (results.username.equals("valid")) {
                        isValidInfo = true;
                    } else if (results.username.equals("empty")) {
                        logInValidationMessage.setText("All fields must be filled!");
                        logInValidationMessage.setVisibility(View.VISIBLE);
                        logInValidationMessage.setTextColor(Color.parseColor("red"));
                    } else if (results.username.equals("invalid")) {
                        logInValidationMessage.setText("Wrong username or password!");
                        logInValidationMessage.setVisibility(View.VISIBLE);
                        logInValidationMessage.setTextColor(Color.parseColor("red"));
                    }
                    if (isValidInfo) {
                        UsersAPI usersAPI = retrofit.create(UsersAPI.class);
                        Call<User> userRequest = usersAPI.getCurrentUser(MyApp.getCookie());
                        userRequest.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user = response.body();
                                MyApp.setCurrentUser(user);
                                moveToContactList(user);
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                            }
                        });
                    }/* else
                        logInValidationMessage.setText("");*/
                }

                @Override
                public void onFailure(Call<LogInAPI.LogInResults> call, Throwable t) {
                }
            });
            /*if(logIn.logInCheckValidation(username, password, logInValidationMessage)) {
                logIn.getAndSetCurrentUser();
                Intent i = new Intent(this, contactsList.class);
                //pass current user?
                startActivity(i);

            }
            else {
                logInValidationMessage.setText("");
            }*/

            // if(logInCheckValidation(username,password) == true) {
            //  Intent i = new Intent(this, ChatScreen.class);
//            startActivity(i);
//            }
        });
    }

    private void moveToContactList(User user) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( this, instanceIdResult -> {
            usersDTO.IdClass parameter=new usersDTO.IdClass(instanceIdResult.getToken());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyApp.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UsersAPI usersAPI = retrofit.create(UsersAPI.class);
            Call<Void> sendToken =usersAPI.setFirebaseToken(MyApp.getCookie(),parameter);
            sendToken.enqueue(new Callback<Void>() {@Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("dsasss","sad");
            }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        });
        Intent intent = new Intent(this, contactsList.class);
        startActivity(intent);
    }

    public void SignUpLink() {
        TextView LogInLinkToSignUp = (TextView) findViewById(R.id.LogInLinkToSignUp);
        LogInLinkToSignUp.setPaintFlags(LogInLinkToSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        LogInLinkToSignUp.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });
    }

}