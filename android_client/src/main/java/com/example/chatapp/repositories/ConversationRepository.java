//package com.example.chatapp.repositories;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.chatapp.DAO.ConversationDao;
//import com.example.chatapp.DTO.usersDTO;
//import com.example.chatapp.MyApp;
//import com.example.chatapp.api.UserServiceAPI;
//import com.example.chatapp.api.UsersAPI;
//import com.example.chatapp.models.Contact;
//import com.example.chatapp.models.Conversation;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.PrimitiveIterator;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ConversationRepository {
//    private ConversationDao conversationDao;
//    private ConversationsListData conversationsListData;
//    private UserServiceAPI userServiceAPI;
//    private Retrofit retrofit;
//    UsersAPI userAPI;
//
//
//    public ConversationRepository() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(MyApp.getBaseUrl())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        userAPI = retrofit.create(UsersAPI.class);
//        conversationsListData = new ConversationsListData();
//    }
//
//    class ConversationsListData extends MutableLiveData<List<Conversation>> {
//        public ConversationsListData() {
//            super();
////            List<Conversation> conversations = new ArrayList<>();
////            List<Conversation> conversations = userServiceAPI.getAllConversationsOfUser();
//            usersDTO.IdClass param = new usersDTO.IdClass(MyApp.getCurrentUser().getId());
//            Call<List<Conversation>> userRequest = userAPI.getAllConversations(MyApp.getCookie(), param);
//            userRequest.enqueue(new Callback<List<Conversation>>() {
//                @Override
//                public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
//                    List<Conversation> conversationList = response.body();
//                    setValue(conversationList);
////                MyApp.setCurrentUser(user1);
//                }
//
//                @Override
//                public void onFailure(Call<List<Conversation>> call, Throwable t) {
//                }
//            });
////        contacts.add(new Contact(1, "Alice1","Alice1","555", "hello world", "11:00"));
////        contacts.add(new Contact(2, "Alice2","Alice2","555", "hello world", "11:00"));
////        contacts.add(new Contact(3, "Alice3","Alice3","555", "hello world", "11:00"));
////        contacts.add(new Contact(4, "Alice4","Alice4","555", "hello world", "11:00"));
////        contacts.add(new Contact(5, "Alice5","Alice5","555", "hello world", "11:00"));
////        contacts.add(new Contact(6, "Alice1","Alice1","555", "hello world", "11:00"));
////        contacts.add(new Contact(7, "Alice2","Alice2","555", "hello world", "11:00"));
////        contacts.add(new Contact(8, "Alice3","Alice3","555", "hello world", "11:00"));
////        contacts.add(new Contact(9, "Alice4","Alice4","555", "hello world", "11:00"));
////        contacts.add(new Contact(10, "Alice5","Alice5","555", "hello world", "11:00"));
//
////            setValue(conversations);
//        }
//
//        @Override
//        protected void onActive() {
//            super.onActive();
////            new Thread(() -> {
////                conversationsListData.postValue(conversationDao.getAllConversations());
////            }).start();
//        }
//    }
//
//    public LiveData<List<Conversation>> getAllConversations() {
//        return conversationsListData;
//    }
//}
