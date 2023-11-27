package com.example.chatapp.DAO;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chatapp.models.Conversation;
import com.example.chatapp.models.UserProfilePicture;

@Dao
public interface ProfileImageDao {

    @Nullable
    @Query("SELECT * FROM UserProfilePicture WHERE username = :username")
    UserProfilePicture getImage(String username);

    @Insert
    void insert(UserProfilePicture...userProfilePictures);
}
