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

//    ConversationDao conversationDao;
//    private ConversationsViewModel viewModel;

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
//            if(MyApp.getCurrentUser() != null) {
//                int x = 5;
//            }
        }
        TextView toplogInUsername = findViewById(R.id.ToplogInUsername);
        toplogInUsername.setText(MyApp.getCurrentUser().getId());
        //ImageView topProfilePictureLogInUser = findViewById(R.id.topProfilePictureLogInUser);
        toplogInUsername.setText(MyApp.getCurrentUser().getName());
        //topProfilePictureLogInUser.setImageResource(R.drawable.defaultimage);
        setProfilePicture();
//        db = Room.databaseBuilder(getApplicationContext(), MyAppDB.class, "MyAppDB")
//                .allowMainThreadQueries().build();
        conversationDao = db.conversationDao();
//        viewModel = new ViewModelProvider(this).get(ConversationsViewModel.class);

        RecyclerView listOfContacts = findViewById(R.id.ListOfContacts);
        adapter = new ContactsListAdapter(this);
//        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        listOfContacts.setAdapter(adapter);
        listOfContacts.setLayoutManager(new LinearLayoutManager(this));
        usersDTO.IdClass param = new usersDTO.IdClass(MyApp.getCurrentUser().getId());
        Call<List<Conversation>> userRequest = usersAPI.getAllConversations(MyApp.getCookie(), param);
        userRequest.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
//                    List<Conversation> conversationList = response.body();
//                    List<Contact> contactsList = new ArrayList<>();
//                    conversationsList = new ArrayList<>();
                conversationsList = response.body();
                List<Conversation> daoConversationList = conversationDao.getAllConversations();
                for (Conversation c : daoConversationList) {
                    conversationDao.delete(c);
                }
//                    conversationDao.deleteAll(conversationDao.getAllConversations());
//        List<Conversation> conversations = conversationDao.getAllConversations();
                for (Conversation c : conversationsList) {
                    //contactList.add(c.contact);
                    //todo
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
/*            Gson gson = new Gson();
            String myJson = gson.toJson(adapter);
            i.putExtra("contactAdapter", myJson);*/
            startActivity(i);
        });
        setSettingsBtnListener();
//        viewModel.getAllConversations().observe(this, conversations -> {
//            List<Contact> contactsList = getAllContacts(conversations);
//            adapter.setContacts(contactsList);
//        });
//        adapter.setContacts(contactList);

    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        List<Conversation> conDao = conversationDao.getAllConversations();
//        List<Conversation> currentConList = conversationsList;
//        contactList.clear();
//        conversationsList.clear();
//        conversationsList.addAll(conversationDao.getAllConversations());
////        List<Conversation> conversationList = conversationDao.getAllConversations();
////        List<Contact> contactsList;
//        for (Conversation c : conversationsList) {
//            contactList.add(c.contact);
//        }
////        contactList.addAll(contactsList);
//        adapter.notifyDataSetChanged();
//        //        contactList.clear();
////        conversationsList.clear();
////        conversationDao.deleteAll(conversationDao.getAllConversations());
////        for (Conversation c : conversationsList) {
////            conversationDao.insert(c);
////        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstInitialization) {
            refreshContactList();
            /*contactList.clear();
            conversationsList.clear();
            conversationsList.addAll(conversationDao.getAllConversations());
//        List<Conversation> conversationList = conversationDao.getAllConversations();
//        List<Contact> contactsList;
            for (Conversation c : conversationsList) {
                contactList.add(c.contact);
            }
//        contactList.addAll(contactsList);
            adapter.setContacts(contactList);*/
        } else
            firstInitialization = false;
    }

    public void refreshContactList() {
        contactList.clear();
        conversationsList.clear();
        conversationsList.addAll(conversationDao.getAllConversations());
//        List<Conversation> conversationList = conversationDao.getAllConversations();
//        List<Contact> contactsList;
        for (Conversation c : conversationsList) {
            contactList.add(c.contact);
        }
//        contactList.addAll(contactsList);
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
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        conversationDao.deleteAll(conversationDao.getAllConversations());
//    }

    //    public List<Contact> getAllContacts(List<Conversation> conversations) {
//        List<Contact> contactsList = new ArrayList<>();
////        List<Conversation> conversations = conversationDao.getAllConversations();
//        for (Conversation c : conversations) {
//            contactsList.add(c.contact);
//        }
//        return contactsList;
//    }
}