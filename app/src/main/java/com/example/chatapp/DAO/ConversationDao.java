package com.example.chatapp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chatapp.models.Conversation;

import java.util.List;

@Dao
public interface ConversationDao {

    @Query("SELECT * FROM conversation")
    List<Conversation> getAllConversations();

    @Query("SELECT * FROM conversation WHERE id = :conversationId")
    Conversation getConversation(String conversationId);

    @Insert
    void insert(Conversation...conversations);

    @Update
    void update(Conversation...conversations);

//    @Delete
//    void delete(Contact...contacts);
}
