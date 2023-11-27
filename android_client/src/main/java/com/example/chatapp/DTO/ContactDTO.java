package com.example.chatapp.DTO;

public class ContactDTO {

    public static class AddContactParams {
        public String id;
        public String name;
        public String server;

        public AddContactParams(String id, String name, String server) {
            this.id = id;
            this.name = name;
            this.server = server;
        }
    }

    public static class EditContactParams {
        public String name;
        public String server;

        public EditContactParams(String name, String server) {
            this.name = name;
            this.server = server;
        }
    }

    public static class MessageContent {
        public String content;

        public MessageContent(String content) {
            this.content = content;
        }
    }
}
