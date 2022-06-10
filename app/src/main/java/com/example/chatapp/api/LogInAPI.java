package com.example.chatapp.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LogInAPI {
    @POST("LogIn")
    Call<LogInResults> checkValidation(@Body LogInParams parameters);
//    Call<LogInResults> checkValidation(@Body String username, String password);
//
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
