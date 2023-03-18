package com.keyboard.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPrafrances {

    private Context mContext;

    private static SharedPreferences mySharedPrafrances;
    private static SharedPreferences.Editor mEditor;

    private static String key_theme = "key_theme";
    private static String key_theme_color = "key_theme_color";
    private static String key_custom_theme = "key_custom_theme";

    private static String key_show_sugession = "key_how_sugession";
    private static String key_sound = "key_sound";
    private static String key_vibration = "key_vibration";

    private static String key_sound_name = "key_sound_name";
    private static String key_keyboard_name = "key_keyboard_name";


//    isCustomTheme


    public MyPrafrances(Context context){
        this.mContext = context;
        this.mySharedPrafrances = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = this.mySharedPrafrances.edit();
    }

    public static String getTheme(){
        return mySharedPrafrances.getString(key_theme,"ic_1");
    }

    public static void setTheme(String pram){
        mEditor.putString(key_theme,pram);
        mEditor.apply();
    }

    public static String getThemeColor(){
        return mySharedPrafrances.getString(key_theme_color,"white");
    }

    public static void setThemeColor(String pram){
        mEditor.putString(key_theme_color,pram);
        mEditor.apply();
    }

    public static Boolean getCustomTheme(){
        return mySharedPrafrances.getBoolean(key_custom_theme,false);
    }

    public static void setCustomTheme(Boolean pram){
        mEditor.putBoolean(key_custom_theme,pram);
        mEditor.apply();
    }

    public static Boolean getShowSugession(){
        return mySharedPrafrances.getBoolean(key_show_sugession,true);
    }

    public static void setShowSugession(Boolean pram){
        mEditor.putBoolean(key_show_sugession,pram);
        mEditor.apply();
    }

    public static Boolean getSound(){
        return mySharedPrafrances.getBoolean(key_sound,true);
    }

    public static void setSound(Boolean pram){
        mEditor.putBoolean(key_sound,pram);
        mEditor.apply();
    }

    public static Boolean getVibration(){
        return mySharedPrafrances.getBoolean(key_vibration,false);
    }

    public static void setVibration(Boolean pram){
        mEditor.putBoolean(key_vibration,pram);
        mEditor.apply();
    }
    public static String getSoundName(){
        return mySharedPrafrances.getString(key_sound_name,"android.mp3");
    }

    public static void setSoundName(String pram){
        mEditor.putString(key_sound_name,pram);
        mEditor.apply();
    }

    public static String getLastOpenedKeyboard(){
        return mySharedPrafrances.getString(key_keyboard_name,"android.mp3");
    }

    public static void setLastOpenedKeyboard(String pram){
        mEditor.putString(key_keyboard_name,pram);
        mEditor.apply();
    }


}
