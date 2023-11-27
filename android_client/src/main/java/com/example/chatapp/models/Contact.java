package com.example.chatapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String name;
    private String server;
    private String last;
    private String lastdate;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public Contact(int id, String username, String name, String server, String last, String lastdate) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getServer() {
        return server;
    }

    public String getLast() {
        return last;
    }

    public String getLastdate() {
        return lastdate;
    }
}
