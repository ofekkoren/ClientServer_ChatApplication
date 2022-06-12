package com.example.chatapp.api;

import com.example.chatapp.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TransferAPI {

    @POST("transfer")
    Call<Void> transferMessage(@Header("Cookie") String session, @Body TransferParams parameters);

    public class TransferParams {
        public String from;
        public String to;
        public String content;

        public TransferParams(String from, String to, String content) {
            this.from = from;
            this.to = to;
            this.content = content;
        }
    }
}
