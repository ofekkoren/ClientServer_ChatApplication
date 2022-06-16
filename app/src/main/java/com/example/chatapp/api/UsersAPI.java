package com.example.chatapp.api;

import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.models.ContactToJson;
import com.example.chatapp.models.Conversation;
import com.example.chatapp.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UsersAPI {
    @POST("Users")
    Call<User> getCurrentUser(@Header("Cookie") String session);

    @POST("Users/GetAllConversationsOfUser")
    Call<List<Conversation>> getAllConversations(@Header("Cookie") String session, @Body usersDTO.IdClass parameter);

    @POST("Users/MoveConversationToTopList")
    Call<ArrayList<ContactToJson>> moveConversationToTopList(@Header("Cookie") String session, @Body usersDTO.parametersForMoveConversation parameters);

    @POST("Users/GetConversation")
    Call<Conversation> getConversation(@Header("Cookie") String session, @Body usersDTO.IdClass parameter);
}
