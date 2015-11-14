package com.enriko.exsys.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class VitalSignItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("ekg")
	public String ekg;
	
	@SerializedName("hrm")
	public String hrm;
	
	@SerializedName("bpm")
	public String bpm;
	
	@SerializedName("weight")
	public String weight;
	
	@SerializedName("tanggal")
	public String tanggal;
}
