package com.example.chatapp;

public class Utils {
    public static String backendToAndroidServer(String server) {
        if (!server.toLowerCase().contains("localhost:"))
            return "http://" + server + "/";
        String serverNumber;
        String[] strings = server.split(":");
        int size = strings.length;
        for (int i = 0; i < size; i++) {
            if (strings[i].equalsIgnoreCase("localhost")) {
                server = strings[i + 1];
                break;
            }
        }
        return "http://10.0.2.2:" + server + "/";
    }

    public static String androidToBackendServer(String server) {
        if (!server.contains("http://10.0.2.2:")) {
            return server;
        }
        String str = server.replace("http://10.0.2.2:", "");
        if (str.charAt(str.length() - 1) == '/')
            return "localhost:"+str.substring(0, str.length() - 1);
        return str;
    }
}
