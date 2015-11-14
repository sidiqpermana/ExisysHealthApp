package com.enriko.exsys.utils;

import com.enriko.exsys.model.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ApplicationPreferences {
	public static final String PREFS_NAME = "exsyspref";
	public static final String KEY_TOKEN = "token";
	public static final String KEY_USER = "user";
	
	private SharedPreferences preferences = null;
	private Editor editor = null;
	private Context context;
	
	public ApplicationPreferences(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = preferences.edit();
	}
	
	public void setToken(String token){
		editor.putString(KEY_TOKEN, token);
		editor.commit();
	}
	
	public String getToken(){
		return preferences.getString(KEY_TOKEN, "");
	}
	
	public void setUser(String user){
		editor.putString(KEY_USER, user);
		editor.commit();
	}
	
	public User getUser(){
		return User.getUser(preferences.getString(KEY_USER, ""));
	}
	
	public void clear(){
		editor.clear();
		editor.commit();
	}
}
