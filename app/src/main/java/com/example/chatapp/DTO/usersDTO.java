package com.example.chatapp.DTO;

public class usersDTO {

    public static class IdClass
    {
        public String id;
        public IdClass(String id) {
            this.id = id;
        }
    }
    public static class parametersForMoveConversation
    {
        public String id;
        public String username;
        public parametersForMoveConversation(String id, String username) {
            this.id = id;
            this.username = username;
        }
    }

}
