package com.example.chatapp.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.chatapp.DAO.Converters;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Message;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Conversation {

    @PrimaryKey(autoGenerate = true)
    public int conId;

    public String ConversationId;

    @TypeConverters(Converters.class)
    public ArrayList<Message> messages;

    @Embedded
    public Contact contact;

    public Conversation() {
    }

    public Conversation(String conversationId, ArrayList<Message> messages, Contact contact) {
        ConversationId = conversationId;
        this.messages = messages;
        this.contact = contact;
    }

    public String getConversationId() {
        return ConversationId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
