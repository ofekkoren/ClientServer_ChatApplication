package com.example.chatapp.api;

import com.example.chatapp.DTO.ContactDTO;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.ContactToJson;
import com.example.chatapp.models.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContactAPI {

    @GET("api/Contacts")
    Call<ArrayList<ContactToJson>> getContacts(@Header("Cookie") String session);

    @POST("api/Contacts")
    Call<Void> addContact(@Header("Cookie") String session, @Body ContactDTO.AddContactParams parameters);

    @GET("api/Contacts/{id}")
    Call<Contact> getContact(@Path("id") String id, @Header("Cookie") String session);

    @PUT("api/Contacts/{id}")
    Call<Void> editContact(@Path("id") String id, @Header("Cookie") String session,
                           @Body ContactDTO.EditContactParams parameters);

    @DELETE("api/Contacts/{id}")
    Call<Void> deleteContact(@Path("id") String id, @Header("Cookie") String session);

    @GET("api/Contacts/{id}/messages")
    Call<ArrayList<Message>> getMessages(@Path("id") String id, @Header("Cookie") String session);

    @POST("api/Contacts/{id}/messages")
    Call<Void> AddMessage(@Path("id") String id, @Header("Cookie") String session,
                          @Body ContactDTO.MessageContent parameter);

    @GET("api/Contacts/{id}/messages/{id2}")
    Call<Message> getMessage(@Path("id") String id, @Path("id2") int id2, @Header("Cookie") String session);

    @PUT("api/Contacts/{id}/messages/{id2}")
    Call<Void> editMessage(@Path("id") String id, @Path("id2") int id2, @Header("Cookie") String session,
                           @Body ContactDTO.MessageContent parameter);

    @DELETE("api/Contacts/{id}/messages/{id2}")
    Call<Void> deleteMessage(@Path("id") String id, @Path("id2") int id2, @Header("Cookie") String session);
}
