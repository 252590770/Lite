package com.i.lite.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SPTool {
    private static final String JSON_CACHE = "JSON_CACHE";

    public SPTool() {
    }

    public static void putContent(Context context, String tag, String content) {
        putString(context, tag, content);
    }

    public static String getContent(Context context, String tag) {
        return getString(context, tag);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        String value = sp.getString(key, "");
        return value;
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        int value = sp.getInt(key, -1);
        return value;
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        long value = sp.getLong(key, -1L);
        return value;
    }

    public static void putFloat(Context context, String key, float value) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        float value = sp.getFloat(key, -1.0F);
        return value;
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        boolean value = sp.getBoolean(key, false);
        return value;
    }

    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(key, 0);
        sp.edit().remove(key).apply();
    }

    public static void putJSONCache(Context context, String key, String content) {
        SharedPreferences sp = context.getSharedPreferences("JSON_CACHE", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, content);
        editor.apply();
    }

    public static String readJSONCache(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("JSON_CACHE", 0);
        String jsoncache = sp.getString(key, (String)null);
        return jsoncache;
    }

    public static void clearPreference(Context context, String name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(key != null) {
            editor.remove(key);
        } else {
            editor.clear();
        }

        editor.apply();
    }
}
