package com.example.chatapp.models;

public class Contact {

    private int id;
    private String username;
    private String name;
    private String server;
    private String last;
    private String lastdate;

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
