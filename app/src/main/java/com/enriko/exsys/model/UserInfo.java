package com.enriko.exsys.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SerializedName("id")
	public String id;
	
	@SerializedName("user_login")
	public String userLogin;
	
	@SerializedName("user_password")
	public String userPassword;
	
	@SerializedName("user_email")
	public String userEmail;
	
	@SerializedName("user_type")
	public String userType;
	
	@SerializedName("user_created")
	public String userCreated;
	
	@SerializedName("user_modified")
	public String userModified;
	
	@SerializedName("user_fullname")
	public String userFullname;
	
	@SerializedName("user_role")
	public String userRole;
	
	@SerializedName("user_status")
	public String userStatus;
	
	@SerializedName("user_photo")
	public String userPhoto;
	
	@SerializedName("user_sex")
	public String userSex;
	
	@SerializedName("user_birthday")
	public String userBirthday;
	
	@SerializedName("user_address")
	public String userAddress;
	
	@SerializedName("user_disease")
	public String userDisease;

}
