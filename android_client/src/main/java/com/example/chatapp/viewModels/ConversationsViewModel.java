//package com.example.chatapp.viewModels;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.chatapp.ContactsList.contactsList;
//import com.example.chatapp.models.Contact;
//import com.example.chatapp.models.Conversation;
//import com.example.chatapp.repositories.ConversationRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ConversationsViewModel extends ViewModel {
//    // Manages the interaction in front of db, server.
//    private ConversationRepository conversationRepository;
//    private LiveData<List<Conversation>> conversations;
//
//    public ConversationsViewModel() {
//        conversationRepository = new ConversationRepository();
//        conversations = conversationRepository.getAllConversations();
//
//    }
////
//    public LiveData<List<Conversation>> getAllConversations() {
//        return conversations;
////        LiveData<List<Contact>> contactsList;
////        for (Conversation c : conversations) {
////            contactsList.add(c.contact);
////        }
////        return contactsList;
//    }
////
////    public void addNewContact(Contact contact) { contactsRepository.add(contact); }
//
//}
