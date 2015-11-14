package com.enriko.exsys.utils;

import com.enriko.exsys.R;

public class Constants {
	public static String APP_TAG = "exsys";
	
	public static final String RES_OK = "OK";
	public static final String RES_NOK = "NOK";
	public static final String RES_CODE_200 = "200";
	public static final String RES_CODE_400 = "400";
	public static final String RES_CODE_401 = "401";

	public static String[][] menus = new String[][]{
			{"Heart Rate", "Check your Heart Rate Today"},
			{"Blood Pressure", "Check your Blood Pressure Today"},
			{"EKG", "Check your EKG Today"},
			{"Weight", "Check your Weight Today"},
			{"Blood Glucose", "Check your Blood Glucose Today"},
			{"Cholesterol", "Check your Cholesterol Today"},
			{"SP02", "Check your SP02 Today"},
	};

	public static int[] menu_icon = new int[]{
			R.drawable.icon_health_care_heart_rate,
			R.drawable.icon_health_care_blood_pressure,
			R.drawable.icon_health_care_ekg,
			R.drawable.icon_health_care_weight,
			R.drawable.icon_health_care_blood_glucose,
			R.drawable.icon_health_care_cholesterol,
			R.drawable.icon_health_care_spo2
	};
}
