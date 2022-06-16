package com.example.chatapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.api.SignUpAPI;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.models.Message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirebaseMessageService extends FirebaseMessagingService {
    public FirebaseMessageService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null && remoteMessage.getData() != null) {
            if (remoteMessage.getData().get("type").equals("message"))
                handleNewMessage(remoteMessage);
        }
    }

    private void handleNewMessage(RemoteMessage remoteMessage) {
        int msgId = Integer.parseInt(remoteMessage.getData().get("id"));
        boolean msgSent = Boolean.parseBoolean(remoteMessage.getData().get("sent"));
        String msgContent = remoteMessage.getData().get("content");
        String msgCreatedAt = remoteMessage.getData().get("created");
        Message m = new Message(msgId, msgContent, msgCreatedAt, msgSent);
        //TODO ADD MESSAGE TO ROOM
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                getString(R.string.newMessageChannelId))
                .setSmallIcon(R.drawable.ic_message_notification)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name1 = getString(R.string.newMessageChannel);
            String description1 = getString((R.string.newMessageChannelDesc));
            String id1 = getString(R.string.newMessageChannelId);
            NotificationChannel messageChannel = new NotificationChannel(id1, name1,
                    NotificationManager.IMPORTANCE_DEFAULT);
            messageChannel.setDescription(description1);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(messageChannel);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("token1", "shhh");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener((Executor) this, instanceIdResult -> {
            usersDTO.IdClass parameter = new usersDTO.IdClass(instanceIdResult.getToken());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyApp.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UsersAPI usersAPI = retrofit.create(UsersAPI.class);
            Call<Void> sendToken = usersAPI.setFirebaseToken(MyApp.getCookie(), parameter);
            sendToken.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("dsasss", "sad");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        });
    }
}