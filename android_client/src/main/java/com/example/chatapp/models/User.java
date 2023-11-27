package com.example.chatapp.models;

import java.util.ArrayList;

public class User {
    private String id ;
    private String name ;
    private String password ;
    private ArrayList<Conversation> conversations;

    public User(String id, String name, String password, ArrayList<Conversation> conversations) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.conversations = conversations;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }
}
