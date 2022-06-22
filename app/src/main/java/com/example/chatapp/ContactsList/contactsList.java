package com.example.chatapp.ContactsList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.AddNewContact;
import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.DAO.ProfileImageDao;
import com.example.chatapp.DB.MyAppDB;
import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.adapters.ContactsListAdapter;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Conversation;
import com.example.chatapp.models.User;
import com.example.chatapp.models.UserProfilePicture;
import com.example.chatapp.settings.SettingsScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.example.chatapp.viewModels.ConversationsViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class contactsList extends AppCompatActivity {
    private MyAppDB db;
    private ConversationDao conversationDao;
    private List<Contact> contactList;
    private List<Conversation> conversationsList;
    ContactsListAdapter adapter;
    Retrofit retrofit;
    UsersAPI usersAPI;
    private boolean firstInitialization;
    private BroadcastReceiver broadcastReceiver;

    public contactsList() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usersAPI = retrofit.create(UsersAPI.class);
        contactList = new ArrayList<>();
        conversationsList = new ArrayList<>();
        firstInitialization = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstInitialization = true;
        setContentView(R.layout.activity_contacts_list);
        Intent activityIntent = getIntent();
        db = MyAppDB.getInstance(getApplicationContext());
        if (activityIntent == null) {
        }
        TextView toplogInUsername = findViewById(R.id.ToplogInUsername);
        toplogInUsername.setText(MyApp.getCurrentUser().getId());
        toplogInUsername.setText(MyApp.getCurrentUser().getName());
        setProfilePicture();
        conversationDao = db.conversationDao();
        RecyclerView listOfContacts = findViewById(R.id.ListOfContacts);
        adapter = new ContactsListAdapter(this);
        listOfContacts.setAdapter(adapter);
        listOfContacts.setLayoutManager(new LinearLayoutManager(this));
        usersDTO.IdClass param = new usersDTO.IdClass(MyApp.getCurrentUser().getId());
        Call<List<Conversation>> userRequest = usersAPI.getAllConversations(MyApp.getCookie(), param);
        userRequest.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                conversationsList = response.body();
                List<Conversation> daoConversationList = conversationDao.getAllConversations();
                for (Conversation c : daoConversationList) {
                    conversationDao.delete(c);
                }
                for (Conversation c : conversationsList) {
                    conversationDao.insert(c);
                }
                conversationsList = conversationDao.getAllConversations();
                for (Conversation c : conversationsList) {
                    contactList.add(c.contact);
                }
                adapter.setContacts(contactList);
                createMessageBroadcastReceiver();
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
            }
        });
        FloatingActionButton btnAddNewContact = findViewById(R.id.btnAddNewContact);
        btnAddNewContact.setOnClickListener(view -> {
            Intent i = new Intent(this, AddNewContact.class);
            startActivity(i);
        });
        setSettingsBtnListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstInitialization) {
            refreshContactList();
        } else
            firstInitialization = false;
    }

    public void refreshContactList() {
        contactList.clear();
        conversationsList.clear();
        conversationsList.addAll(conversationDao.getAllConversations());
        for (Conversation c : conversationsList) {
            contactList.add(c.contact);
        }
        adapter.setContacts(contactList);
    }

    private void setSettingsBtnListener() {
        ImageButton backBtn = findViewById(R.id.contactListSettingsBtn);
        backBtn.setOnClickListener(view -> {
            Intent settingsIntent = new Intent(view.getContext(), SettingsScreen.class);
            view.getContext().startActivity(settingsIntent);
        });
    }

    private void createMessageBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshContactList();
            }
        };
        LocalBroadcastManager.getInstance(MyApp.getContext()).registerReceiver(broadcastReceiver,
                new IntentFilter("new-message"));
        LocalBroadcastManager.getInstance(MyApp.getContext()).registerReceiver(broadcastReceiver,
                new IntentFilter("new-contact"));
    }

    private void setProfilePicture() {
        ImageView topProfilePictureLogInUser = findViewById(R.id.topProfilePictureLogInUser);
        ProfileImageDao profileImageDao = db.profileImageDao();
        UserProfilePicture userProfilePicture = profileImageDao.getImage(MyApp.getCurrentUser().getId());
        if (userProfilePicture != null) {
            String base64Image=userProfilePicture.getImage();
            byte[] decodedBytes = Base64.decode(base64Image.substring(base64Image.indexOf(",")  + 1), Base64.DEFAULT);
            Bitmap img =BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            topProfilePictureLogInUser.setImageBitmap(img);
        } else
            topProfilePictureLogInUser.setImageResource(R.drawable.defaultimage);
    }
}