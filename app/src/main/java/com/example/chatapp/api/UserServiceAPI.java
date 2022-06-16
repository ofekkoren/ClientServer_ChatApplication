//package com.example.chatapp.api;
//
//import com.example.chatapp.DTO.usersDTO;
//import com.example.chatapp.MyApp;
//import com.example.chatapp.models.Conversation;
//import com.example.chatapp.models.User;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class UserServiceAPI {
//    Retrofit retrofit;
//    UsersAPI userAPI;
//    List<Conversation> conversationList;
//
//    public UserServiceAPI() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(MyApp.getBaseUrl())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        userAPI = retrofit.create(UsersAPI.class);
//    }
//
////    public List<Conversation> getAllConversationsOfUser() {
////        usersDTO.IdClass param = new usersDTO.IdClass(MyApp.getCurrentUser().getId());
////        Call<List<Conversation>> userRequest = userAPI.getAllConversations(MyApp.getCookie(),param);
////        userRequest.enqueue(new Callback<List<Conversation>>() {
////            @Override
////            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
////                conversationList = response.body();
//////                MyApp.setCurrentUser(user1);
////            }
////
////            @Override
////            public void onFailure(Call<List<Conversation>> call, Throwable t) {
////            }
////        });
////    }
//}
