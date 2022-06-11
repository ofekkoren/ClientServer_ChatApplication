package com.example.chatapp.api;

import com.example.chatapp.models.ContactToJson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignUpAPI {

    @POST("SignUp")
    Call<signUpResults> signUp(@Body signUpParams parameters);

    //TODO delete!!!
/*    @GET("Contacts")
    Call <ArrayList<ContactToJson>> getContacts(@Header("Cookie") String session);*/

    public class signUpParams {
        public String username;
        public String nickname;
        public String password;
        public String repeatPassword;

        public signUpParams(String username, String nickname, String password, String repeatPassword) {
            this.username = username;
            this.nickname = nickname;
            this.password = password;
            this.repeatPassword = repeatPassword;
        }
    }

    public class signUpResults{
        public String usernameV;
        public String nicknameV;
        public String passwordV;
        public String repeatPasswordV;

        public signUpResults(String usernameV, String nicknameV, String passwordV, String repeatPasswordV) {
            this.usernameV = usernameV;
            this.nicknameV = nicknameV;
            this.passwordV = passwordV;
            this.repeatPasswordV = repeatPasswordV;
        }

        public String getUsernameV() {
            return usernameV;
        }

        public String getNicknameV() {
            return nicknameV;
        }

        public String getPasswordV() {
            return passwordV;
        }

        public String getRepeatPasswordV() {
            return repeatPasswordV;
        }
    }

}
