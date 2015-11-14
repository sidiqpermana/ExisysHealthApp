package com.enriko.exsys.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.enriko.exsys.base.model.BaseModel;
import com.enriko.exsys.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class VitalSign extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("data")
	public ArrayList<VitalSignItem> listItem = new ArrayList<VitalSignItem>();
	
	public static ArrayList<VitalSignItem> getListItem(String response){
		ArrayList<VitalSignItem> list = null;
		Gson gson = new Gson();
		VitalSign vitalSign = gson.fromJson(response, VitalSign.class);
		if (vitalSign.status.equals(Constants.RES_OK)) {
			list = vitalSign.listItem;
		}
		return list;
	}
}
