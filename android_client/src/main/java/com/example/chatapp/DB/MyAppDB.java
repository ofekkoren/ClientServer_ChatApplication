package com.example.chatapp.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.DAO.Converters;
import com.example.chatapp.DAO.ProfileImageDao;
import com.example.chatapp.models.Conversation;
import com.example.chatapp.models.UserProfilePicture;

@Database(entities = {Conversation.class, UserProfilePicture.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MyAppDB extends RoomDatabase {

    private static volatile MyAppDB instance;
    public abstract ConversationDao conversationDao();
    public abstract ProfileImageDao profileImageDao();

    public static MyAppDB getInstance(Context context) {
        if(instance == null) {
            instance = buildDBInstance(context);
        }
        return instance;
    }

    private static MyAppDB buildDBInstance(Context context) {
        return Room.databaseBuilder(context, MyAppDB.class, "MyAppDB")
                            .allowMainThreadQueries().build();
    }
}
