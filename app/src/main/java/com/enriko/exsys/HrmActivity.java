package com.enriko.exsys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.enriko.exsys.base.BaseActivity;
import com.enriko.exsys.fragment.HrmFragment;
import com.enriko.exsys.heartratemonitor.HeartRateMonitor;


public class HrmActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Heart Rate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.frame_container,
					new HrmFragment(),
					HrmFragment.class.getSimpleName()).commit();
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
        	//AddNewVitalSignActivity.toAddNewVitalSignActivity(HrmActivity.this);
            HeartRateMonitor.toHeartRateMonitor(HrmActivity.this);
            return true;
        }

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

	public static void toHrmActivity(Activity activity){
    	activity.startActivity(new Intent(activity, HrmActivity.class));
    }
}
