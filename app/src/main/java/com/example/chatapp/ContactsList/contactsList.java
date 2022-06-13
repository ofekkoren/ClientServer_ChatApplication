package com.example.chatapp.ContactsList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.DB.MyAppDB;
import com.example.chatapp.R;
import com.example.chatapp.adapters.ContactsListAdapter;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Conversation;

import java.util.ArrayList;
import java.util.List;

public class contactsList extends AppCompatActivity {
    private MyAppDB db;
    private ConversationDao conversationDao;
    private List<Contact> contactList;
    final ContactsListAdapter adapter = new ContactsListAdapter(this);


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
        db = Room.databaseBuilder(getApplicationContext(), MyAppDB.class, "MyAppDB")
                .allowMainThreadQueries().build();
        conversationDao = db.conversationDao();

        RecyclerView listOfContacts = findViewById(R.id.ListOfContacts);
//        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        listOfContacts.setAdapter(adapter);
        listOfContacts.setLayoutManager(new LinearLayoutManager(this));

//        contactList = new ArrayList<>();
//        adapter.setContacts(contactList);


        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1, "Alice1","Alice1","555", "hello world", "11:00"));
        contacts.add(new Contact(2, "Alice2","Alice2","555", "hello world", "11:00"));
        contacts.add(new Contact(3, "Alice3","Alice3","555", "hello world", "11:00"));
        contacts.add(new Contact(4, "Alice4","Alice4","555", "hello world", "11:00"));
        contacts.add(new Contact(5, "Alice5","Alice5","555", "hello world", "11:00"));
        contacts.add(new Contact(6, "Alice1","Alice1","555", "hello world", "11:00"));
        contacts.add(new Contact(7, "Alice2","Alice2","555", "hello world", "11:00"));
        contacts.add(new Contact(8, "Alice3","Alice3","555", "hello world", "11:00"));
        contacts.add(new Contact(9, "Alice4","Alice4","555", "hello world", "11:00"));
        contacts.add(new Contact(10, "Alice5","Alice5","555", "hello world", "11:00"));

        adapter.setContacts(contacts);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactList.clear();
        List<Contact> contactsList = getAllContacts();
        contactList.addAll(contactsList);
        adapter.notifyDataSetChanged();
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactsList = new ArrayList<>();
        List<Conversation> conversations = conversationDao.getAllConversations();
        for (Conversation c : conversations) {
            contactsList.add(c.contact);
        }
        return contactsList;
    }
}