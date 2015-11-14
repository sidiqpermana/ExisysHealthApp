package com.enriko.exsys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.enriko.exsys.base.BaseActivity;
import com.enriko.exsys.base.VolleySingleton;
import com.enriko.exsys.model.GeneralResponse;
import com.enriko.exsys.utils.Constants;
import com.enriko.exsys.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class AddNewVitalSignActivity extends BaseActivity{
	private EditText edtHrm, edtBpm, edtWeight;
	private RadioGroup rbEkg;
    private RadioButton rbEkgNormal, rbEkgTidakNormal;
	private Button btnSubmit;
    public static String KEY_HRM = "hrm";
    public static String KEY_IS_NORMAL = "isNormal";
    private int hrm = 0;
    private boolean isNormal = false;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vital_sign);
		
		initUI();
        initIntent();
		initEvent();
	}
	
	@Override
	public void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		edtBpm = (EditText)findViewById(R.id.edt_fbpm);
		edtHrm = (EditText)findViewById(R.id.edt_fhrm);
		edtWeight = (EditText)findViewById(R.id.edt_fweight);
		btnSubmit = (Button)findViewById(R.id.btn_submit);
		rbEkg = (RadioGroup)findViewById(R.id.rb_ekg_group);
        rbEkgNormal = (RadioButton)findViewById(R.id.rb_ekg_normal);
        rbEkgTidakNormal = (RadioButton)findViewById(R.id.rb_ekg_abnormal);
		
		getSupportActionBar().setTitle("Add new record");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

    @Override
    public void initIntent() {
        super.initIntent();
        Intent intent = getIntent();
        if (intent != null){
            hrm = intent.getIntExtra(KEY_HRM, 0);
            isNormal = intent.getBooleanExtra(KEY_IS_NORMAL, false);

            edtHrm.setText(String.valueOf(hrm));
            if (isNormal){
                rbEkgNormal.setChecked(true);
            }else{
                rbEkgTidakNormal.setChecked(true);
            }
        }
    }

    @Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String bpm = edtBpm.getText().toString().trim();
                String hrm = edtHrm.getText().toString().trim();
                String weight = edtWeight.getText().toString().trim();
                String ekg = rbEkg.getCheckedRadioButtonId() == R.id.rb_ekg_normal ? "1" :
                        "0";
                String date = utils.getCurrentDate();

                if (bpm.equals("") || hrm.equals("") || weight.equals("") || ekg.equals("")) {
                    utils.showToast("Semua field harus terisi");
                } else {
                    postNewRecord(bpm, hrm, weight, ekg, date);
                }
            }
        });
	}

	@Override
	public void parsingResponse(String response) {
		// TODO Auto-generated method stub
		super.parsingResponse(response);
		
		GeneralResponse generalResponse = GeneralResponse.getGeneralResponse(response);
		if (generalResponse.status.equals(Constants.RES_OK)) {
			utils.showToast(generalResponse.message);
			finish();
		}else{
			utils.showToast(generalResponse.message);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static void toAddNewVitalSignActivity(Activity activity){
		activity.startActivity(new Intent(activity, AddNewVitalSignActivity.class));
	}
	
	protected void postNewRecord(final String bpm, final String hrm, final String weight,
			final String ekg, final String date) {
		// TODO Auto-generated method stub
		final ProgressDialog dialog = new ProgressDialog(AddNewVitalSignActivity.this);
		dialog.setMessage("Harap tunggu...");
		dialog.show();
		
		StringRequest request = new StringRequest(Request.Method.POST,
				URLs.SET_VITAL_SIGN,
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
						utils.printLog("Gagal dalam menambah data");
					}
				}){
				@Override
				protected Map<String, String> getParams()
						throws AuthFailureError {
					// TODO Auto-generated method stub
					Map<String, String> params = new HashMap<String, String>();
					params.put("uid", preferences.getUser().userInfo.id);
					params.put("fdate", date);
					params.put("fekg", ekg);
					params.put("fweight", weight);
					params.put("fbpm", bpm);
					return params;
				}
			
		};
		
		VolleySingleton.getInstance(AddNewVitalSignActivity.this).addToRequestQueue(request);
	}

    public static void toAddNewVitalSign(Activity activity, int hrm, boolean isNormal){
        Intent intent = new Intent(activity, AddNewVitalSignActivity.class);
        intent.putExtra(KEY_HRM, hrm);
        intent.putExtra(KEY_IS_NORMAL, isNormal);
        activity.startActivityForResult(intent, 0);
    }
}
