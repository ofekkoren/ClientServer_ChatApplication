package com.example.chatapp.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.models.Conversation;

@Database(entities = {Conversation.class}, version = 1)
public abstract class MyAppDB extends RoomDatabase {
    public abstract ConversationDao conversationDao();
}
