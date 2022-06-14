package com.example.chatapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.api.SignUpAPI;
import com.example.chatapp.api.UsersAPI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.Executor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirebaseMessageService extends FirebaseMessagingService {
    public FirebaseMessageService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d ("nibba","received msg");
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("token1","shhh");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener((Executor) this, instanceIdResult -> {
            usersDTO.IdClass parameter=new usersDTO.IdClass(instanceIdResult.getToken());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyApp.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UsersAPI usersAPI = retrofit.create(UsersAPI.class);
            usersAPI.setFirebaseToken(MyApp.getCookie(),parameter);
        });
    }
}