package com.enriko.exsys.model;

import java.io.Serializable;

import com.enriko.exsys.base.model.BaseModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class User extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("token")
	public String token;
	
	@SerializedName("user_info")
	public UserInfo userInfo;
	
	public static User getUser(String response){
		Gson gson = new Gson();
		User user = gson.fromJson(response, User.class);
		return user;
	}
	
}
