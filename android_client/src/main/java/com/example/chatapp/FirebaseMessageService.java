package com.example.chatapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.DB.MyAppDB;
import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.api.SignUpAPI;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.conversation.ConversationScreen;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Conversation;
import com.example.chatapp.models.Message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
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
        if (validateReceivedMessage(remoteMessage)) {
            if (validateReceivedMessageType(remoteMessage, "newMessage"))
                handleNewMessage(remoteMessage);
            else if (validateReceivedMessageType(remoteMessage, "newConversation"))
                handleNewChat(remoteMessage);
        }
    }

    private boolean validateReceivedMessageType(RemoteMessage remoteMessage, String messageType) {
        if (remoteMessage.getData().get("type").equals(messageType))
            return true;
        return false;
    }

    private boolean validateReceivedMessage(RemoteMessage remoteMessage) {
        if (remoteMessage == null)
            return false;
        if (remoteMessage.getNotification() == null || remoteMessage.getData() == null)
            return false;
        if (remoteMessage.getData().get("type") == null)
            return false;
        if (remoteMessage.getData().get("sender") == null || remoteMessage.getData().get("sentTo") == null)
            return false;
        if (remoteMessage.getData().get("sender").equals(remoteMessage.getData().get("sentTo")) ||
                remoteMessage.getData().get("sender").equals(MyApp.getCurrentUser().getId()))
            return false;
        return true;
    }

    private void handleNewMessage(RemoteMessage remoteMessage) {
        int msgId = Integer.parseInt(remoteMessage.getData().get("id"));
        boolean msgSent = Boolean.parseBoolean(remoteMessage.getData().get("sent"));
        String msgContent = remoteMessage.getData().get("content");
        String msgCreatedAt = remoteMessage.getData().get("created");
        Message message = new Message(msgId, msgContent, msgCreatedAt, msgSent);
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
        MyAppDB db = MyAppDB.getInstance(getApplicationContext());
        ConversationDao conversationDao = db.conversationDao();
        Conversation existingConversation = conversationDao.getConversation(remoteMessage.getData().get("sender"));
        existingConversation.messages.add(message);
        Contact contact = existingConversation.getContact();
        Contact updatedContact = new Contact(contact.getId(), contact.getUsername(), contact.getName(),
                contact.getServer(), message.getContent(), message.getCreated());
        existingConversation.setContact(updatedContact);
        conversationDao.update(existingConversation);
        Intent intent = new Intent("new-message");
        intent.putExtra(getString(R.string.broadcastReceiverContactName), remoteMessage.getData().get("sender"));
        LocalBroadcastManager.getInstance(MyApp.getContext()).sendBroadcast(intent);
    }

    private void handleNewChat(RemoteMessage remoteMessage) {
        String conversationId = remoteMessage.getData().get("conversationId");
        int contactId = Integer.parseInt(remoteMessage.getData().get("contactId"));
        String contactUsername = remoteMessage.getData().get("contactUsername");
        String contactName = remoteMessage.getData().get("contactName");
        String contactServer = remoteMessage.getData().get("contactServer");
        String contactLast = remoteMessage.getData().get("contactLast");
        String contactLastDate = remoteMessage.getData().get("contactLastDate");
        Contact newContact = new Contact(contactId, contactUsername, contactName, contactServer, contactLast,
                contactLastDate);
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
        Conversation newConversation = new Conversation(conversationId, new ArrayList<Message>(), newContact);
        MyAppDB db = MyAppDB.getInstance(getApplicationContext());
        ConversationDao conversationDao = db.conversationDao();
        conversationDao.insert(newConversation);
        Intent intent = new Intent("new-contact");
        intent.putExtra(getString(R.string.broadcastReceiverContactName), remoteMessage.getData().get("sender"));
        LocalBroadcastManager.getInstance(MyApp.getContext()).sendBroadcast(intent);
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
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        usersDTO.IdClass parameter = new usersDTO.IdClass(token);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsersAPI usersAPI = retrofit.create(UsersAPI.class);
        Call<Void> sendToken = usersAPI.setFirebaseToken(MyApp.getCookie(), parameter);
        sendToken.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("dsasss", "sad");//TODO DEL
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}