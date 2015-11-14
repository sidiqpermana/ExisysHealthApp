package com.enriko.exsys.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.enriko.exsys.R;
import com.enriko.exsys.adapter.HrmAdapter;
import com.enriko.exsys.base.BaseFragment;
import com.enriko.exsys.base.VolleySingleton;
import com.enriko.exsys.model.Hrm;
import com.enriko.exsys.model.HrmItem;
import com.enriko.exsys.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HrmFragment extends BaseFragment{
	private ArrayList<HrmItem> listItem;
	private ListView lvItem;
	private ProgressBar indicator;
	private boolean isBetween = false;
	private String fdate = null;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listItem = new ArrayList<>();
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_hrm, container, false);
		initUI(view);
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initProcess();
		getHrmData("2015", "");
	}
	
	@Override
	public void initUI(View view) {
		// TODO Auto-generated method stub
		super.initUI(view);
		lvItem = (ListView)view.findViewById(R.id.lv_item);
		indicator = (ProgressBar)view.findViewById(R.id.pb_indicator);
	}
	
	@Override
	public void initProcess() {
		// TODO Auto-generated method stub
		super.initProcess();
	}
	
	@Override
	public void parsingResponse(String response) {
		// TODO Auto-generated method stub
		super.parsingResponse(response);

		listItem = Hrm.getListItem(response);
		if (listItem.size() > 0){
			HrmAdapter adapter = new HrmAdapter(getActivity(), listItem);
			lvItem.setAdapter(adapter);
		}
	}
	
	private void getHrmData(final String fdate1, final String fdate2) {
		// TODO Auto-generated method stub
		indicator.setVisibility(View.VISIBLE);
		lvItem.setVisibility(View.GONE);
		String url = null;

		if(!fdate1.equals("")&&!fdate2.equals("")){
			url = URLs.GET_HRM + "between/";
			isBetween = true;
		}else{
			url = URLs.GET_HRM + "by/year";
			fdate = fdate1;
			isBetween = false;
		}
		StringRequest request = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						utils.printLog(response);
						indicator.setVisibility(View.GONE);
						lvItem.setVisibility(View.VISIBLE);
						parsingResponse(response);
					}
					
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						indicator.setVisibility(View.GONE);
						lvItem.setVisibility(View.GONE);
						utils.showToast("Error in getting data");
					}
				}){
				@Override
				protected Map<String, String> getParams()
						throws AuthFailureError {
					// TODO Auto-generated method stub
					Map<String, String> params = new HashMap<String, String>();
					params.put("uid", preferences.getUser().userInfo.id);
					if (isBetween){
						params.put("fdate1", fdate1);
						params.put("fdate2", fdate2);
					}else{
						params.put("fdate", fdate);
					}
					return params;
				}
		};
		
		VolleySingleton.getInstance(getActivity()).addToRequestQueue(request);
	}
}
