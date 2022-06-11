package com.example.chatapp.api;

import com.example.chatapp.models.ContactToJson;
import com.example.chatapp.models.Conversation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UsersAPI {
    @POST("Users/GetAllConversationsOfUser")
    Call<List<Conversation>> signUp(@Body idClass parameters);

    @GET("Contacts")
    Call<ArrayList<ContactToJson>> getContacts(@Header("Cookie") String session);

}
