package com.example.chatapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.chatapp.models.Contact;
import com.example.chatapp.models.Conversation;

import java.util.List;

@Dao
public interface ConversationDao {

    @Query("SELECT * FROM conversation ORDER BY lastdate DESC,conId DESC")
    List<Conversation> getAllConversations();

    @Query("SELECT * FROM conversation WHERE username = :conversationId")
    Conversation getConversation(String conversationId);

/*    @Query("SELECT * FROM Conversation ORDER BY lastdate DESC,id DESC")
    List<Conversation> getAllConversationsByDate();*/

    @Insert
    void insert(Conversation...conversations);

    @Update
    void update(Conversation...conversations);

    @Delete
    void delete(Conversation...conversations);

    @Delete
    void deleteAll(List<Conversation> conversationList);

}
