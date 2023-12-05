package com.example.duan1bookapp.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class CustomerDataManager {
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER = "user";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public CustomerDataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveUser(Customer user) {
        String userJson = gson.toJson(user);
        sharedPreferences.edit().putString(KEY_USER, userJson).apply();
    }

    public Customer getUser() {
        String userJson = sharedPreferences.getString(KEY_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, Customer.class);
        }
        return null;
    }
}
