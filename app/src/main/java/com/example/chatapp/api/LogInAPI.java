package com.example.chatapp.api;

import com.example.chatapp.models.ContactToJson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LogInAPI {
    @POST("api/LogIn")
    Call<LogInResults> checkValidation(@Body LogInParams parameters);

/*    @GET("api/Contacts")
    Call <ArrayList<com.example.chatapp.models.ContactToJson>> getContacts(@Header("Cookie") String session);*/

/*    public class ContactToJson{
        public String id ;
        public String name ;
        public String server ;
        public String last;
        public String lastdate ;

        public String getId() {
            return id;
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
    }*/


    public class LogInParams {
        public String username;
        public String password;

        public LogInParams(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public class LogInResults{
        public String username;
        public String password;

        public LogInResults(String usernameV, String passwordV) {
            this.username = usernameV;
            this.password = passwordV;
        }

        public String getUsernameV() {
            return username;
        }

        public String getPasswordV() {
            return password;
        }
    }

}
