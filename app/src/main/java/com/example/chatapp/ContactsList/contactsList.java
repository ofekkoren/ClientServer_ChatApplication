package com.example.chatapp.ContactsList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.AddNewContact;
import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.DB.MyAppDB;
import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.MyApp;
import com.example.chatapp.R;
import com.example.chatapp.adapters.ContactsListAdapter;
import com.example.chatapp.api.LogInAPI;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Conversation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.example.chatapp.viewModels.ConversationsViewModel;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        Intent activityIntent = getIntent();
        if(activityIntent == null) {
//            if(MyApp.getCurrentUser() != null) {
//                int x = 5;
//            }
        }
        TextView toplogInUsername = findViewById(R.id.ToplogInUsername);
        toplogInUsername.setText(MyApp.getCurrentUser().getId());
        ImageView topProfilePictureLogInUser = findViewById(R.id.topProfilePictureLogInUser);
        toplogInUsername.setText(MyApp.getCurrentUser().getId());
        topProfilePictureLogInUser.setImageResource(R.drawable.defaultimage);

//        db = Room.databaseBuilder(getApplicationContext(), MyAppDB.class, "MyAppDB")
//                .allowMainThreadQueries().build();
        db = MyAppDB.getInstance(getApplicationContext());
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
                    List<Conversation> conversationList = conversationDao.getAllConversations();
                    for (Conversation c : conversationList) {
                        conversationDao.delete(c);
                    }
//                    conversationDao.deleteAll(conversationDao.getAllConversations());
//        List<Conversation> conversations = conversationDao.getAllConversations();
                    for (Conversation c : conversationsList) {
                        contactList.add(c.contact);
                        //todo
                        conversationDao.insert(c);
                    }
                    adapter.setContacts(contactList);
                }

                @Override
                public void onFailure(Call<List<Conversation>> call, Throwable t) {
                    int x = 5;
                }
            });
        FloatingActionButton btnAddNewContact = findViewById(R.id.btnAddNewContact);
        btnAddNewContact.setOnClickListener(view -> {
            Intent i = new Intent(this, AddNewContact.class);
            startActivity(i);
        });
//        viewModel.getAllConversations().observe(this, conversations -> {
//            List<Contact> contactsList = getAllContacts(conversations);
//            adapter.setContacts(contactsList);
//        });
//        adapter.setContacts(contactList);

//        List<Contact> contacts = new ArrayList<>();
//        contacts.add(new Contact(1, "Alice1","Alice1","555", "hello world", "11:00"));
//        contacts.add(new Contact(2, "Alice2","Alice2","555", "hello world", "11:00"));
//        contacts.add(new Contact(3, "Alice3","Alice3","555", "hello world", "11:00"));
//        contacts.add(new Contact(4, "Alice4","Alice4","555", "hello world", "11:00"));
//        contacts.add(new Contact(5, "Alice5","Alice5","555", "hello world", "11:00"));
//        contacts.add(new Contact(6, "Alice1","Alice1","555", "hello world", "11:00"));
//        contacts.add(new Contact(7, "Alice2","Alice2","555", "hello world", "11:00"));
//        contacts.add(new Contact(8, "Alice3","Alice3","555", "hello world", "11:00"));
//        contacts.add(new Contact(9, "Alice4","Alice4","555", "hello world", "11:00"));
//        contacts.add(new Contact(10, "Alice5","Alice5","555", "hello world", "11:00"));
//
//        adapter.setContacts(contacts);
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
        contactList.clear();
        conversationsList.clear();
        conversationsList.addAll(conversationDao.getAllConversations());
//        List<Conversation> conversationList = conversationDao.getAllConversations();
//        List<Contact> contactsList;
        for (Conversation c : conversationsList) {
            contactList.add(c.contact);
        }
//        contactList.addAll(contactsList);
        adapter.notifyDataSetChanged();
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