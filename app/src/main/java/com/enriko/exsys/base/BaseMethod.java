package com.enriko.exsys.base;

import android.view.View;
import android.widget.ListView;

public interface BaseMethod {
	public void initIntent();
	public void initUI();
	public void initUI(View view);
	public void initLib();
	public void initEvent();
	public void initRequest();
	public void initProcess();
	public void parsingResponse(String response);
	public void initLoadmoreUI(ListView lv);
	public void initLoadmoreEvent();
}
