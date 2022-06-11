package com.example.chatapp;

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

    @GET("Contacts")
    Call<ArrayList<ContactToJson>> getContacts(@Header("Cookie") String session);

    @POST("Contacts")
    Call<Void> addContact(@Header("Cookie") String session, @Body ContactDTO.AddContactParams parameters);

    @GET("Contacts/{id}")
    Call<Contact> getContact(@Path("id") String id, @Header("Cookie") String session);

    @PUT("Contacts/{id}")
    Call<Void> editContact(@Path("id") String id, @Header("Cookie") String session,
                           @Body ContactDTO.EditContactParams parameters);

    @DELETE("Contacts/{id}")
    Call<Void> deleteContact(@Path("id") String id, @Header("Cookie") String session);

    @GET("Contacts/{id}/messages")
    Call<ArrayList<Message>> getMessages(@Path("id") String id, @Header("Cookie") String session);

    @POST("Contacts/{id}/messages")
    Call<Void> AddMessage(@Path("id") String id, @Header("Cookie") String session,
                          @Body ContactDTO.MessageContent parameter);

    @GET("Contacts/{id}/messages/{id2}")
    Call<Message> getMessage(@Path("id") String id, @Path("id2") int id2, @Header("Cookie") String session);

    @PUT("Contacts/{id}/messages/{id2}")
    Call<Void> editMessage(@Path("id") String id, @Path("id2") int id2, @Header("Cookie") String session,
                           @Body ContactDTO.MessageContent parameter);

    @DELETE("Contacts/{id}/messages/{id2}")
    Call<Void> deleteMessage(@Path("id") String id, @Path("id2") int id2, @Header("Cookie") String session);
}
