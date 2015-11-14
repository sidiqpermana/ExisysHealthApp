package com.enriko.exsys.base;

import com.enriko.exsys.utils.ApplicationPreferences;
import com.enriko.exsys.utils.Utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

public class BaseActivity extends ActionBarActivity implements BaseMethod	{
	public Utils utils;
	public ApplicationPreferences preferences;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		utils = new Utils(BaseActivity.this);
		preferences = new ApplicationPreferences(BaseActivity.this);
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
