package com.enriko.exsys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.enriko.exsys.base.BaseActivity;
import com.enriko.exsys.base.VolleySingleton;
import com.enriko.exsys.model.User;
import com.enriko.exsys.utils.Constants;
import com.enriko.exsys.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity{
	
	private EditText edtUsername, edtPassword;
	private Button btnLogin;
	private User user;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getSupportActionBar().hide();
		initUI();
		initEvent();
	}
	
	@Override
	public void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		edtUsername = (EditText)findViewById(R.id.edt_login_username);
		edtPassword = (EditText)findViewById(R.id.edt_login_password);
		btnLogin = (Button)findViewById(R.id.btn_login_submit);
		
		getSupportActionBar().setTitle("Login");
	}
	
	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = edtUsername.getText().toString().trim();
				String password = edtPassword.getText().toString().trim();
				
				if (username.equals("") && password.equals("")) {
					utils.showToast("Semua field harus diisi");
				} else {
					postLogin(username, password);
				}
			}
		});
	}

	public static void toLoginActivity(Activity activity){
		activity.startActivity(new Intent(activity, LoginActivity.class));
	}
	
	protected void postLogin(final String username, final String password) {
		// TODO Auto-generated method stub
		final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
		dialog.setMessage("Harap tunggu...");
		dialog.show();
		
		StringRequest request = new StringRequest(Request.Method.POST, 
				URLs.LOGIN,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						
						parsingResponse(response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						utils.showToast("Login gagal. Silakan coba lagi");
					}
				}){
				@Override
				protected Map<String, String> getParams()
						throws AuthFailureError {
					// TODO Auto-generated method stub
					Map<String, String> params = new HashMap<String, String>();
					params.put("user_id", username);
					params.put("user_pswd", password);
					return params;
				}
		};
		VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(request);
	}
	
	@Override
	public void parsingResponse(String response) {
		// TODO Auto-generated method stub
		super.parsingResponse(response);
		user = User.getUser(response);
		if (user.status.equals(Constants.RES_OK)) {
			preferences.setUser(response);
			preferences.setToken(user.token);
			HomeActivity.toHomeActivity(LoginActivity.this);
			finish();
		}else{
			utils.showToast(user.message);
		}
	}
}
