package com.enriko.exsys.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import com.enriko.exsys.utils.ApplicationPreferences;
import com.enriko.exsys.utils.Utils;
import com.google.gson.Gson;

public class BaseFragment extends Fragment implements BaseMethod{
	public ApplicationPreferences preferences;
	public Utils utils;
	public Gson gson;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		utils = new Utils(getActivity());
		preferences = new ApplicationPreferences(getActivity());
		gson = new Gson();
	}
	
	@Override
	public void initIntent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initUI(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initLib() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initRequest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initProcess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parsingResponse(String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initLoadmoreUI(ListView lv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initLoadmoreEvent() {
		// TODO Auto-generated method stub
		
	}

}
