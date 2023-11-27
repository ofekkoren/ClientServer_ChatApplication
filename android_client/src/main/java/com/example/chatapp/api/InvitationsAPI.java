package com.example.chatapp.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InvitationsAPI {
    @POST("api/invitations")
    Call<Void> invitation(@Header("Cookie") String session, @Body InvitationsAPI.InvitationParams parameters);

    public class InvitationParams {
        public String from;
        public String to;
        public String fromServer;

        public InvitationParams(String from, String to, String fromServer) {
            this.from = from;
            this.to = to;
            this.fromServer = fromServer;
        }
    }
}
