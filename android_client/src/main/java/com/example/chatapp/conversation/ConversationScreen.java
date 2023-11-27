package com.example.chatapp.conversation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.DB.MyAppDB;
import com.example.chatapp.DTO.ContactDTO;
import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.Utils;
import com.example.chatapp.adapters.RecyclerMessageListAdapter;
import com.example.chatapp.api.ContactAPI;
import com.example.chatapp.api.TransferAPI;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Conversation;
import com.example.chatapp.models.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConversationScreen extends AppCompatActivity {

    private static Conversation currentConversation;
    private MyAppDB db;
    private ConversationDao conversationDao;
    private RecyclerMessageListAdapter messagesListAdapter;
    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversationscreen);
        String currentUsername = getIntent().getExtras().getString(getString(R.string.conversationContactExtraKey));
        db = MyAppDB.getInstance(getApplicationContext());
        conversationDao = db.conversationDao();
        currentConversation = conversationDao.getConversation(currentUsername);
        TextView header = findViewById(R.id.conversationHeader);
        header.setText(currentConversation.getContact().getName());
        RecyclerView chatBody = findViewById(R.id.chatBody);
        messagesListAdapter = new RecyclerMessageListAdapter(this);
        chatBody.setAdapter(messagesListAdapter);
        chatBody.setLayoutManager(new LinearLayoutManager(this));
        messagesListAdapter.setMessages(currentConversation.getMessages());
        chatBody.scrollToPosition(currentConversation.getMessages().size() - 1);
        createMessageBroadcastReceiver();
        setBackBtnListener();
        setSendBtnListener();
    }

    private void setBackBtnListener() {
        ImageButton backBtn = findViewById(R.id.conversationBackBtn);
        backBtn.setOnClickListener(view -> {
            this.finish();
        });
    }

    private void setSendBtnListener() {
        ImageButton sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(view -> {
            EditText textBox = findViewById(R.id.textBox);
            if (textBox.getText().toString().trim().equals(""))
                return;
            String messageContent = textBox.getText().toString();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyApp.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            String contactServer = Utils.backendToAndroidServer(currentConversation.getContact().getServer());
            Retrofit transferRetrofit = new Retrofit.Builder()
                    .baseUrl(contactServer)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TransferAPI transferAPI = transferRetrofit.create(TransferAPI.class);
            TransferAPI.TransferParams params = new TransferAPI.TransferParams(MyApp.getCurrentUser().getId(),
                    currentConversation.getContact().getUsername(), messageContent);
            Call<Void> transferCall = transferAPI.transferMessage(MyApp.getCookie(), params);
            transferCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        ContactAPI contactAPI = retrofit.create(ContactAPI.class);
                        ContactDTO.MessageContent content = new ContactDTO.MessageContent(messageContent);
                        Call<Void> postCall = contactAPI.AddMessage(currentConversation.getContact().getUsername(),
                                MyApp.getCookie(), content);
                        postCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                UsersAPI usersAPI = retrofit.create(UsersAPI.class);
                                usersDTO.IdClass contactName =
                                        new usersDTO.IdClass(currentConversation.getContact().getUsername());
                                Call<Conversation> conversationCall = usersAPI.getConversation(MyApp.getCookie(),
                                        contactName);
                                conversationCall.enqueue(new Callback<Conversation>() {
                                    @Override
                                    public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                                        Conversation conversation = response.body();
                                        Message newMessage =
                                                conversation.getMessages().get(conversation.getMessages().size() - 1);
                                        addNewMessage(newMessage, conversation);
                                    }

                                    @Override
                                    public void onFailure(Call<Conversation> call, Throwable t) {
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
            textBox.setText("");
        });
    }

    private void addNewMessage(Message message, Conversation updatedConversation) {
        Conversation existingConversation = conversationDao.getConversation(currentConversation.getContact().getUsername());
        existingConversation.setContact(updatedConversation.getContact());
        existingConversation.messages.add(message);
        conversationDao.update(existingConversation);
        messagesListAdapter.addMessage(message);
        currentConversation = existingConversation;
        RecyclerView chatBody = findViewById(R.id.chatBody);
        chatBody.scrollToPosition(currentConversation.getMessages().size() - 1);


    }

    private void createMessageBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String contactName =
                        intent.getExtras().getString(context.getString(R.string.broadcastReceiverContactName));
                if (contactName.equals(currentConversation.getContact().getUsername())) {
                    Conversation conversation = conversationDao.getConversation(contactName);
                    currentConversation = conversation;
                    messagesListAdapter.setMessages(conversation.getMessages());
                    Toast.makeText(getApplicationContext(), "New message", Toast.LENGTH_SHORT).show();
                }
            }
        };
        LocalBroadcastManager.getInstance(MyApp.getContext()).registerReceiver(broadcastReceiver,
                new IntentFilter("new-message"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(MyApp.getContext()).unregisterReceiver(broadcastReceiver);
    }
}