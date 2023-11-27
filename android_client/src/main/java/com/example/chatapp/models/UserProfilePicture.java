package com.example.chatapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserProfilePicture {

    @PrimaryKey(autoGenerate = true)
    public int imageId;

    public String username;

    public String image;

    public UserProfilePicture(String username, String image) {
        this.username = username;
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
