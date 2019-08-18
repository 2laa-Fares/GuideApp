package com.example.a10.guideapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a10.guideapplication.app.App;

public class PrefUtils {

    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
    }

    public static void storeKeys(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getKeys(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }
    public static void clear(){
        SharedPreferences.Editor editor = getSharedPreferences(App.getContext()).edit();
        editor.clear();
        editor.commit();
    }
    public static void remove(String key){
        SharedPreferences.Editor editor = getSharedPreferences(App.getContext()).edit();
        editor.remove(key);
        editor.apply();
    }
}
