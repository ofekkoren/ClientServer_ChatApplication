package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatapp.ContactsList.contactsList;
import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.DB.MyAppDB;
import com.example.chatapp.DTO.ContactDTO;
import com.example.chatapp.DTO.usersDTO;
import com.example.chatapp.api.ContactAPI;
import com.example.chatapp.api.InvitationsAPI;
import com.example.chatapp.api.LogInAPI;
import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Conversation;
import com.example.chatapp.models.Message;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;

public class AddNewContact extends AppCompatActivity {
    Retrofit retrofit;
//    UsersAPI usersAPI;
    Retrofit contactRetrofit;
    InvitationsAPI invitationsAPI;
    ContactAPI contactAPI;
    private MyAppDB db;
    private ConversationDao conversationDao;

    public AddNewContact() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        invitationsAPI = retrofit.create(InvitationsAPI.class);

        contactRetrofit = new Retrofit.Builder()
                .baseUrl(MyApp.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        contactAPI = contactRetrofit.create(ContactAPI.class);

//        retrofit = new Retrofit.Builder()
//                .baseUrl(MyApp.getBaseUrl())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        invitationsAPI = retrofit.create(LogInAPI.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
        Intent activityIntent = getIntent();
        db = MyAppDB.getInstance(getApplicationContext());
//        db = Room.databaseBuilder(getApplicationContext(), MyAppDB.class, "MyAppDB")
//                .allowMainThreadQueries().build();
        conversationDao = db.conversationDao();

        Button submitNewContactDetails = findViewById(R.id.SubmitNewContactDetails);
        submitNewContactDetails.setOnClickListener(view-> {
            EditText newContactsUsername = findViewById(R.id.newContactsUsername);
            String username = newContactsUsername.getText().toString();
            EditText newContactsNickname = findViewById(R.id.newContactsNickname);
            String nickname = newContactsNickname.getText().toString();
            EditText newContactsServer = findViewById(R.id.newContactsServer);
            String server = newContactsServer.getText().toString();
            TextView addNewContactValidationMessage = findViewById(R.id.addNewContactValidationMessage);

            addNewContactValidationMessage.setText("");
            addNewContactValidationMessage.setVisibility(View.VISIBLE);

            if(username.equals("") || nickname.equals("") || server.equals("")) {
                addNewContactValidationMessage.setText("All fields must be filled!");
                addNewContactValidationMessage.setVisibility(View.VISIBLE);
                return;
            }
            if(username.equals(MyApp.getCurrentUser().getId())) {
                addNewContactValidationMessage.setText("You can't add yourself honey");
                addNewContactValidationMessage.setVisibility(View.VISIBLE);
                return;
            }
            List<Conversation> conversationList = conversationDao.getAllConversations();
            boolean isValidUser = true;
            for (Conversation c : conversationList) {
                if(c.contact.getUsername() == username) {
                    addNewContactValidationMessage.setText("This user is already talking with you!");
                    addNewContactValidationMessage.setVisibility(View.VISIBLE);
                    isValidUser = false;
                    break;
                }
            }

            InvitationsAPI.InvitationParams params = new InvitationsAPI.InvitationParams(MyApp.getCurrentUser().getId(), username, "localhost:5170");
            Call<Void> invitationRequest = invitationsAPI.invitation(MyApp.getCookie(), params);
            invitationRequest.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                   response.body();
                   if(response.code() == 400) {
                       addNewContactValidationMessage.setText("Invalid user!");
                       addNewContactValidationMessage.setVisibility(View.VISIBLE);
                       return;
                   }
                   //todo - check status
                    Contact contact = new Contact(0,username,nickname,server, "", "");
                    String conId = MyApp.getCurrentUser().getId() + username;
                    Conversation conversation = new Conversation(conId,new ArrayList<Message>(), contact);
//                    conversationDao.insert(conversation);
                    ContactDTO.AddContactParams parameters = new ContactDTO.AddContactParams(username,nickname,server);
                    Call<Void> newContactRequest = contactAPI.addContact(MyApp.getCookie(), parameters);
                    newContactRequest.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 404) {
                                return;
                            }
                            addNewContactValidationMessage.setText("User added successfully :)");
                            addNewContactValidationMessage.setVisibility(View.VISIBLE);
                            List<Conversation> conversations = conversationDao.getAllConversations();
//                            conversationDao.deleteAll(conversationDao.getAllConversations());
                            for (Conversation c : conversations) {
                                conversationDao.delete(c);
                            }
                            conversationDao.insert(conversation);
                            for (Conversation c : conversations) {
                                conversationDao.insert(c);
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
//            finish();
//            Intent i = new Intent(this, contactsList.class);
//            startActivity(i);
        });
    }
}