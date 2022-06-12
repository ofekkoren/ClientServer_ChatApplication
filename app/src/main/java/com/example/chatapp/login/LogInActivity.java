package com.example.chatapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatapp.ContactsList.contactsList;
import com.example.chatapp.MainActivity;
import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.models.User;
import com.example.chatapp.signup.SignUp;

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
            if(logIn.logInCheckValidation(username, password, logInValidationMessage)) {
                logIn.getAndSetCurrentUser();
                Intent i = new Intent(this, contactsList.class);
                //pass current user?
                startActivity(i);

            }
            else {
                logInValidationMessage.setText("");
            }

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
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });
    }

}