package com.enriko.exsys.base.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("status")
	public String status;
	
	@SerializedName("rescode")
	public String resCode;
	
	@SerializedName("message")
	public String message;

	@SerializedName("data_count")
	public int dataCount;
	
}
