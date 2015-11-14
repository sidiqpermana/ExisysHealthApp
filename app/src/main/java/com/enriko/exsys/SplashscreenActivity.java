package com.enriko.exsys;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.enriko.exsys.base.BaseActivity;

public class SplashscreenActivity extends BaseActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);

		getSupportActionBar().hide();
		new Delay().execute();
		
	}
	
	class Delay extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if (preferences.getToken().equals("")) {
				LoginActivity.toLoginActivity(SplashscreenActivity.this);
				finish();
			} else {
				HomeActivity.toHomeActivity(SplashscreenActivity.this);
				finish();
			}
		}
	}
}
