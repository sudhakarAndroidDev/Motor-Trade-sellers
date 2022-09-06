package com.app.compare_my_trade.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    private static String tokan;
    private static String status;
    private static int length;
    private static String id;
    private static Context context;

    public  PreferenceUtils(){

    }

    public static boolean saveTokan(String token, Context context) {
        PreferenceUtils.tokan = tokan;
        PreferenceUtils.context = context;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_TOKAN, token);
        prefsEditor.apply();
        return true;
    }

    public static String getTokan(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_TOKAN, null);
    }

    public static boolean saveStatus(String status, Context context) {
        PreferenceUtils.status = status;
        PreferenceUtils.context = context;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_STATUS, status);
        prefsEditor.apply();
        return true;
    }

    public static String getStatus(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_STATUS, null);
    }


    public static boolean saveLength(int length, Context context) {
        PreferenceUtils.length = length;
        PreferenceUtils.context = context;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(String.valueOf(Constants.KEY_LENGTH), length);
        prefsEditor.apply();
        return true;
    }

    public static int getLength(Context context) {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(String.valueOf(Constants.KEY_LENGTH), 0);
    }



}
