package com.example.chatapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private String content ;
    private String created ;
    private boolean sent ;

    public Message(int id, String content, String created, boolean sent) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.sent = sent;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        return created;
    }

    public boolean isSent() {
        return sent;
    }
}
