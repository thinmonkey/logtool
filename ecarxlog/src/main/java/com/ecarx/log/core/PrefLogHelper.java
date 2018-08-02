package com.ecarx.log.core;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefLogHelper {

    protected static final String PREF_NAME = "PrefLogHelper";
    protected static SharedPreferences sSettingsPreferences;
    protected static PrefLogHelper instance;
    private static Context context;

    protected PrefLogHelper() {
        sSettingsPreferences = context.getSharedPreferences(PREF_NAME, 0);
    }

    public static PrefLogHelper getInstance() {
        if (instance == null) {
            instance = new PrefLogHelper();
        }

        return instance;
    }

    public static void init(Context context){
        PrefLogHelper.context = context;
    }

    public String getString(String key) {
        return sSettingsPreferences.getString(key, null);
    }

    public String getString(String key, String defaultStr) {
        return sSettingsPreferences.getString(key, defaultStr);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sSettingsPreferences.getBoolean(key, defaultValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sSettingsPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sSettingsPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sSettingsPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sSettingsPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putDouble(String key, double value) {
        SharedPreferences.Editor editor = sSettingsPreferences.edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.commit();
    }

    public Double getDouble(String key, double defaultValue) {
        long value = sSettingsPreferences.getLong(key, Double.doubleToRawLongBits(defaultValue));
        return Double.valueOf(Double.longBitsToDouble(value));
    }

    public int getInt(String key, int defaultValue) {
        return sSettingsPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return sSettingsPreferences.getLong(key, defaultValue);
    }



}
