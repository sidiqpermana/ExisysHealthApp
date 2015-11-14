package com.enriko.exsys.model;

import com.enriko.exsys.base.model.BaseModel;
import com.google.gson.Gson;

public class GeneralResponse extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static GeneralResponse getGeneralResponse(String response){
		Gson gson = new Gson();
		GeneralResponse generalResponse = gson.fromJson(response, GeneralResponse.class);
		return generalResponse;
	}
}
