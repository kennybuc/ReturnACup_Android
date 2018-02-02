package com.app.flexivendsymbol.api.domain;

import com.app.flexivendsymbol.helpers.LocalStorage;
import com.google.gson.Gson;

public class AppUser {

    private static final String KEY_USER = "KEY_USER";

    public String user_id;
    public String user_name;
    public String company_name;
    public String email;
    public String password;

    public void saveToStorage() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        LocalStorage.getInstance().put(KEY_USER, json);
    }

    public static AppUser getSavedUser() {
        Gson gson = new Gson();
        String json = LocalStorage.getInstance().getStringPreference(KEY_USER);
        if (json != null) {
            return gson.fromJson(json, AppUser.class);
        }
        return null;
    }

    public static void logoutUser() {
        LocalStorage.getInstance().put(KEY_USER, null);
    }
}
