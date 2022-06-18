package com.example.chatapp.DAO;

import androidx.room.TypeConverter;

import com.example.chatapp.models.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Converters {
    @TypeConverter
    public String fromValuesToList(ArrayList<Message> value) {
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Message>>() {}.getType();
        return gson.toJson(value, type);
    }

    @TypeConverter
    public ArrayList<Message> toOptionValuesList(String value) {
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>() {}.getType();
        return gson.fromJson(value, type);
    }
}