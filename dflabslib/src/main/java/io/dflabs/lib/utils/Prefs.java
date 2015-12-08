package io.dflabs.lib.utils;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Created by Daniel García Alvarado on 6/21/15.
 * GnpAjuste - danielgarcia
 */
@SuppressWarnings("unused")
public class Prefs {

    private static String PREFS_NAME;
    private static Prefs singleton;
    private Context context;

    public Prefs(Context context) {
        this.context = context;
        PREFS_NAME = context.getPackageName();
    }

    public static void setDefaultContext(Context context) {
        with(context);
    }

    public static Prefs instance() {
        if (singleton == null || singleton.context == null) {
            throw new IllegalArgumentException("Call setDefaultContext(context) first");
        }
        return singleton;
    }

    public static Prefs with(Context context) {
        if (singleton == null) {
            synchronized (Prefs.class) {
                if (singleton == null) {
                    if (context == null) {
                        throw new IllegalArgumentException("Context must not be null");
                    }
                    singleton = new Prefs(context);
                    singleton.context = context.getApplicationContext();
                }
            }
        }
        return singleton;
    }

    /**
     * @param key Key for preferences
     * @return intValue
     */
    public int integer(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt(key, -1);
    }

    public int integer(String key, int defaultValue) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    public String string(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(key, "");
    }

    public String string(String key, String defaultValue) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public boolean bool(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getBoolean(key, false);
    }

    public boolean bool(String key, boolean defaultValue) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    public long number(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getLong(key, -1L);
    }

    public long number(String key, long defaultValue) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getLong(key, defaultValue);
    }

    public Object object(String key, Class<?> fromClass) {
        try {
            String json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(key, null);
            if (json != null) {
                Gson gson = new Gson();
                return gson.fromJson(json, fromClass);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public void putInt(String key, int value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(key, value)
                .commit();
    }

    public void putBool(String key, boolean value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, value)
                .commit();
    }

    public void putString(String key, String value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .commit();
    }

    public void putLong(String key, long value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putLong(key, value)
                .commit();
    }

    public void putObject(String key, Object value, Class<?> fromClass) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(value, fromClass);
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putString(key, json)
                    .commit();
        } catch (Exception e) {
            throw new IllegalArgumentException("Only plain objects are supported");
        }
    }
}
