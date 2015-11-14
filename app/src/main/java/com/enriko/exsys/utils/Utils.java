package com.enriko.exsys.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	Activity activity;
	
	public Utils(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}
	
	public void showToast(String message){
		Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
	}
	
	public void printLog(String message){
		Log.d(Constants.APP_TAG, message);
	}
	
	public String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateandTime = sdf.format(new Date());
		return currentDateandTime;
	}
}
