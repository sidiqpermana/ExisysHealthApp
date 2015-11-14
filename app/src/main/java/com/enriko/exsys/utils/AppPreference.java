package com.enriko.exsys.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sidiq on 15/08/2015.
 */
public class AppPreference {
    private SharedPreferences appPreference;
    private String PREF_NAME = "contect8a";
    private String KEY_SISTOLIK = "sistolik";
    private String KEY_DISTOLIK = "distolik";
    private Activity activity;

    private SharedPreferences.Editor editor;

    public AppPreference(Activity activity){
        this.activity = activity;
        appPreference = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = appPreference.edit();
    }

    public void setSistolik(int sistolik){
        editor.putInt(KEY_SISTOLIK, sistolik);
        editor.commit();
    }

    public int getSistolik(){
        return appPreference.getInt(KEY_SISTOLIK, 0);
    }

    public void setDistolik(int distolik){
        editor.putInt(KEY_DISTOLIK, distolik);
        editor.commit();
    }

    public int getDistolik(){
        return appPreference.getInt(KEY_DISTOLIK, 0);
    }
}
