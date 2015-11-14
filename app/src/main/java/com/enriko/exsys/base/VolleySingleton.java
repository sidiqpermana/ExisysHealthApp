package com.enriko.exsys.base;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.enriko.exsys.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class VolleySingleton {
	private static VolleySingleton volleySingleton;
	private RequestQueue mRequestQueue;
	private static Context mContext;
	
	private Cache cache = null;
	
	DisplayImageOptions displayImageOptions;
	
	private VolleySingleton(Context ctx) {
		// TODO Auto-generated constructor stub
		mContext = ctx;
		cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024);	
		mRequestQueue = getRequestQueue();
		displayImageOptions = getDisplayImageOptions();
	}
	
	public static synchronized VolleySingleton getInstance(Context context){
		if (volleySingleton == null) {
			volleySingleton = new VolleySingleton(context);
		}
		
		return volleySingleton;
	}
	
	public RequestQueue getRequestQueue(){
		if (mRequestQueue == null) {
			BasicNetwork network = new BasicNetwork(new HurlStack());
			mRequestQueue = new RequestQueue(cache, network);
			mRequestQueue.start();
		}
		
		return mRequestQueue;
	}
	
	public <T> void addToRequestQueue(Request<T> Req){
		getRequestQueue().add(Req);
	}
	
	public DisplayImageOptions getDisplayImageOptions(){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	
		return options;
	}
}
