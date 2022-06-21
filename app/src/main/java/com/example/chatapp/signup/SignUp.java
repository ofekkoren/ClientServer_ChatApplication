package com.example.chatapp.signup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.ContactsList.contactsList;
import com.example.chatapp.DAO.ProfileImageDao;
import com.example.chatapp.DB.MyAppDB;
import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.MainActivity;
import com.example.chatapp.api.ContactAPI;
import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.api.SignUpAPI;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.login.LogInActivity;
import com.example.chatapp.models.User;
import com.example.chatapp.models.UserProfilePicture;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
    Retrofit retrofit;
    SignUpAPI signUpAPI;
    UsersAPI usersAPI;
    final int SELECT_IMAGE = 1;
    Bitmap USER_IMAGE;

    public SignUp() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        signUpAPI = retrofit.create(SignUpAPI.class);
        usersAPI = retrofit.create(UsersAPI.class);
        //USER_IMAGE = BitmapFactory.decodeResource(MyApp.getContext().getResources(), R.drawable.defaultimage);;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageView image = findViewById(R.id.profileImageView);
        image.setImageResource(R.drawable.defaultimage);
/*        final int defaultImage;
        defaultImage = R.drawable.defaultimage;
        image.setImageResource(defaultImage);*/
        Button imageBtn = findViewById(R.id.profileImage);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent();
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
                    MyApp.setCookie(response.headers().get("Set-Cookie"));
                    if (validateSignUP(res.usernameV, res.nicknameV, res.passwordV, res.repeatPasswordV)) {
                        /*var newUser = await getUser(newUserName.toString());
                        setUser(newUser);
                        navigate("../chatScreen");*/
//                        ContactDTO.AddContactParams a = new ContactDTO.AddContactParams("ttt", "yyyy", "serv");
                        Call<User> userRequest = usersAPI.getCurrentUser(MyApp.getCookie());
                        userRequest.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User newUser = response.body();
                                createUserPicture(newUser.getId());
                                logInAfterRegistration(newUser);
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<SignUpAPI.signUpResults> call, Throwable t) {
                }
            });
        });
        createLinkToLogIn();
    }

    private void logInAfterRegistration(User user) {
        MyApp.setCurrentUser(user);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            usersDTO.IdClass parameter = new usersDTO.IdClass(instanceIdResult.getToken());
            UsersAPI usersAPI = retrofit.create(UsersAPI.class);
            Call<Void> sendToken = usersAPI.setFirebaseToken(MyApp.getCookie(), parameter);
            sendToken.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });
        clearFields();
        Intent intent = new Intent(this, contactsList.class);
        startActivity(intent);
    }

    private void clearFields(){
        EditText username = findViewById(R.id.Username);
        username.setText("");
        EditText nickname = findViewById(R.id.Nickname);
        nickname.setText("");
        EditText password = findViewById(R.id.Password);
        password.setText("");
        EditText repeatPassword = findViewById(R.id.RepeatPassword);
        repeatPassword.setText("");
        ImageView image = findViewById(R.id.profileImageView);
        image.setImageResource(R.drawable.defaultimage);
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

    public void createLinkToLogIn() {
        TextView logInLink = findViewById(R.id.SignUpLinkToLogIn);
        logInLink.setPaintFlags(logInLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        logInLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        });
    }

    ActivityResultLauncher<Intent> launchChooseImageActivity =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        ImageView image = findViewById(R.id.profileImageView);
                        image.setImageURI(selectedImageUri);
                        /*Bitmap selectedImageBitmap;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
            });

    private void createUserPicture(String username) {
        ImageView image = findViewById(R.id.profileImageView);
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        //MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
        Bitmap imageBitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        String base64Image=Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
/*        byte[] decodedBytes = Base64.decode(base64Image.substring(base64Image.indexOf(",")  + 1), Base64.DEFAULT);
        Bitmap img =BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);*/
        MyAppDB db = MyAppDB.getInstance(getApplicationContext());
        ProfileImageDao profileImageDao = db.profileImageDao();
        UserProfilePicture userProfilePicture=new UserProfilePicture(username,base64Image);
        profileImageDao.insert(userProfilePicture);
    }
}