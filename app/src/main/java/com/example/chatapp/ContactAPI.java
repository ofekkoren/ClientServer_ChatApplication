package com.example.chatapp;

import com.example.chatapp.models.ContactToJson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ContactAPI {

    @GET("Contacts")
    Call<ArrayList<ContactToJson>> getContacts(@Header("Cookie") String session);

    @POST("Contacts")
    Call<Void> addContact(@Header("Cookie") String session, @Body ContactDTO.AddContactParams parameters);


}
