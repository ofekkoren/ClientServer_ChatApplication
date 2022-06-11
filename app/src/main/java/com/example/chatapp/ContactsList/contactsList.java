package com.example.chatapp.ContactsList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.chatapp.R;
import com.example.chatapp.adapters.ContactsListAdapter;
import com.example.chatapp.entities.Contact;

import java.util.ArrayList;
import java.util.List;

public class contactsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        RecyclerView listOfContacts = findViewById(R.id.ListOfContacts);
        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        listOfContacts.setAdapter(adapter);
        listOfContacts.setLayoutManager(new LinearLayoutManager(this));

        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1, "Alice1","Alice1","555", "hello world", "11:00"));
        contacts.add(new Contact(2, "Alice2","Alice2","555", "hello world", "11:00"));
        contacts.add(new Contact(3, "Alice3","Alice3","555", "hello world", "11:00"));
        contacts.add(new Contact(4, "Alice4","Alice4","555", "hello world", "11:00"));
        contacts.add(new Contact(5, "Alice5","Alice5","555", "hello world", "11:00"));
        adapter.setContacts(contacts);
    }
}