package com.example.chatapp;

public class ContactDTO {

    public static class AddContactParams{
        public String id ;
        public String name ;
        public String server;

        public AddContactParams(String id, String name, String server) {
            this.id = id;
            this.name = name;
            this.server = server;
        }
    }
}
