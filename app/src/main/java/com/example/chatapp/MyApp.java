package com.example.chatapp;

import android.app.Application;
import android.content.Context;

import com.example.chatapp.models.User;


public class MyApp extends Application {
    private static Context context;
    private static User currentUser;
    private static String cookie;
    private static String baseUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        baseUrl = "http://10.0.2.2:5170/api/";
        context = getApplicationContext();
        currentUser = null;
    }

    public static Context getContext() {
        return context;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static String getCookie() {
        return cookie;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setCurrentUser(User currentUser) {
        MyApp.currentUser = currentUser;
    }

    public static void setCookie(String cookie) {
        MyApp.cookie = cookie;
    }

    public static void setBaseUrl(String baseUrl) {
        MyApp.baseUrl = baseUrl;
    }
}
