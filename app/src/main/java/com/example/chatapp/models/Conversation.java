package com.example.chatapp.models;

import java.util.ArrayList;

public class Conversation {

    private String ConversationId ;
    private ArrayList<Message> messages ;
    private Contact contact ;

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
}
