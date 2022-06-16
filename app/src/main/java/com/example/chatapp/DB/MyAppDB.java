package com.example.chatapp.DB;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.chatapp.DAO.ConversationDao;
import com.example.chatapp.MyApp;
import com.example.chatapp.models.Conversation;

@Database(entities = {Conversation.class}, version = 1)
public abstract class MyAppDB extends RoomDatabase {

    private static volatile MyAppDB instance;
    public abstract ConversationDao conversationDao();

    public static MyAppDB getInstance(Context context) {
        if(instance == null) {
            synchronized (MyAppDB.class) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), MyAppDB.class, "MyAppDB")
                            .addCallback(prepuplateDB()).build();
                }
            }
        }
        return instance;
    }

    private static Callback prepuplateDB() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                ContentValues contentValues = new ContentValues();
//                contentValues.put();
            }
        };
    }
}
